package com.cque.usedweb.dao;

import com.cque.usedweb.entity.UserPw;
import com.cque.usedweb.entity.UserPwExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface UserPwMapper {
    int countByExample(UserPwExample example);

    int deleteByExample(UserPwExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserPw record);

    int insertSelective(UserPw record);

    List<UserPw> selectByExample(UserPwExample example);

    UserPw selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserPw record, @Param("example") UserPwExample example);

    int updateByExample(@Param("record") UserPw record, @Param("example") UserPwExample example);

    int updateByPrimaryKeySelective(UserPw record);

    int updateByPrimaryKey(UserPw record);

    UserPw selectById(Integer userId);
}