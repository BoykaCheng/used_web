package com.cque.usedweb.dao;

import com.cque.usedweb.entity.PushPro;
import com.cque.usedweb.entity.PushProExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface PushProMapper {
    int countByExample(PushProExample example);

    int deleteByExample(PushProExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PushPro record);

    int insertSelective(PushPro record);

    List<PushPro> selectByExample(PushProExample example);

    PushPro selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PushPro record, @Param("example") PushProExample example);

    int updateByExample(@Param("record") PushPro record, @Param("example") PushProExample example);

    int updateByPrimaryKeySelective(PushPro record);

    int updateByPrimaryKey(PushPro record);
}