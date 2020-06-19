package com.cque.usedweb.dao;

import com.cque.usedweb.entity.BuyedPro;
import com.cque.usedweb.entity.BuyedProExample;
import com.cque.usedweb.entity.ManDealNum;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface BuyedProMapper {
    int countByExample(BuyedProExample example);

    int deleteByExample(BuyedProExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BuyedPro record);

    int insertSelective(BuyedPro record);

    List<BuyedPro> selectByExample(BuyedProExample example);

    BuyedPro selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BuyedPro record, @Param("example") BuyedProExample example);

    int updateByExample(@Param("record") BuyedPro record, @Param("example") BuyedProExample example);

    int updateByPrimaryKeySelective(BuyedPro record);

    int updateByPrimaryKey(BuyedPro record);

    List<ManDealNum> selectOrderByDealNum();
}