package com.cque.usedweb.controller;

import com.cque.usedweb.entity.*;
import com.cque.usedweb.services.AdminServices;
import com.cque.usedweb.services.NeedServices;
import com.cque.usedweb.services.PersonServices;
import com.cque.usedweb.services.UsedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Chenge on 2020.3.17 21:45
 */
@Controller
public class AdminController {
    private static final int WEISHENHE=0;
    private static final int ZHENGCHANG = 1;
    private static final int XIAJIA = 2;
    private static final int SALEOUT = 3;
    @Autowired
    private AdminServices adminServices;
    @Autowired
    private UsedService usedService;
    @Autowired
    private PersonServices personServices;
    @Autowired
    private NeedServices needServices;


    /**
     * 跳转至管理界面
     * @return
     */
    @RequestMapping("/toAdmin")
    public ModelAndView toAdmin(){
        ModelAndView mv =new ModelAndView();
        mv.setViewName("admin");
        return mv;
    }
    /**
     * 商品管理
     * @param flag
     * @return
     */
    @RequestMapping("/toCheckProPage")
    public ModelAndView toCheckProPage(@RequestParam(value = "flag",required = false,defaultValue = "0") Integer flag){
        ModelAndView mv =new ModelAndView();

        List<Product> products = adminServices.findProByDisplay(flag);
        Map<Integer, String> topicMap = getTopicMap();
        Map<Integer, String> tranMap = getTranMap();
        Map<Integer, String> userMap = getAllUsers();
        mv.addObject("products",products);
        mv.addObject("flag",flag);
        mv.addObject("topicMap",topicMap);
        mv.addObject("tranMap",tranMap);
        mv.addObject("userMap",userMap);
        mv.setViewName("admin_pro");
        return mv;
    }

    /**
     * 帖子管理
     * @return
     */
    @RequestMapping("/toCheckArticlePage")
    public ModelAndView toCheckArticlePage(@RequestParam(value = "flag",required = false,defaultValue = "0") String flag){
        ModelAndView mv = new ModelAndView();
        List<Article> getAllArticles = adminServices.findArticleByDisplay(flag);
        mv.addObject("articles",getAllArticles);
        mv.addObject("flag",flag);
        mv.setViewName("admin_article");
        return mv;
    }

    /**
     * 用户管理界面
     * @param flag 0：所有 1：会员 2：锁定
     * @return
     */
    @RequestMapping("/toCheckUserPage")
    public ModelAndView toCheckUserPage(@RequestParam(value = "flag",required = false,defaultValue = "0") Integer flag){
        ModelAndView mv = new ModelAndView();
        List<Person> users = adminServices.findUserByFlag(flag);
        mv.addObject("users",users);
        mv.addObject("flag",flag);
        mv.setViewName("admin_user");
        return mv;
    }

    /**
     * 板块管理界面
     * @return
     */
    @RequestMapping("/toCheckBoardPage")
    public ModelAndView toCheckBoardPage(){
        ModelAndView mv = new ModelAndView();
        List<Board> boards = usedService.findAllBoard();
        mv.addObject("boards",boards);
        mv.setViewName("admin_board");
        return mv;
    }

    /**
     * 子类别管理界面
     * @return
     */
    @RequestMapping("/toCheckTopicPage")
    public ModelAndView toCheckTopicPage(){
        ModelAndView mv = new ModelAndView();
        List<Topic> topics = usedService.getAllTopics();
        Map<Integer, String> boardMap = getBoardMap();
        mv.addObject("topics",topics);
        mv.addObject("boardMap",boardMap);
        mv.setViewName("admin_topic");
        return mv;
    }

    //找到所有板块的map
    public Map<Integer,String> getBoardMap(){
        List<Board> allBoard = usedService.findAllBoard();
        Map<Integer,String> map = new HashMap<>();
        for (Board board : allBoard) {
            map.put(board.getId(),board.getBoardName());
        }
        return map;
    }

    /**
     * 社区管理界面
     * @return
     */
    @RequestMapping("/toCheckVillagePage")
    public ModelAndView toCheckVillagePage(){
        ModelAndView mv = new ModelAndView();
        List<Village> villages = usedService.getAllVillages();
        mv.addObject("villages",villages);
        mv.setViewName("admin_village");
        return mv;
    }

