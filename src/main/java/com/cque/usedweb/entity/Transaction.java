package com.cque.usedweb.entity;

import java.util.Date;

public class Transaction {
    private Integer id;

    private Integer num;

    private String tranWay;

    private Date modify;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getTranWay() {
        return tranWay;
    }

    public void setTranWay(String tranWay) {
        this.tranWay = tranWay == null ? null : tranWay.trim();
    }

    public Date getModify() {
        return modify;
    }

    public void setModify(Date modify) {
        this.modify = modify;
    }
}