package com.cque.usedweb.entity;

import java.util.Date;

public class ProductMsg {
    private Integer id;

    private Date modify;

    private Integer proId;

    private Integer display;

    private Integer userId;

    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getModify() {
        return modify;
    }

    public void setModify(Date modify) {
        this.modify = modify;
    }

    public Integer getProId() {
        return proId;
    }

    public void setProId(Integer proId) {
        this.proId = proId;
    }

    public Integer getDisplay() {
        return display;
    }

    public void setDisplay(Integer display) {
        this.display = display;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    @Override
    public String toString() {
        return "ProductMsg{" +
                "id=" + id +
                ", modify=" + modify +
                ", proId=" + proId +
                ", display=" + display +
                ", userId=" + userId +
                ", content='" + content + '\'' +
                '}';
    }
}