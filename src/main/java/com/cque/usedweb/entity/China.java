package com.cque.usedweb.entity;

public class China {
    private Integer cnId;

    private String cnName;

    private Integer cnPid;

    public Integer getCnId() {
        return cnId;
    }

    public void setCnId(Integer cnId) {
        this.cnId = cnId;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName == null ? null : cnName.trim();
    }

    public Integer getCnPid() {
        return cnPid;
    }

    public void setCnPid(Integer cnPid) {
        this.cnPid = cnPid;
    }
}