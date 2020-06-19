package com.cque.usedweb.controller;

import com.cque.usedweb.dao.TransactionMapper;
import com.cque.usedweb.entity.*;
import com.cque.usedweb.services.PersonServices;
import com.cque.usedweb.services.UsedService;
import com.cque.usedweb.util.AliyunOSSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Chenge on 2020.2.7 14:27
 */
@Controller
public class PersonController {

    @Autowired
    private PersonServices personServices;
    @Autowired
    private UsedService usedService;
    @Autowired
    private AliConfEntity aliConfEntity;
    @Autowired
    private AliyunOSSUtil aliyunOSSUtil;
    @Autowired
    private TransactionMapper transactionMapper;

    @RequestMapping("/index")
    public ModelAndView toIndex(){
        ModelAndView mv = new ModelAndView();

        mv.addObject("msg","success");
        List<Map<String,Object>> dealNumMan = usedService.findUserOrderByDealNum();
        mv.addObject("dealNumMan",dealNumMan);
        mv.setViewName("index");
        return mv;
    }

    /**
     * 用户登录
     * @param username
     * @param password
     * @param request
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletRequest request){
        String login = personServices.login(username, password);
        if("success".equals(login)){
            Person user = personServices.findUserByUserName(username);
            HttpSession session = request.getSession();
            session.setAttribute("user",user);
        }
        return login;
    }

    /**
     * 用户注销
     * @param request
     * @return
     */
    @PostMapping("/logout")
    @ResponseBody
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        return "success";
    }

    /**
     * 用户注册
     * @param person
     * @param regPassword
     * @return
     */
    @RequestMapping("/register")
    @ResponseBody
    public String register(Person person, @RequestParam("password") String regPassword, @RequestParam("file") MultipartFile file,HttpServletRequest request)throws Exception{
        if (!(file.isEmpty())){
            final String oriName = file.getOriginalFilename().toLowerCase();
            //判断上传文件的类型
            try {
                BufferedImage image = ImageIO.read(file.getInputStream());
                if (image==null){
                    return "上传的不是图片";
                }
            } catch (IOException e) {
                e.printStackTrace();
            };
            //上传头像至oss
            HttpSession session = request.getSession();
            String uuid = UUID.randomUUID().toString().replace("-","");
            String contentType = file.getContentType();
            //获取后缀名称
            String type = contentType.substring(contentType.indexOf("/")+1);
            String path = "/avatar/"+uuid+"."+type;
            String s = aliyunOSSUtil.upload(file, path);
            if ("success".equals(s)){
                person.setAvatar(aliConfEntity.getProtocol()+aliConfEntity.getBucketname()+"."+aliConfEntity.getEndpoint()+"/"+aliConfEntity.getFilehost()+path);
            }
        }else {
            person.setAvatar(aliConfEntity.getProtocol()+aliConfEntity.getDefaultAvatar());
        }
        String register = personServices.register(person, regPassword);
        return register;
    }

    /**
     * 用户未登录的情况重新跳转带主页
     * @return
     */
    @RequestMapping("/notLogin")
    public ModelAndView notLogin(){
        ModelAndView m = new ModelAndView();
        m.setViewName("index");
        m.addObject("msg","您还未登录");
        return m;
    }

    /**
     * 用户未登录的情况重新跳转带主页
     * @return
     */
    @RequestMapping("/notAdmin")
    public ModelAndView notAdmin(){
        ModelAndView m = new ModelAndView();
        m.setViewName("index");
        m.addObject("msg","请以管理员身份访问");
        return m;
    }

    /**
     * 用户给卖家留言
     * @param userMsg 留言实体
     * @return
     */
    @PostMapping("/userMsg")
    @ResponseBody
    public String userMsg(UserMsg userMsg){
        userMsg.setTime(new Date());
        int i = personServices.setUserMsg(userMsg);
        if (i == 1){
            return "留言成功";
        }
        return "留言失败，请重试";
    }

    /**
     * 跳转到个人中心
     * @return
     */
    @RequestMapping("/toPersonalCenter")
    public ModelAndView toPersonalCenter(){
        ModelAndView mv= new ModelAndView();
        mv.setViewName("personalInfo");
        return mv;
    }

    /**
     * 用户信息更新
     * @param person 更新信息实体
     * @param request
     * @return
     */
    @PostMapping("/updateUserInfo")
    @ResponseBody
    public String updateUserInfo(Person person,HttpServletRequest request) throws IllegalAccessException {
        HttpSession session = request.getSession();
        Person person1 = (Person)session.getAttribute("user");
        person.setId(person1.getId());
        //通过反射将对象中空字符串变为null
        Field[] fields = person.getClass().getDeclaredFields();
        for (Field f : fields){
            f.setAccessible(true);
            if ("".equals(f.get(person))){
                f.set(person,null);
            }
        }
        //更新修改时间
        person.setModify(new Date());
        int i = personServices.changeUserInfo(person);
        if (i==1){
            Person person2 = personServices.findById(person1.getId());
            session.setAttribute("user",person2);
            return "用户信息更新成功";
        }
        return "更新失败，请重试";
    }

    /**
     * 跳转到我的留言信箱
     * @return
     */
    @RequestMapping("/toMsgBox")
    public ModelAndView toMsgBox(HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        HttpSession session = request.getSession();
        Person user = (Person)session.getAttribute("user");
        List<Person> leavePersons = personServices.findLeavePerson(user.getId());
        mv.addObject("leavePersons",leavePersons);
        mv.setViewName("message_box");
        return mv;
    }

    /**
     * 点击某一个用户得到我们的聊天记录
     * @param fromUserId
     * @return
     */
    @PostMapping("/getMyMsg")
    @ResponseBody
    public List<UserMsg> getMyMsg(HttpServletRequest request,@RequestParam("fromUserId") Integer fromUserId){
        HttpSession session = request.getSession();
        Person user = (Person) session.getAttribute("user");
        List<UserMsg> myMsgs = personServices.findMyMsg(user.getId(), fromUserId);
        return myMsgs;
    }

    /**
     * 三级联动，省市县
     * @return
     */
    @GetMapping("/getAllProvinces")
    @ResponseBody
    public List<China> getAllProvinces(){
        return personServices.findAllProvinces();
    }

    @GetMapping("/getCitys/{proId}")
    @ResponseBody
    public List<China> getCitys(@PathVariable("proId") Integer proId){
        return personServices.findCityByPro(proId);
    }

    @GetMapping("/getCountys/{cId}")
    @ResponseBody
    public List<China> getCountys(@PathVariable("cId") Integer cId){
        return personServices.findCountyByCid(cId);
    }

    /**
     * 查看双方的地图位置
     * @param dealId 交易记录ID
     * @return
     */
    @RequestMapping("/getWay/{dealId}")
    public ModelAndView getWay(@PathVariable("dealId") Integer dealId){
       ModelAndView mv = new ModelAndView();
        BuyedPro deal = personServices.findBuyedProById(dealId);
        Integer fromUserId = deal.getFromUserId();
        Integer toUserId = deal.getUserId();
        Person from = personServices.findById(fromUserId);
        Person to = personServices.findById(toUserId);
        Village fromV = usedService.getVillageById(from.getVillageId());
        Village toV = usedService.getVillageById(to.getVillageId());
        mv.addObject("fromV",fromV);
        mv.addObject("toV",toV);
        mv.setViewName("map");
        return mv;
    }
}