    /**
     * 交易方式管理界面
     * @return
     */
    @RequestMapping("/toCheckTranPage")
    public ModelAndView toCheckTranPage(){
        ModelAndView mv = new ModelAndView();
        List<Transaction> trans = usedService.getAllTrans();
        mv.addObject("trans",trans);
        mv.setViewName("admin_tran");
        return mv;
    }

    /**
     * 交易记录界面
     * @param flag 1：交易成功  0：交易失败
     * @return
     */
    @RequestMapping("/toCheckDealPage")
    public ModelAndView toCheckDealPage(@RequestParam(value = "flag",required = false,defaultValue = "1") Integer flag){
        List<Map<String,Object>> list = new ArrayList<>();
        ModelAndView mv = new ModelAndView();
        List<BuyedPro> deals = adminServices.findDealByStatu(flag);
        for (BuyedPro deal : deals) {
            Map<String,Object> map = new HashMap<>();
            map.put("product",usedService.getProById(deal.getProId()));
            map.put("in",personServices.findById(deal.getUserId()));
            map.put("out",personServices.findById(deal.getFromUserId()));
            map.put("tran",usedService.getTranById(deal.getTransaction()));
            map.put("deal",deal);
            list.add(map);
        }
        mv.addObject("lists",list);
        mv.setViewName("admin_deal");
        return mv;
    }

    /**
     * 上架或者下架商品
     * @param proId 商品ID
     * @param flag 1：上架商品。  2：下架商品
     * @return
     */
    @PostMapping("/checkPro")
    @ResponseBody
    public String shelfPro(@RequestParam("proId") Integer proId,@RequestParam("flag") Integer flag){
        int i = adminServices.shelfPro(proId,flag);
        if (i==1){
            return "success";
        }
        return "error";
    }

    /**
     * 上架或者下架帖子
     * @param atcId 帖子ID
     * @param flag 1：上架帖子。  2：下架帖子
     * @return
     */
    @PostMapping("/checkArticle")
    @ResponseBody
    public String checkArticle(@RequestParam("atcId") Integer atcId,@RequestParam("flag") Integer flag){
        int i = adminServices.checkArticle(atcId,flag);
        if (i==1){
            return "success";
        }
        return "error";
    }

    /**
     * 锁定或解锁用户
     * @param userId 用户ID
     * @param flag 1：锁定用户。  2：解锁用户
     * @return
     */
    @PostMapping("/checkUser")
    @ResponseBody
    public String checkUser(@RequestParam("userId") Integer userId,@RequestParam("flag") String flag){
        int i = adminServices.lockUser(userId,flag);
        if (i==1){
            return "success";
        }
        return "error";
    }

    //将子类别装进map
    public Map<Integer,String> getTopicMap(){
        Map<Integer,String> map = new HashMap<>();
        List<Topic> allTopics = usedService.getAllTopics();
        for (Topic t : allTopics){
            map.put(t.getId(),t.getTopicName());
        }
        return map;
    }

    //将交易方式装进Map
    public Map<Integer,String> getTranMap(){
        Map<Integer,String> map = new HashMap<>();
        List<Transaction> allTrans = usedService.getAllTrans();

        for (Transaction tran : allTrans) {
            map.put(tran.getNum(),tran.getTranWay());
        }
        return map;
    }
    //将所有用户装进map
    public Map<Integer,String> getAllUsers(){
        Map<Integer,String> map = new HashMap<>();
        List<Person> users = personServices.findAllUsers();
        for(Person p : users){
            map.put(p.getId(),p.getUserName());
        }
        return map;
    }

    /**
     * 跳转添加板块界面
     * @return
     */
    @RequestMapping("/toAddBoard")
    public ModelAndView toAddBoard(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("add_board");
        return mv;
    }

    /**
     * 跳转添加子类别界面
     * @return
     */
    @RequestMapping("/toAddTopic")
    public ModelAndView toAddTopic(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("add_topic");
        return mv;
    }

    /**
     * 跳转添加社区信息界面
     * @return
     */
    @RequestMapping("/toAddVillage")
    public ModelAndView toAddVillage(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("add_village");
        return mv;
    }

