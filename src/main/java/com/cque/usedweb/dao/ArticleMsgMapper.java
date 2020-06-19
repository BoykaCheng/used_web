package com.cque.usedweb.dao;

import com.cque.usedweb.entity.ArticleMsg;
import com.cque.usedweb.entity.ArticleMsgExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ArticleMsgMapper {
    int countByExample(ArticleMsgExample example);

    int deleteByExample(ArticleMsgExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ArticleMsg record);

    int insertSelective(ArticleMsg record);

    List<ArticleMsg> selectByExampleWithBLOBs(ArticleMsgExample example);

    List<ArticleMsg> selectByExample(ArticleMsgExample example);

    ArticleMsg selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ArticleMsg record, @Param("example") ArticleMsgExample example);

    int updateByExampleWithBLOBs(@Param("record") ArticleMsg record, @Param("example") ArticleMsgExample example);

    int updateByExample(@Param("record") ArticleMsg record, @Param("example") ArticleMsgExample example);

    int updateByPrimaryKeySelective(ArticleMsg record);

    int updateByPrimaryKeyWithBLOBs(ArticleMsg record);

    int updateByPrimaryKey(ArticleMsg record);
}