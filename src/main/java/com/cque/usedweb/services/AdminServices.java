package com.cque.usedweb.services;

import com.cque.usedweb.dao.*;
import com.cque.usedweb.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Chenge on 2020.3.17 21:51
 */
@Service
public class AdminServices {
    private static final int WEISHENHE=0;
    private static final int ZHENGCHANG = 1;
    private static final int XIAJIA = 2;
    private static final int SALEOUT = 3;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private PersonMapper personMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private BoardMapper boardMapper;
    @Autowired
    private TopicMapper topicMapper;
    @Autowired
    private VillageMapper villageMapper;
    @Autowired
    private TransactionMapper transactionMapper;
    @Autowired
    private BuyedProMapper buyedProMapper;

    //找出未审核，已审核，正常的商品
    public List<Product> findProByDisplay(Integer display){
        ProductExample example = new ProductExample();
        ProductExample.Criteria criteria = example.createCriteria();
        criteria.andDisplayEqualTo(display);
        List<Product> products = productMapper.selectByExample(example);
        return products;
    }

    //找出未审核，已审核，正常的帖子
    public List<Article> findArticleByDisplay(String display){
        ArticleExample example = new ArticleExample();
        ArticleExample.Criteria criteria = example.createCriteria();
        criteria.andDisplayEqualTo(Byte.valueOf(display));
        List<Article> articles = articleMapper.selectByExampleWithBLOBs(example);
        return articles;
    }

    //找出相应用户
    public List<Person> findUserByFlag(Integer flag){
        PersonExample example = new PersonExample();
        PersonExample.Criteria criteria = example.createCriteria();
        if (flag == 1){
            criteria.andHuiYEqualTo(Byte.valueOf("1"));
        }else if (flag == 2){
            criteria.andLockedEqualTo(Byte.valueOf("1"));
        }
        List<Person> people = personMapper.selectByExample(example);
        return people;
    }

    //找出相应 的交易记录
    public List<BuyedPro> findDealByStatu(Integer statu){
        BuyedProExample example = new BuyedProExample();
        BuyedProExample.Criteria criteria = example.createCriteria();
        criteria.andStateEqualTo(statu);
        List<BuyedPro> buyedPros = buyedProMapper.selectByExample(example);
        return buyedPros;
    }

    //上架,下架商品
    public int shelfPro(Integer proId,Integer flag){
        Product product = productMapper.selectByPrimaryKey(proId);
        if (flag == 1){
            product.setDisplay(ZHENGCHANG);
            //相应的子类别商品数加一
            Topic topic = topicMapper.selectByPrimaryKey(product.getTopicId());
            topic.setTopicNum(topic.getTopicNum()+1);
            topicMapper.updateByPrimaryKeySelective(topic);
        }else {
            product.setDisplay(XIAJIA);
        }
        int i = productMapper.updateByPrimaryKeySelective(product);
        return i;
    }

    //上架,下架帖子
    public int checkArticle(Integer atcId,Integer flag){
        Article article = articleMapper.selectByPrimaryKey(atcId);
        if (flag == 1){
            article.setDisplay(Byte.valueOf("1"));
            //相应的板块帖子数加一
            Board board = boardMapper.selectByPrimaryKey(article.getBoardId());
            board.setArticleNum(board.getArticleNum()+1);
            boardMapper.updateByPrimaryKeySelective(board);
        }else {
            article.setDisplay(Byte.valueOf("2"));
        }
        int i = articleMapper.updateByPrimaryKeySelective(article);
        return i;
    }

    //锁定解锁用户
    public int lockUser(Integer userId,String flag){
        Person person = personMapper.selectByPrimaryKey(userId);
        person.setLocked(Byte.valueOf(flag));
        int i = personMapper.updateByPrimaryKeySelective(person);
        return i;
    }

    //添加板块
    public int addBoard(Board board){
        int i = boardMapper.insertSelective(board);
        return i;
    }
    //添加子类别
    public int addTopic(Topic topic){
        int i = topicMapper.insertSelective(topic);
        if(i == 1){
            //相应的板块子类别数加一
            Board board = boardMapper.selectByPrimaryKey(topic.getBoardId());
            board.setTopicNum(board.getTopicNum()+1);
            boardMapper.updateByPrimaryKeySelective(board);
        }
        return i;
    }

    //添加社区
    public int addVillage(Village village){
        return villageMapper.insertSelective(village);
    }
    //添加交易方式
    public int addTran(Transaction transaction){
        transaction.setModify(new Date());
        return transactionMapper.insertSelective(transaction);
    }

    //修改板块
    public int updateBoard(Board board){
        return boardMapper.updateByPrimaryKeySelective(board);
    }

    //修改子类别
    public int updateTopic(Topic topic){
        return topicMapper.updateByPrimaryKeySelective(topic);
    }

    //修改社区
    public int updateVillage(Village village){
        return villageMapper.updateByPrimaryKeySelective(village);
    }

    //修改交易方式
    public int updateTran(Transaction transaction){
        return transactionMapper.updateByPrimaryKeySelective(transaction);
    }
}
