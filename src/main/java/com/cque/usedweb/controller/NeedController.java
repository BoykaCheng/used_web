package com.cque.usedweb.controller;

import com.cque.usedweb.entity.*;
import com.cque.usedweb.services.NeedServices;
import com.cque.usedweb.services.PersonServices;
import com.cque.usedweb.services.UsedService;
import com.cque.usedweb.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by Chenge on 2020.3.14 17:29
 */
@Controller
public class NeedController {
    @Autowired
    private NeedServices needServices;
    @Autowired
    private PersonServices personServices;
    @Autowired
    private UsedService usedService;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 跳转到二手论坛界面
     * @return
     */
    @RequestMapping("/toNeedPage")
    public ModelAndView toNeedWedPage(){
        //redis找出查看次数最多的几篇文章
        Set<Object> article_watch_count = redisUtil.zReverseRange("article_watch_count", 0, 3);
        List<Map<String,Object>> hotAtcs = new ArrayList<>();
        for (Object obj : article_watch_count) {
            Map<String,Object> map = new HashMap<>();
            String o = (String)obj;
            Article atc = needServices.findAtcById(Integer.valueOf(o));
            //只能看正常帖子，下架的，未审核的不能看
            if (atc.getDisplay()==1){
                Double count = redisUtil.zScore("article_watch_count", o);
                map.put("article",atc);
                map.put("watchCounts",count);
                hotAtcs.add(map);
            }
        }

        ModelAndView mv = new ModelAndView();
        List<Board> allBoard = needServices.findAllBoard();
        //装map的list ,map中包含板块信息，和响应板块帖子信息
        List<Object> boardList = new ArrayList<>();
        for (Board board : allBoard) {
            Map<String,Object> map = new HashMap<>();

            List<Article> atcs = needServices.findAtcByBoardIdALikeDesc(board.getId());
            if (atcs.size()>4){
                 atcs = atcs.subList(0, 3);
            }
            //map中包含板块信息，和响应板块帖子信息
            map.put("board",board);
            map.put("atcs",atcs);
            boardList.add(map);
        }

        //最火的帖子
        List<Article> hotestAtcs = needServices.findHotestAtc();
        //最新的帖子
        List<Article> newestAtcs = needServices.findNewestAtc();
        if (hotestAtcs.size() > 4){
            hotestAtcs = hotestAtcs.subList(0,3);
        }
        if (newestAtcs.size() > 4){
            newestAtcs = newestAtcs.subList(0,3);
        }

        mv.addObject("hotestAtcs",hotestAtcs);
        mv.addObject("newestAtcs",newestAtcs);
        mv.addObject("boardList",boardList);
        mv.setViewName("need");
        return mv;
    }


    /**
     * 跳转到上传帖子界面
     * @return
     */
    @RequestMapping("/toAddArticle")
    public ModelAndView toAddArticle(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("add_article");
        return mv;
    }

    /**
     * 上传帖子
     * @param article
     * @param request
     * @return
     */
    @PostMapping("/addArticle")
    public ModelAndView addArticle(Article article,HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        HttpSession session = request.getSession();
        Person user = (Person) session.getAttribute("user");
        article.setUserId(user.getId());
        article.setCreateTime(new Date());
        article.setLastClickT(new Date());
        int i = needServices.setArticle(article);
        if (i==1){
            mv = toNeedWedPage();
        }else {
            mv.setViewName("error");
            mv.addObject("errorInfo","上传帖子失败");
        }
        return mv;
    }

