package com.cque.usedweb.services;

import com.cque.usedweb.dao.*;
import com.cque.usedweb.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * Created by Chenge on 2020.3.14 17:31
 */
@Service
public class NeedServices {
    @Autowired
    private BoardMapper boardMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private ArticleMsgMapper articleMsgMapper;
    @Autowired
    private PushProMapper pushProMapper;
    @Autowired
    private ProductMapper productMapper;

    //找出所有板块
    public List<Board> findAllBoard(){
        BoardExample example = new BoardExample();
        return boardMapper.selectByExample(example);
    }

    //找出响应板块的帖子并按照  点赞数降序
    public List<Article> findAtcByBoardIdALikeDesc(Integer boardId){
        ArticleExample example = new ArticleExample();
        example.setOrderByClause("like_num DESC");
        ArticleExample.Criteria criteria = example.createCriteria();
        criteria.andBoardIdEqualTo(boardId);
        criteria.andDisplayEqualTo(Byte.valueOf("1"));
        List<Article> articles = articleMapper.selectByExample(example);
        return articles;
    }

    //找出最新的帖子
    public List<Article> findNewestAtc(){
        ArticleExample example = new ArticleExample();
        example.setOrderByClause("create_time DESC");
        ArticleExample.Criteria criteria = example.createCriteria();
        criteria.andDisplayEqualTo(Byte.valueOf("1"));
        List<Article> articles = articleMapper.selectByExample(example);
        return articles;
    }

    //找出最热的帖子
    public List<Article> findHotestAtc(){
        ArticleExample example = new ArticleExample();
        example.setOrderByClause("like_num DESC");
        ArticleExample.Criteria criteria = example.createCriteria();
        criteria.andDisplayEqualTo(Byte.valueOf("1"));
        List<Article> articles = articleMapper.selectByExample(example);
        return articles;
    }

    //通过帖子ID找到帖子详情
    public Article findAtcById(Integer id){
        Article article = articleMapper.selectByPrimaryKey(id);
        return article;
    }

    //通过帖子ID找到帖子的全部回复（可见的回复）,并且根据回复时间降序
    public List<ArticleMsg> findCmtByAtcId(Integer atcId, HttpServletRequest request){
        HttpSession session = request.getSession();
        Person user = (Person) session.getAttribute("user");
        Article article = articleMapper.selectByPrimaryKey(atcId);

        ArticleMsgExample example = new ArticleMsgExample();
        example.setOrderByClause("modify DESC");
        ArticleMsgExample.Criteria criteria = example.createCriteria();
        criteria.andActIdEqualTo(atcId);
        //如果是上传用户的人，可以看到
        if (!(user.getId().equals(article.getUserId()))){
            criteria.andDisplayEqualTo(1);
        }

        List<ArticleMsg> articleMsgs = articleMsgMapper.selectByExampleWithBLOBs(example);
        return articleMsgs;
    }

    //插入帖子
    public int addArticle(Article article){
        int i = articleMapper.insertSelective(article);
        return i;
    }

    //插入帖子评论
    public int addArticleMsg(ArticleMsg articleMsg){
        return articleMsgMapper.insert(articleMsg);
    }

    //通过帖子ID找到帖子的全部推送商品（可见的推送商品）,并且根据推送时间降序
    public List<PushPro> findPushProByAtcId(Integer articleId,HttpServletRequest request){
        HttpSession session = request.getSession();
        Person user = (Person) session.getAttribute("user");
        Article article = articleMapper.selectByPrimaryKey(articleId);

        PushProExample example = new PushProExample();
        example.setOrderByClause("modify DESC");
        PushProExample.Criteria criteria = example.createCriteria();
        criteria.andArticleIdEqualTo(articleId);
        //如果是上传用户的人，可以看到
        if (!(user.getId().equals(article.getUserId()))){
            criteria.andDisplayEqualTo(1);
        }
        List<PushPro> pushPros = pushProMapper.selectByExample(example);
        return pushPros;
    }

    //插入帖子推送商品
    public int addPushPro(PushPro pushPro){
        return pushProMapper.insertSelective(pushPro);
    }

    //通过帖子ID和商品ID查找是否有响应的记录
    public List<PushPro> findByAtcIdAndProId(Integer articleId,Integer ProId){
        PushProExample example = new PushProExample();
        PushProExample.Criteria criteria = example.createCriteria();
        criteria.andArticleIdEqualTo(articleId);
        criteria.andProIdEqualTo(ProId);
        List<PushPro> pushPros = pushProMapper.selectByExample(example);
        return pushPros;
    }

    //通过关键字搜索帖子并按照点赞数降序
    public List<Article> findAtcByTitleLike(String keyWord){
        ArticleExample example = new ArticleExample();
        example.setOrderByClause("like_num DESC");
        ArticleExample.Criteria criteria = example.createCriteria();
        criteria.andArticleTitleLike("%"+keyWord+"%");
        criteria.andDisplayEqualTo(Byte.valueOf("1"));
        List<Article> articles = articleMapper.selectByExample(example);
        return articles;
    }

    //我上传的帖子
    public List<Article> findMyArticle(Integer userId){
        ArticleExample example = new ArticleExample();
        example.setOrderByClause("like_num DESC");
        ArticleExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        criteria.andDisplayEqualTo(Byte.valueOf("1"));
        List<Article> articles = articleMapper.selectByExample(example);
        return articles;
    }

    //将文章的点赞数加上num
    public int updateArticle(Integer atcId,int num){
        Article article = articleMapper.selectByPrimaryKey(atcId);
        Integer likeNum = article.getLikeNum();
        article.setLikeNum(likeNum+num);
        ArticleExample example = new ArticleExample();
        int i = articleMapper.updateByPrimaryKeySelective(article);
        return i;
    }

    //更新文章的最后一次点击时间
    public int updateAtcT(Integer atcId){
        Article article = articleMapper.selectByPrimaryKey(atcId);
        article.setLastClickT(new Date());
        return articleMapper.updateByPrimaryKeySelective(article);
    }

    //添加帖子记录
    public int setArticle(Article article){
        int i = articleMapper.insertSelective(article);
        return i;
    }

    //找出全部帖子
    public List<Article> getAllArticles(){
        ArticleExample example = new ArticleExample();
        List<Article> articles = articleMapper.selectByExample(example);
        return articles;
    }

}
