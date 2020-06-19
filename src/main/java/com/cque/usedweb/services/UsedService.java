package com.cque.usedweb.services;

import com.cque.usedweb.dao.*;
import com.cque.usedweb.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by Chenge on 2020.2.13 14:12
 */
@Service
public class UsedService {

    @Autowired
    private BoardMapper boardMapper;
    @Autowired
    private TopicMapper topicMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private EmageMapper emageMapper;
    @Autowired
    private ProductMsgMapper productMsgMapper;
    @Autowired
    private VillageMapper villageMapper;
    @Autowired
    private TransactionMapper transactionMapper;
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private BuyedProMapper buyedProMapper;
    @Autowired
    private PersonMapper personMapper;


    //找出全部的板块
    public List<Board> findAllBoard(){
        BoardExample example = new BoardExample();
        return boardMapper.selectByExample(example);
    }

    //通过板块ID找出相应的子类别项目
    public List<Topic> findTopicByBoardId(Integer boardId){
        return topicMapper.selectByBoardId(boardId);
    }

    //模糊查询找出商品
    public List<Product> findProByDyCondition(Map map){
        return productMapper.selectByDyCondition(map);
    }

    //找出成功交易商品数前三的用户
    public List<Map<String,Object>> findUserOrderByDealNum(){
        List<ManDealNum> dealPers = buyedProMapper.selectOrderByDealNum();
        List<Map<String,Object>> topThreeMan = new ArrayList<>();

        if (dealPers.size()>3){
            dealPers = dealPers.subList(0,3);
        }
        for (ManDealNum dealPer : dealPers) {
            Person person = personMapper.selectByPrimaryKey(dealPer.getUserId());
            Map<String,Object> map = new HashMap<>();
            map.put("user",person);
            map.put("dealNum",dealPer.getDealNums());
            topThreeMan.add(map);
        }

        return topThreeMan;
    }
    //自带的example进行模糊查询
    public List<Product> findProByExample(Condition condition){
        ProductExample example = new ProductExample();
        ProductExample.Criteria criteria = example.createCriteria();
        criteria.andDisplayEqualTo(1);
        if ((condition.getFlag())){
            criteria.andNameLike("%"+condition.getKeyWord()+"%");
        }else{
            if(condition.getTopicId()!= null){
                criteria.andTopicIdEqualTo(condition.getTopicId());
            }
            if(condition.getKeyWord() != null){
                criteria.andNameLike("%"+condition.getKeyWord()+"%");
            }
            if(condition.getLevel() != null){
                criteria.andLevelGreaterThanOrEqualTo(condition.getLevel());
            }
            if(condition.getColumnSort() != null){
                example.setOrderByClause(condition.getColumnSort());
            }
            if(condition.getUserId() != null){
                criteria.andUserIdEqualTo(condition.getUserId());
            }
        }
        List<Product> products = productMapper.selectByExample(example);
        return products;
    }

    //找出全部商品
    public List<Product> getAllPros(){
        ProductExample example = new ProductExample();
        ProductExample.Criteria criteria = example.createCriteria();
        criteria.andDisplayEqualTo(1);
        List<Product> products = productMapper.selectByExample(example);
        return products;
    }

    //找出全部的子类别
    public List<Topic> getAllTopics(){
        TopicExample example = new TopicExample();
        List<Topic> topics = topicMapper.selectByExample(example);
        return topics;
    }

    //保存商品
    public int savePro(Product product){
        int i = productMapper.updateByPrimaryKeySelective(product);
        return i;
    }

    //找出交易成功的商品
    public List<BuyedPro> getAllDealNums(){
        BuyedProExample example = new BuyedProExample();
        List<BuyedPro> dealNums = buyedProMapper.selectByExample(example);
        return dealNums;
    }

    //通过topicId找出商品
    public List<Product> getProByTopicId(Integer topicId){
        ProductExample example = new ProductExample();
        ProductExample.Criteria criteria = example.createCriteria();
        criteria.andTopicIdEqualTo(topicId);
        criteria.andDisplayEqualTo(1);
        List<Product> products = productMapper.selectByExample(example);
        return products;
    }

    //通过商品ID查找商品
    public Product getProById(Integer proId){
        Product product = productMapper.selectByPrimaryKey(proId);
        return product;
    }

    //通过商品id将商品图片查出来
    public List<Emage> getImgByProId(Integer proId){
        EmageExample example = new EmageExample();
        EmageExample.Criteria criteria = example.createCriteria();
        criteria.andProIdEqualTo(proId);
        return emageMapper.selectByExample(example);
    }

    //用户给商品留言，评价
    public int setProductMsg(ProductMsg productMsg){
        return productMsgMapper.insertSelective(productMsg);
    }

    //通过商品Id查出该商品的留言
    public List<ProductMsg> getProMsgByProId(Integer proId, HttpServletRequest request){
        HttpSession session = request.getSession();
        Person user = (Person) session.getAttribute("user");
        Product product = productMapper.selectByPrimaryKey(proId);

        ProductMsgExample example = new ProductMsgExample();
        ProductMsgExample.Criteria criteria = example.createCriteria();
        criteria.andProIdEqualTo(proId);
        //如果是上传用户的人，可以看到
        if (!(user.getId().equals(product.getUserId()))){
            criteria.andDisplayEqualTo(1);
        }
        return productMsgMapper.selectByExampleWithBLOBs(example);
    }

