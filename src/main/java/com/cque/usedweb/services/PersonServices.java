package com.cque.usedweb.services;

import com.cque.usedweb.dao.*;
import com.cque.usedweb.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Chenge on 2020.2.7 15:06
 */
@Service
public class PersonServices {

    @Autowired
    private PersonMapper personMapper;
    @Autowired
    private UserPwMapper userPwMapper;
    @Autowired
    private UserMsgMapper userMsgMapper;
    @Autowired
    VillageMapper villageMapper;
    @Autowired
    ChinaMapper chinaMapper;
    @Autowired
    BuyedProMapper buyedProMapper;

    //查询用户是否已存在（已注册）
    public boolean isUserExist(String username){
        List<Person> users = personMapper.selectByUsername(username);
        if(users.size() == 1){
            return true;
        }
        return false;
    }

    //根据用户名查找用户
    public Person findUserByUserName(String username){
        List<Person> users = personMapper.selectByUsername(username);
        return users.get(0);
    }

    //用户登陆
    public String login(String username,String password){
        List<Person> users = personMapper.selectByUsername(username);
        if(users.size()==1){
            Person user = users.get(0);
            UserPw userPw = userPwMapper.selectById(user.getId());
            if (user.getLocked()==0){
                if (password.equals(userPw.getPassword())){
                    return "success";
                }else{
                    return "用户密码错误，请重试";
                }
            }else{
                return "该用户已被锁定，请联系管理员";
            }
        }
        return "该用户不存在";
    }

    //用户注册
    public String register(Person person,String password){
        List<Person> users = personMapper.selectByUsername(person.getUserName());
        if(users.size()==0){
            //插入用户
            person.setCreateTime(new Date());
            person.setModify(new Date());
            String villageId = person.getVillage();
            Village village = villageMapper.selectByPrimaryKey(Integer.valueOf(villageId));
            person.setVillageId(Integer.valueOf(villageId));
            person.setVillage(village.getVillageName());
            int i = personMapper.insertSelective(person);
            if (i==1){
                //插入用户密码
                List<Person> u = personMapper.selectByUsername(person.getUserName());
                UserPw userPw = new UserPw();
                userPw.setModify(new Date());
                userPw.setPassword(password);
                userPw.setUserId(u.get(0).getId());
                int i1 = userPwMapper.insertSelective(userPw);
                if(i1 == 1){
                    //社区人员数加一
                    /*VillageExample example = new VillageExample();
                    VillageExample.Criteria criteria = example.createCriteria();
                    criteria.andVillageNameEqualTo(u.get(0).getVillage());
                    List<Village> villages = villageMapper.selectByExample(example);
                    Village village = villages.get(0);*/
                    Village village1 = villageMapper.selectByPrimaryKey(u.get(0).getVillageId());
                    village1.setUserNum(village1.getUserNum()+1);
                    villageMapper.updateByPrimaryKeySelective(village1);

                    return "success";

                }else {
                    return "用户密码出现问题";
                }
            }else{
                return "由于未知原因注册失败";
            }
        }
        return "用户名已被注册";
    }

    //找出所有的用户
    public List<Person> findAllUsers(){
        PersonExample example = new PersonExample();
        List<Person> people = personMapper.selectByExample(example);
        return people;
    }

    //找出所有的会员用户
    public List<Person> findAllMember(){
        return personMapper.selectAllMember();
    }

    //通过主键 用户id查询出用户
    public Person findById(Integer userId){
        return personMapper.selectByPrimaryKey(userId);
    }

    //用户给卖家留言
    public int setUserMsg(UserMsg userMsg){
        return userMsgMapper.insertSelective(userMsg);
    }
    //用户个人信息更新
    public int changeUserInfo(Person person){
        return personMapper.updateByPrimaryKeySelective(person);
    }

    //找到留给我的信息用户
    public List<Person> findLeavePerson(Integer userId){
        UserMsgExample example = new UserMsgExample();
        UserMsgExample.Criteria criteria = example.createCriteria();
        criteria.andToUserEqualTo(userId);
        List<UserMsg> userMsgs = userMsgMapper.selectByExampleWithBLOBs(example);
        List<Person> leavePerson = new ArrayList<>();

        for (UserMsg userMsg : userMsgs) {
            Person person = personMapper.selectByPrimaryKey(userMsg.getFromUser());
            if (!leavePerson.contains(person)){
                leavePerson.add(person);
            }
        }
        return leavePerson;
    }

    //找到用户留给我的信息
    public List<UserMsg> findMyMsg(Integer userId,Integer fromUserId){
        UserMsgExample example = new UserMsgExample();
        example.setOrderByClause("time ASC");
        UserMsgExample.Criteria criteria = example.createCriteria();
        criteria.andToUserEqualTo(userId);
        criteria.andFromUserEqualTo(fromUserId);
        criteria.andDisplayEqualTo(1);

        UserMsgExample.Criteria criteria1 = example.createCriteria();
        criteria1.andToUserEqualTo(fromUserId);
        criteria1.andFromUserEqualTo(userId);
        example.or(criteria1);
        List<UserMsg> userMsgs = userMsgMapper.selectByExampleWithBLOBs(example);
        return userMsgs;
    }
    //找到全部的省份
    public List<China> findAllProvinces(){
        ChinaExample example = new ChinaExample();
        ChinaExample.Criteria criteria = example.createCriteria();
        criteria.andCnPidEqualTo(0);
        List<China> provinces = chinaMapper.selectByExample(example);
        return provinces;
    }

    //通过省份找到市
    public List<China> findCityByPro(Integer proId){
        ChinaExample example = new ChinaExample();
        ChinaExample.Criteria criteria = example.createCriteria();
        criteria.andCnPidEqualTo(proId);
        return chinaMapper.selectByExample(example);
    }
    //通过市找到县
    public List<China> findCountyByCid(Integer cityId){
        ChinaExample example = new ChinaExample();
        ChinaExample.Criteria criteria = example.createCriteria();
        criteria.andCnPidEqualTo(cityId);
        return chinaMapper.selectByExample(example);
    }

    //通过Id找到交易记录
    public BuyedPro findBuyedProById(Integer id){
        return buyedProMapper.selectByPrimaryKey(id);
    }


}