    /**
     * 跳转添加交易方式界面
     * @return
     */
    @RequestMapping("/toAddTran")
    public ModelAndView toAddTran(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("add_tran");
        return mv;
    }

    /**
     * 添加板块
     * @param board
     * @return
     */
    @PostMapping("/addBoard")
    public ModelAndView addBoard(Board board){
        int i = adminServices.addBoard(board);
        ModelAndView mv = toCheckBoardPage();
        return mv;
    }

    /**
     * 添加子类别
     * @param topic
     * @return
     */
    @PostMapping("/addTopic")
    public ModelAndView addTopic(Topic topic){
        int i = adminServices.addTopic(topic);
        ModelAndView mv = toCheckTopicPage();
        return mv;
    }

    /**
     * 添加社区信息
     * @param village
     * @return
     */
    @PostMapping("/addVillage")
    public ModelAndView addVillage(Village village){
        int i = adminServices.addVillage(village);
        ModelAndView mv = toCheckVillagePage();
        return mv;
    }

    /**
     * 添加交易方式
     * @param transaction
     * @return
     */
    @PostMapping("/addTran")
    public ModelAndView addTran(Transaction transaction){
        int i = adminServices.addTran(transaction);
        ModelAndView mv = toCheckTranPage();
        return mv;
    }

    /**
     * 跳转修改板块界面
     * @param boardId
     * @return
     */
    @RequestMapping("/toChangeBoard")
    public ModelAndView toChangeBoard(@RequestParam("boardId") Integer boardId){
        ModelAndView mv = new ModelAndView();
        Board board = usedService.getBoardById(boardId);
        mv.addObject("board",board);
        mv.setViewName("edit_board");
        return mv;
    }

    /**
     * 跳转修改子类别界面
     * @param topicId
     * @return
     */
    @RequestMapping("/toChangeTopic")
    public ModelAndView toChangeTopic(@RequestParam("topicId") Integer topicId){
        ModelAndView mv = new ModelAndView();
        Topic topicById = usedService.getTopicById(topicId);
        mv.addObject("topic",topicById);
        mv.setViewName("edit_topic");
        return mv;
    }

    /**
     * 跳转修改社区信息界面
     * @param villageId
     * @return
     */
    @RequestMapping("/toChangeVillage")
    public ModelAndView toChangeVillage(@RequestParam("villageId") Integer villageId){
        ModelAndView mv = new ModelAndView();
        Village villageById = usedService.getVillageById(villageId);
        mv.addObject("village",villageById);
        mv.setViewName("edit_village");
        return mv;
    }

    /**
     * 跳转修改交易方式界面
     * @param tranId
     * @return
     */
    @RequestMapping("/toChangeTran")
    public ModelAndView toChangeTran(@RequestParam("tranId") Integer tranId){
        ModelAndView mv = new ModelAndView();
        Transaction tranById = usedService.getTranById(tranId);
        mv.addObject("tran",tranById);
        mv.setViewName("edit_tran");
        return mv;
    }

    /**
     * 修改板块
     * @param board
     * @return
     */
    @PostMapping("/changeBoard")
    public ModelAndView changeBoard(Board board){
        int i = adminServices.updateBoard(board);
        ModelAndView mv = toCheckBoardPage();
        return mv;
    }

    /**
     * 修改子类别
     * @param topic
     * @return
     */
    @PostMapping("/changeTopic")
    public ModelAndView changeTopic(Topic topic){
        int i = adminServices.updateTopic(topic);
        ModelAndView mv = toCheckTopicPage();
        return mv;
    }

    /**
     * 修改社区
     * @param village
     * @return
     */
    @PostMapping("/changeVillage")
    public ModelAndView changeVillage(Village village){
        int i = adminServices.updateVillage(village);
        ModelAndView mv = toCheckVillagePage();
        return mv;
    }

    /**
     * 修改交易方式
     * @param transaction
     * @return
     */
    @PostMapping("/changeTran")
    public ModelAndView changeTran(Transaction transaction){
        int i = adminServices.updateTran(transaction);
        ModelAndView mv = toCheckTranPage();
        return mv;
    }
}