    //找出已注册的小区
    public List<Village> getAllVillages(){
        VillageExample example = new VillageExample();
        return villageMapper.selectByExample(example);
    }

    //通过Id 查出社区
    public Village getVillageById(Integer villageId){
        return villageMapper.selectByPrimaryKey(villageId);
    }

    //找出全部的交易方式
    public List<Transaction> getAllTrans(){
        TransactionExample example = new TransactionExample();
        return transactionMapper.selectByExample(example);
    }

    //通过Id查出交易方式
    public Transaction getTranById(Integer tranId){
        return transactionMapper.selectByPrimaryKey(tranId);
    }

    //添加上架新商品
    public int insertPro(Product product){
        return productMapper.insertSelective(product);
    }

    //添加商品图片
    public int addEmage(Emage emage){
        return emageMapper.insertSelective(emage);
    }

    //通过用户Id查找商品(上架时间排序)
    public List<Product> findProByUserId(Integer userId){
        ProductExample example = new ProductExample();
        example.setOrderByClause("shelf DESC");
        ProductExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        criteria.andDisplayEqualTo(1);
        List<Product> products = productMapper.selectByExample(example);
        return products;
    }

    //添加购物车记录
    public int setCart(Cart cart){
        int i = cartMapper.insertSelective(cart);
        return i;
    }

    //删除购物车记录
    public int removeCart(Integer proId,Integer userId){
        CartExample example = new CartExample();
        CartExample.Criteria criteria = example.createCriteria();
        criteria.andProIdEqualTo(proId);
        criteria.andUserIdEqualTo(userId);
        int i = cartMapper.deleteByExample(example);
        return i;
    }

    public int removeCart2(Integer cartId){
        int i = cartMapper.deleteByPrimaryKey(cartId);
        return i;
    }

    //通过用户Id找到用户的购物车信息
    public List<Map<String,Object>> getMyCart(Integer userId){
        List<Map<String,Object>> cartList = new ArrayList<>();
        CartExample example = new CartExample();
        CartExample.Criteria criteria = example.createCriteria();
        criteria.andDisplayEqualTo(1);
        criteria.andUserIdEqualTo(userId);
        List<Cart> carts = cartMapper.selectByExample(example);
        for (Cart cart : carts) {
            Map<String,Object> map = new HashMap<>();
            Product product = productMapper.selectByPrimaryKey(cart.getProId());
            map.put("cart",cart);
            map.put("product",product);
            cartList.add(map);
        }
        return cartList;
    }

    //查看是否以加购本商品
    public List<Cart> findCart(Integer proId,Integer userId){
        CartExample example = new CartExample();
        CartExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        criteria.andProIdEqualTo(proId);
        List<Cart> carts = cartMapper.selectByExample(example);
        return carts;

    }

    //插入交易记录
    public int setBuyedPro(BuyedPro buyedPro){
        int i = buyedProMapper.insertSelective(buyedPro);
        return i;
    }



    //找出我销售的商品
    public List<Map<String,Object>> findMySalePro(Integer userId){
        List<Map<String,Object>> list = new ArrayList<>();
        BuyedProExample example = new BuyedProExample();
        BuyedProExample.Criteria criteria = example.createCriteria();
        criteria.andFromUserIdEqualTo(userId);
        List<BuyedPro> buyedPros = buyedProMapper.selectByExample(example);
        for (BuyedPro buyedPro : buyedPros) {
            Map<String,Object> map = new HashMap<>();
            Product product = productMapper.selectByPrimaryKey(buyedPro.getProId());
            map.put("buyedPro",buyedPro);
            map.put("product",product);
            list.add(map);
        }
        return list;
    }

    //找出我销售的商品
    public List<Map<String,Object>> findMyBuyedPro(Integer userId){
        List<Map<String,Object>> list = new ArrayList<>();
        BuyedProExample example = new BuyedProExample();
        BuyedProExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        List<BuyedPro> buyedPros = buyedProMapper.selectByExample(example);
        for (BuyedPro buyedPro : buyedPros) {
            Map<String,Object> map = new HashMap<>();
            Product product = productMapper.selectByPrimaryKey(buyedPro.getProId());
            map.put("buyedPro",buyedPro);
            map.put("product",product);
            list.add(map);
        }
        return list;
    }

    //通过板块ID找到板块
    public Board getBoardById(Integer boardId){
        Board board = boardMapper.selectByPrimaryKey(boardId);
        return board;
    }

    //上架商品，板块商品数加一
    public int updateBoard(Board board){
        int i = boardMapper.updateByPrimaryKeySelective(board);
        return i;
    }

    //通过板块ID找到子类别
    public Topic getTopicById(Integer topicId){
        Topic topic = topicMapper.selectByPrimaryKey(topicId);
        return topic;
    }

    //上架商品，子类别商品数加一
    public int updateTopic(Topic topic){
        int i = topicMapper.updateByPrimaryKeySelective(topic);
        return i;
    }

    //更新商品
    public int updatePro(Product product){
        int i = productMapper.updateByPrimaryKeySelective(product);
        return i;
    }






}