    /**
     * 跳转到帖子详情界面
     * @param actId 帖子Id
     * @return
     */
    @GetMapping("/detailAtc/{atcId}")
    public ModelAndView detailAtc(@PathVariable("atcId") Integer actId, HttpServletRequest request){
        HttpSession session = request.getSession();
        Person person = (Person)session.getAttribute("user");
        //redis存放文章观看次数
        redisUtil.zIncrScore("article_watch_count",""+actId,1);
        Double watchCount = redisUtil.zScore("article_watch_count", "" + actId);

        //redis查看自己是否对本文章点赞过
        boolean b = redisUtil.sHasKey("article_like_member" + actId, person.getId());


        ModelAndView mv = new ModelAndView();
        //更新帖子的最后点击时间
        needServices.updateAtcT(actId);
        //帖子实体详情
        Article act = needServices.findAtcById(actId);


        //评论List详情
        List<ArticleMsg> atcMsgs = needServices.findCmtByAtcId(actId,request);
        //推送商品详情
        List<PushPro> pushPros = needServices.findPushProByAtcId(actId,request);

        //评论，map里装评论的用户，以及评论内容
        List<Map<String,Object>> comments = new ArrayList<>();
        //推送商品，map里装评论的用户，以及推送商品
        List<Map<String,Object>> allPushs = new ArrayList<>();

        for (ArticleMsg comment : atcMsgs) {
            Map<String,Object> map = new HashMap<>();
            Person commentUser = personServices.findById(comment.getUserId());
            map.put("commentUser",commentUser);
            map.put("comment",comment);
            comments.add(map);
        }

        for (PushPro pushPro : pushPros) {
            Map<String,Object> map = new HashMap<>();
            Person pushUser = personServices.findById(pushPro.getUserId());
            Product pro = usedService.getProById(pushPro.getProId());
            map.put("pushUser",pushUser);
            map.put("pro",pro);
            map.put("pushPro",pushPro);
            allPushs.add(map);
        }

        //为了在前台展示该帖子的所属板块
        Map<Integer, String> boardMap = getBoardMap();

        //找出浏览该页面的用户的商品
        List<Product> myPros = usedService.findProByUserId(person.getId());




        mv.addObject("myPros",myPros);
        mv.addObject("watchCount",watchCount.intValue());
        mv.addObject("boardMap",boardMap);
        mv.addObject("act",act);
        mv.addObject("comments",comments);
        mv.addObject("allPushs",allPushs);
        mv.addObject("b",b);
        mv.setViewName("act_detail");
        return mv;
    }

    //将所有的板块装进Map
    public Map<Integer,String> getBoardMap(){
        Map<Integer,String> map = new HashMap<>();
        List<Board> allBoard = needServices.findAllBoard();
        for (Board board : allBoard) {
            map.put(board.getId(),board.getBoardName());
        }
        return map;
    }

    /**
     * 插入帖子评论
     * @param articleMsg 评论实体
     * @return
     */
    @PostMapping("/addArticleMsg")
    @ResponseBody
    public String addArticleMsg(ArticleMsg articleMsg){
        articleMsg.setModify(new Date());
        int i = needServices.addArticleMsg(articleMsg);
        if (i == 1){
            return "success";
        }
        return "error";
    }

    /**
     * 插入帖子推送商品
     * @param pushPro 推送商品详情
     * @return
     */
    @PostMapping("/addPushPro")
    @ResponseBody
    public String addPushPro(PushPro pushPro){
        List<PushPro> byAtcIdAndProId = needServices.findByAtcIdAndProId(pushPro.getArticleId(), pushPro.getProId());
        if (byAtcIdAndProId.size()>=1){
            return "existed";
        }else{
            pushPro.setModify(new Date());
            int i = needServices.addPushPro(pushPro);
            if (i == 1){
                return "success";
            }
        }
        return "error";
    }

    /**
     * 通过关键字搜索帖子
     * @param keyWord
     * @return
     */
    @PostMapping("/searchArticle")
    public ModelAndView searchArticle(String keyWord){
        ModelAndView mv = new ModelAndView();
        List<Article> articles = needServices.findAtcByTitleLike(keyWord);
        mv.addObject("articles",articles);
        mv.setViewName("search_atc");
        return mv;
    }

    /**
     * 点击板块，进入帖子列表
     * @param boardId
     * @return
     */
    @RequestMapping("/getBoardAtc/{id}")
    public ModelAndView getBoardAtc(@PathVariable("id") Integer boardId) {
        ModelAndView mv = new ModelAndView();
        List<Article> atcs = needServices.findAtcByBoardIdALikeDesc(boardId);
        mv.addObject("articles",atcs);
        mv.setViewName("search_atc");
        return mv;

    }

    /**
     * 我上传的帖子
     * @return
     */
    @GetMapping("/myArticles")
    public ModelAndView myArticles(HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        HttpSession session = request.getSession();
        Person user = (Person) session.getAttribute("user");
        List<Article> articles = needServices.findMyArticle(user.getId());
        mv.addObject("articles",articles);
        mv.setViewName("my_atc");
        return mv;
    }

    /**
     * 点赞文章
     * @param atcId 文章Id
     * @return
     */
    @PostMapping("/likeArticle")
    @ResponseBody
    public String likeArticle(@RequestParam("atcId") Integer atcId,HttpServletRequest request){
        HttpSession session = request.getSession();
        Person user = (Person) session.getAttribute("user");
        //将点赞存到redis中，每个人只能点赞一次
        redisUtil.sSet("article_like_member"+atcId,user.getId());

        int i = needServices.updateArticle(atcId, 1);
        if (i == 1){
            return "success";
        }
        return "error";
    }



}
