package com.cque.usedweb.dao;

import com.cque.usedweb.entity.ProductMsg;
import com.cque.usedweb.entity.ProductMsgExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ProductMsgMapper {
    int countByExample(ProductMsgExample example);

    int deleteByExample(ProductMsgExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProductMsg record);

    int insertSelective(ProductMsg record);

    List<ProductMsg> selectByExampleWithBLOBs(ProductMsgExample example);

    List<ProductMsg> selectByExample(ProductMsgExample example);

    ProductMsg selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProductMsg record, @Param("example") ProductMsgExample example);

    int updateByExampleWithBLOBs(@Param("record") ProductMsg record, @Param("example") ProductMsgExample example);

    int updateByExample(@Param("record") ProductMsg record, @Param("example") ProductMsgExample example);

    int updateByPrimaryKeySelective(ProductMsg record);

    int updateByPrimaryKeyWithBLOBs(ProductMsg record);

    int updateByPrimaryKey(ProductMsg record);
}