package com.cque.usedweb.dao;

import com.cque.usedweb.entity.UserMsg;
import com.cque.usedweb.entity.UserMsgExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface UserMsgMapper {
    int countByExample(UserMsgExample example);

    int deleteByExample(UserMsgExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserMsg record);

    int insertSelective(UserMsg record);

    List<UserMsg> selectByExampleWithBLOBs(UserMsgExample example);

    List<UserMsg> selectByExample(UserMsgExample example);

    UserMsg selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserMsg record, @Param("example") UserMsgExample example);

    int updateByExampleWithBLOBs(@Param("record") UserMsg record, @Param("example") UserMsgExample example);

    int updateByExample(@Param("record") UserMsg record, @Param("example") UserMsgExample example);

    int updateByPrimaryKeySelective(UserMsg record);

    int updateByPrimaryKeyWithBLOBs(UserMsg record);

    int updateByPrimaryKey(UserMsg record);
}