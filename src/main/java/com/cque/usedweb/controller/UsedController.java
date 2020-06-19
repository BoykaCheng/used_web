package com.cque.usedweb.controller;

import com.cque.usedweb.entity.*;
import com.cque.usedweb.services.PersonServices;
import com.cque.usedweb.services.UsedService;
import com.cque.usedweb.util.AliyunOSSUtil;
import com.cque.usedweb.util.RedisUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 求购商城控制器
 * Created by Chenge on 2020.2.13 13:36
 */
@Controller
@Transactional
public class UsedController {

    @Autowired
    private UsedService usedService;
    @Autowired
    private PersonServices personServices;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private AliyunOSSUtil aliyunOSSUtil;
    @Autowired
    private AliConfEntity aliConfEntity;

    /**
     * 跳转到二手网页,并且显示用户数量，会员数量等等
     * @return
     */
    @GetMapping("/toUsedPage")
    public ModelAndView toUsedWedPage(){
        //redis查出最热的商品
        Set<Object> range = redisUtil.zReverseRange("product_watch_count", 0, 4);
        List<Map<String,Object>> hotPros = new ArrayList<>();
        for (Object obj : range) {
            String o = (String) obj;
            Map<String,Object> map = new HashMap<>();
            Double watchCounts = redisUtil.zScore("product_watch_count", o);
            Product pro = usedService.getProById(Integer.valueOf(o));
            if (pro.getDisplay() == 1){
                map.put("pro",pro);
                map.put("watchCounts",watchCounts.intValue());
                hotPros.add(map);
            }
        }

        ModelAndView mv = new ModelAndView();
        int userNums = personServices.findAllUsers().size();
        int memberNums = personServices.findAllMember().size();
        int productNums = usedService.getAllPros().size();
        int dealNums = usedService.getAllDealNums().size();
        mv.addObject("userNums",userNums);
        mv.addObject("memberNums",memberNums);
        mv.addObject("productNums",productNums);
        mv.addObject("dealNums",dealNums);
        mv.addObject("hotPros",hotPros);
        //主要帮助前台显示商品所属的类别与运送方式
        Map<Integer, String> map1 = getTopicMap();
        Map<Integer,String> map2 = getTranMap();
        mv.addObject("map",map1);
        mv.addObject("tranMap",map2);
        mv.setViewName("used");
        return mv;
    }

    /**
     * 找出全部的板块
     * @return
     */
    @GetMapping("/getAllBoard")
    @ResponseBody
    public List<Board> getAllBoard(){
        return usedService.findAllBoard();
    }

    /**
     * 通过板块ID找出相应的子类别项目
     * @param boardId 板块ID
     * @return
     */
    @GetMapping("/getTopicByBoardId")
    @ResponseBody
    public List<Topic> getTopicByBoardId(@RequestParam("boardId") Integer boardId){
        return usedService.findTopicByBoardId(boardId);
    }

    /**
     * 通过条件动态查询出想要找到的商品信息    注：后经优化已弃用
     * @return
     */
    @PostMapping("/getProByDyContion")
    public ModelAndView getProByDyContion(@RequestParam(value = "topicId",required = false) Integer topicId,@RequestParam(value = "keyWord",required = false) String keyword,@RequestParam(value = "flag",defaultValue = "false") boolean flag,@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum){
        ModelAndView mv = new ModelAndView();
        if("".equals(keyword)){
            keyword = null;
        }
        //只通过关键字查询
        if(flag){
            topicId = null;
        }
        Map map = new HashMap<>();
        map.put("topicId",topicId);
        map.put("keyWord",keyword);
        List<Product> pro = usedService.findProByDyCondition(map);
        //分页
        PageHelper.startPage(pageNum,10);
        PageInfo<Product> pageInfo = new PageInfo<>(pro);

        mv.addObject("pageInfo",pageInfo);
        //主要帮助前台显示商品所属的类别
        Map<Integer, String> map1 = getTopicMap();
        mv.addObject("keyWord",keyword);
        mv.addObject("map",map1);
        mv.setViewName("search_cat");
        return mv;
    }

