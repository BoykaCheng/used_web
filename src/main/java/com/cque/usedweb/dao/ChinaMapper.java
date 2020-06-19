package com.cque.usedweb.dao;

import com.cque.usedweb.entity.China;
import com.cque.usedweb.entity.ChinaExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ChinaMapper {
    int countByExample(ChinaExample example);

    int deleteByExample(ChinaExample example);

    int deleteByPrimaryKey(Integer cnId);

    int insert(China record);

    int insertSelective(China record);

    List<China> selectByExample(ChinaExample example);

    China selectByPrimaryKey(Integer cnId);

    int updateByExampleSelective(@Param("record") China record, @Param("example") ChinaExample example);

    int updateByExample(@Param("record") China record, @Param("example") ChinaExample example);

    int updateByPrimaryKeySelective(China record);

    int updateByPrimaryKey(China record);
}