package com.cque.usedweb.entity;

/**
 * Created by Chenge on 2020.5.7 9:03
 */
public class ManDealNum {
    private Integer userId;
    private Integer dealNums;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getDealNums() {
        return dealNums;
    }

    public void setDealNums(Integer dealNums) {
        this.dealNums = dealNums;
    }

    @Override
    public String toString() {
        return "ManDealNum{" +
                "userId=" + userId +
                ", dealNums=" + dealNums +
                '}';
    }
}