    /**
     * 通过条件动态查询出想要找到的商品信息
     * @param condition 自定义的条件封装
     * @param pageNum 页面数
     * @param boardId 板块Id
     * @return
     */
    @RequestMapping("/getProByExample")
    public ModelAndView getProByExample(Condition condition, @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,@RequestParam(value = "boardId",required = false) Integer boardId,@RequestParam(value = "boardIid",required = false) Integer boardIid){
        ModelAndView mv = new ModelAndView();
        //防止用户恶意输入空格字符
        if("".equals(condition.getKeyWord())){
            condition.setKeyWord(null);
        }
        if("".equals(condition.getColumnSort())){
            condition.setColumnSort(null);
        }
        mv.setViewName("search_cat");
        if(boardId != null){
            //跳转到category页面（通过used.html的大板块点击进入）
            List<Topic> topics = usedService.findTopicByBoardId(boardId);
            mv.addObject("topics",topics);
            mv.addObject("boardId",boardId);
            if(condition.getTopicId() == null){
                //如果没有topicId，说明是第一次进入category页面，有topicId说明是点击的category页面里的小类别
                condition.setTopicId(topics.get(0).getId());
            }
            mv.setViewName("category");
        }

        if(condition.getUserId() != null){
            //个人中心的话去另一个页面
            mv.setViewName("my_shelf_pro");
        }

        List<Product> pro = usedService.findProByExample(condition);
        //分页
        PageHelper.startPage(pageNum,10);
        PageInfo<Product> pageInfo = new PageInfo<>(pro);
        mv.addObject("pageInfo",pageInfo);
        //主要帮助前台显示商品所属的类别
        Map<Integer, String> map1 = getTopicMap();
        Map<Integer,String> map2 = getTranMap();
        mv.addObject("keyWord",condition.getKeyWord());
        mv.addObject("map",map1);
        mv.addObject("tranMap",map2);
        mv.addObject("boardIid",boardIid);
        mv.addObject("condition",condition);

        return mv;

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
     * 跳转到category页面（通过used.html的大板块点击进入）  注：后面优化没有用到此handle
     * @param boardId
     * @return
     */
    @GetMapping("/toBoardPage/{boardId}")
    public ModelAndView toBoardPage(@PathVariable("boardId") Integer boardId,@RequestParam(value = "tppicId",required = false) Integer topicId){
        ModelAndView mv= new ModelAndView();
        List<Topic> topics = usedService.findTopicByBoardId(boardId);
        if(topicId == null){
            //如果没有传过来topicId，说明是第一次从used.html的大板块点击进入，默认是第一个子类别的商品
            topicId = topics.get(0).getId();
        }

        mv.addObject("topics",topics);
        mv.setViewName("category");
        return mv;
    }

    //跳转到商品详情页面
    @GetMapping("/detailPro/{proId}")
    public ModelAndView detailPro(@PathVariable("proId") Integer proId,HttpServletRequest request){
        //redis存放商品点击数
        redisUtil.zIncrScore("product_watch_count",""+proId,1);
        //商品被观看次数
        Double watchCount = redisUtil.zScore("product_watch_count", "" + proId);

        ModelAndView mv = new ModelAndView();
        Product pro = usedService.getProById(proId);

        List<Emage> imgs = usedService.getImgByProId(proId);
        //商品的图片信息
        mv.addObject("imgs",imgs);
        //商品
        mv.addObject("pro",pro);
        //主要帮助前台显示商品所属的类别
        Map<Integer, String> map1 = getTopicMap();
        mv.addObject("map",map1);
        //上架商品的用户信息,卖家信息
        Person person = personServices.findById(pro.getUserId());
        mv.addObject("person",person);
        //卖家最新上架商品(默认的是按照上架时间排序的)
        List<Product> proByUserId = usedService.findProByUserId(person.getId());
        Product newShelfPro = null;
        if(proByUserId.size() >= 1){
            newShelfPro = proByUserId.get(0);
        }


        //找出该商品的评价留言,以及留言人的姓名
        List<ProductMsg> msgs = usedService.getProMsgByProId(pro.getId(),request);

        Map<Integer, String> users = getAllUsers();
        //找出该商品的运送方式
        Map<Integer, String> tranMap = getTranMap();

        mv.addObject("tranMap",tranMap);
        mv.addObject("msgs",msgs);
        mv.addObject("users",users);
        mv.addObject("newShelfPro",newShelfPro);
        mv.addObject("watchCount",watchCount.intValue());
        mv.setViewName("detail");
        return mv;
    }

    /**
     * 用户给商品留言，评价
     * @param productMsg 留言实体
     * @param flag 留言是否只对买卖双方可见
     * @param request
     * @return
     */
    @PostMapping("/productMsg")
    @ResponseBody
    public String productMsg(ProductMsg productMsg, @RequestParam("flag") boolean flag, HttpServletRequest request){
        HttpSession session = request.getSession();
        Person user =(Person) session.getAttribute("user");
        productMsg.setUserId(user.getId());
        productMsg.setModify(new Date());
        if(flag){
            productMsg.setDisplay(0);
        }else{
            productMsg.setDisplay(1);
        }
        int i = usedService.setProductMsg(productMsg);
        if(i==1){
            return "评价成功";
        }
        return "评价失败";
    }

    /**
     * 获得全部小区
     * @return
     */
    @PostMapping("/getAllVillages")
    @ResponseBody
    public List<Village> getAllVillages(){
        List<Village> villages = usedService.getAllVillages();
        return villages;
    }

    /**
     * 跳转到上架物品界面
     * @return
     */
    @RequestMapping("/toShelfPro")
    public ModelAndView toShelfPro(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("shelfPro");
        return mv;
    }

    /**
     * 跳转到上传商品图片界面
     * @return
     */
    @RequestMapping("/toAddEmage")
    public ModelAndView toAddEmage(@RequestParam("proId") Integer proId){
        ModelAndView mv = new ModelAndView();
        Product pro = usedService.getProById(proId);
        mv.addObject("pro",pro);
        mv.setViewName("addEmage");
        return mv;
    }

    /**
     * 找出所有的交易方式
     * @return
     */
    @PostMapping("/findAllTrans")
    @ResponseBody
    public List<Transaction> findAllTrans(){
        return usedService.getAllTrans();
    }

    /**
     * 上架商品
     * @param file 商品图片
     * @param product 商品
     * @param request
     * @return
     */
    @RequestMapping("/addPro")
    public ModelAndView addPro(@RequestParam("image_f") MultipartFile file,Product product,HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        HttpSession session = request.getSession();
        Person person = (Person) session.getAttribute("user");
        if (!file.isEmpty()){
            String uuid = UUID.randomUUID().toString().replace("-","");
            String contentType = file.getContentType();
            String type = contentType.substring(contentType.indexOf("/")+1);
            String path = "/"+person.getId()+"/"+uuid+"."+type;
            String s = aliyunOSSUtil.upload(file, path);
            if ("success".equals(s)){
                product.setImage(aliConfEntity.getProtocol()+aliConfEntity.getBucketname()+"."+aliConfEntity.getEndpoint()+"/"+aliConfEntity.getFilehost()+path);
            }else {
                product.setImage(aliConfEntity.getNoProAvatar());
            }
        }
        Date date = new Date();
        product.setModify(date);
        product.setShelf(date);
        product.setUserId(person.getId());
        int i = usedService.insertPro(product);
        if (i == 1){
            mv.setViewName("personalInfo");
        }else{
            mv.setViewName("error");
        }
        //redis:添加商品后为商品的   点击数添加进redis
        Integer id = product.getId();
        redisUtil.zSet("product_watch_count",""+id,0,-1);
        return mv;
    }


    /**
     * 跳转编辑商品界面
     * @param proId
     * @return
     */
    @RequestMapping("/editPro")
    public ModelAndView editPro(@RequestParam("proId") Integer proId){
        ModelAndView mv = new ModelAndView();
        Product pro = usedService.getProById(proId);
        Topic topicById = usedService.getTopicById(pro.getTopicId());
        mv.addObject("boardId",topicById.getBoardId());
        mv.addObject("pro",pro);
        mv.setViewName("edit_pro");
        return mv;

    }
    /**
     * 编辑商品
     * @param file
     * @param product
     * @param request
     * @return
     */
    @RequestMapping("/changePro")
    public ModelAndView Pro(@RequestParam(value = "image_f",required = false) MultipartFile file,Product product,HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        HttpSession session = request.getSession();
        Person person = (Person) session.getAttribute("user");

        //重新添加图片
        if (!file.isEmpty()){
            Product pro = usedService.getProById(product.getId());
            //删除oss上原来的image
            String substring = pro.getImage().substring(pro.getImage().indexOf(aliConfEntity.getFilehost()));
            aliyunOSSUtil.dealFile(substring);

            String uuid = UUID.randomUUID().toString().replace("-","");
            String contentType = file.getContentType();
            String type = contentType.substring(contentType.indexOf("/")+1);
            String path = "/"+person.getId()+"/"+uuid+"."+type;
            String s = aliyunOSSUtil.upload(file, path);
            if ("success".equals(s.trim())){
                product.setImage(aliConfEntity.getProtocol()+aliConfEntity.getBucketname()+"."+aliConfEntity.getEndpoint()+"/"+aliConfEntity.getFilehost()+path);
            }else {
                product.setImage(aliConfEntity.getNoProAvatar());
            }
        }
        product.setModify(new Date());
        int i = usedService.updatePro(product);
        if (i == 1){
            mv.setViewName("personalInfo");
        }else{
            mv.setViewName("error");
        }
        return mv;
    }


    /**
     * 添加多张图片进行介绍
     * @param files
     * @param proId
     * @param request
     * @return
     */
    @PostMapping("/addEmage")
    public String addEmage(@RequestParam("image_f") MultipartFile[] files,@RequestParam("proId") Integer proId,HttpServletRequest request){
        HttpSession session = request.getSession();
        Person person = (Person) session.getAttribute("user");
        int i = 0;
        if (files.length != 0){
            for (MultipartFile file : files) {
                if(!file.isEmpty()){
                    String uuid = UUID.randomUUID().toString().replace("-","");
                    String contentType = file.getContentType();
                    //获取后缀名称
                    String type = contentType.substring(contentType.indexOf("/")+1);
                    String path = "/"+person.getId()+"/pro_"+proId+"/"+uuid+"."+type;
                    String s = aliyunOSSUtil.upload(file, path);
                    if ("success".equals(s)){
                        Emage emage = new Emage();
                        emage.setPath(aliConfEntity.getProtocol()+aliConfEntity.getBucketname()+"."+aliConfEntity.getEndpoint()+"/"+aliConfEntity.getFilehost()+path);
                        emage.setProId(proId);
                        emage.setUserId(person.getId());
                        i = usedService.addEmage(emage);
                    }
                }
            }
        }
        return "personalInfo";
    }

    /**
     * 跳转到我的购物车界面
     * @param request
     * @return
     */
    @RequestMapping("/toMyCart")
    public ModelAndView toMyCart(HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        HttpSession session = request.getSession();
        Person user = (Person) session.getAttribute("user");
        List<Map<String, Object>> myCarts = usedService.getMyCart(user.getId());
        Map<Integer, String> tranMap = getTranMap();
        mv.addObject("myCarts",myCarts);
        mv.addObject("tranMap",tranMap);
        mv.setViewName("cart");
        return mv;
    }


    /**
     * 跳转到选择商品规格界面（加入购物车时）
     * @param proId
     * @return
     */
    @RequestMapping("/toCartChooseNum/{proId}")
    public ModelAndView toCartChooseNum(@PathVariable("proId") Integer proId){
        ModelAndView mv = new ModelAndView();
        Product pro = usedService.getProById(proId);
        mv.addObject("pro",pro);
        Map<Integer, String> tranMap = getTranMap();
        mv.addObject("tranMap",tranMap);
        mv.setViewName("cart_choose_num");
        return mv;
    }

    /**
     * 在选择规格界面可以找到卖家的电话和卖家沟通
     * @param proId
     * @return
     */
    @ResponseBody
    @GetMapping("/getMaijiaInfo")
    public String getMaijiaInfo(@RequestParam("proId") Integer proId){
        Product pro = usedService.getProById(proId);
        Person byId = personServices.findById(pro.getUserId());
        String gender;
        if (byId.getGender().equals("男")){
            gender="先生";
        }else {
            gender = "女士";
        }
        String info = byId.getUserName().substring(0,1)+gender+":"+byId.getPhone();
        return info;
    }

    /**
     * 添加到购物车(此处有一个bug，在添加页面刷新，会一直添加)
     * @param cart
     * @param request
     * @return
     */
    @RequestMapping("/addCart")
    public ModelAndView addCart(Cart cart,HttpServletRequest request){
        HttpSession session = request.getSession();
        Person user = (Person) session.getAttribute("user");
        ModelAndView mv= new ModelAndView();
        List<Cart> cart1 = usedService.findCart(cart.getProId(), user.getId());
        Product pro = usedService.getProById(cart.getProId());
        if (pro.getCount() <= 0){
            mv.setViewName("error");
            mv.addObject("errorInfo","商品已被售完");
        }else {
            if (cart1.size() >= 1){
                mv.addObject("errorInfo","您已加购过本商品");
                mv.setViewName("error");
            }else {
                cart.setDisplay(1);
                cart.setUserId(user.getId());
                cart.setModify(new Date());
                int i = usedService.setCart(cart);
                if (i == 1){
                    mv.setViewName("cart");
                }else{
                    mv.setViewName("error");
                }
                Map<Integer, String> tranMap = getTranMap();
                List<Map<String, Object>> myCarts = usedService.getMyCart(user.getId());
                mv.addObject("myCarts",myCarts);
                mv.addObject("tranMap",tranMap);
            }
        }

        return mv;
    }

    /**
     * 从购物车删除商品
     * @param proId
     * @return
     */
    @PostMapping("/removeCart")
    @ResponseBody
    public String removeCart(@RequestParam("proId") Integer proId,HttpServletRequest request){
        HttpSession session = request.getSession();
        Person user = (Person)session.getAttribute("user");
        int i = usedService.removeCart(proId, user.getId());
        if (i == 1){
            return "success";
        }
        return "error";
    }

    /**
     * 添加交易记录
     * @return
     */
    @PostMapping("/addDealRecord")
    @ResponseBody
    public String addDealRecord(@RequestParam("cartList") String json,@RequestParam("cartIds") String cartIdsJson){

        List<BuyedPro> collection = (List<BuyedPro>) JSONArray.toCollection(JSONArray.fromObject(json),BuyedPro.class);
        List<Integer> cartIds = (List<Integer>) JSONArray.toCollection(JSONArray.fromObject(cartIdsJson),Integer.class);
        int f = 0;
        //添加交易记录 并且 将商品的数量更新
        for (BuyedPro dealRecord : collection) {
            dealRecord.setModify(new Date());
            int i = usedService.setBuyedPro(dealRecord);
            Product pro = usedService.getProById(dealRecord.getProId());
            //商品数量为0，则将商品状态调整为售罄
            if (pro.getCount()-dealRecord.getQuantity() == 0){
                pro.setDisplay(3);
            }
            //数量减少
            pro.setCount(pro.getCount()-dealRecord.getQuantity());
            //销量增加
            pro.setSales(pro.getSales()+dealRecord.getQuantity());
            //时间更新
            pro.setModify(new Date());
            usedService.savePro(pro);
            f += i;
        }
        //将购物车相关记录删除
        for (Integer id : cartIds) {
            usedService.removeCart2(id);
        }


        if (f == 0){
            return "error";
        }
        return "success";

    }

    /**
     * 去到交易记录界面
     * @param flag
     * @param request
     * @return
     */
    @RequestMapping("/toMyDealPage")
    public ModelAndView toMyDealPage(@RequestParam(value = "flag",required = false,defaultValue = "1") Integer flag,HttpServletRequest request){
        HttpSession session = request.getSession();
        Person user = (Person) session.getAttribute("user");
        ModelAndView mv = new ModelAndView();
        List<Map<String, Object>> dealRecords = null;
        if (flag == 1){
            dealRecords = usedService.findMyBuyedPro(user.getId());
        }else if (flag == 2){
            dealRecords = usedService.findMySalePro(user.getId());
        }
        Map<Integer, String> tranMap = getTranMap();
        mv.addObject("tranMap",tranMap);
        mv.addObject("dealRecords",dealRecords);
        mv.addObject("flag",flag);
        mv.setViewName("my_deal_record");
        return mv;
    }


}
