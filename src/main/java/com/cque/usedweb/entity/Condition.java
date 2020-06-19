package com.cque.usedweb.entity;

/**
 * Created by Chenge on 2020.2.15 12:56
 * 自定义的模糊查询条件包装，配合example类
 */
public class Condition {
    private Integer topicId;
    private String keyWord;
    private Integer level;
    private String columnSort;
    private boolean flag;
    private Integer userId;

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getColumnSort() {
        return columnSort;
    }

    public void setColumnSort(String columnSort) {
        this.columnSort = columnSort;
    }

    public boolean getFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Condition{" +
                "topicId=" + topicId +
                ", keyWord='" + keyWord + '\'' +
                ", level=" + level +
                ", columnSort='" + columnSort + '\'' +
                ", flag=" + flag +
                ", userId=" + userId +
                '}';
    }
}
