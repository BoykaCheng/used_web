package com.cque.usedweb.dao;

import com.cque.usedweb.entity.Emage;
import com.cque.usedweb.entity.EmageExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface EmageMapper {
    int countByExample(EmageExample example);

    int deleteByExample(EmageExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Emage record);

    int insertSelective(Emage record);

    List<Emage> selectByExample(EmageExample example);

    Emage selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Emage record, @Param("example") EmageExample example);

    int updateByExample(@Param("record") Emage record, @Param("example") EmageExample example);

    int updateByPrimaryKeySelective(Emage record);

    int updateByPrimaryKey(Emage record);
}