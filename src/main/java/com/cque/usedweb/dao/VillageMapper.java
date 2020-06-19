package com.cque.usedweb.dao;

import com.cque.usedweb.entity.Village;
import com.cque.usedweb.entity.VillageExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface VillageMapper {
    int countByExample(VillageExample example);

    int deleteByExample(VillageExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Village record);

    int insertSelective(Village record);

    List<Village> selectByExample(VillageExample example);

    Village selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Village record, @Param("example") VillageExample example);

    int updateByExample(@Param("record") Village record, @Param("example") VillageExample example);

    int updateByPrimaryKeySelective(Village record);

    int updateByPrimaryKey(Village record);
}