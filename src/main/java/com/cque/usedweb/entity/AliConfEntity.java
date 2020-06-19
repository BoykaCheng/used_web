package com.cque.usedweb.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by Chenge on 2020.5.18 12:15
 */
@Component
public class AliConfEntity {
    @Value("${userImage.endpoint}")
    private   String endpoint;
    @Value("${userImage.keyid}")
    private  String keyid;
    @Value("${userImage.keysecret}")
    private  String keysecret;
    @Value("${userImage.filehost}")
    private  String filehost;
    @Value("${userImage.bucketname}")
    private  String bucketname;
    @Value("${userImage.default-avatar}")
    private String defaultAvatar;
    @Value("${userImage.protocol}")
    private String protocol;
    @Value("${userImage.no-pro-avatar}")
    private String noProAvatar;

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getKeysecret() {
        return keysecret;
    }

    public void setKeysecret(String keysecret) {
        this.keysecret = keysecret;
    }

    public String getFilehost() {
        return filehost;
    }

    public void setFilehost(String filehost) {
        this.filehost = filehost;
    }

    public String getBucketname() {
        return bucketname;
    }

    public void setBucketname(String bucketname) {
        this.bucketname = bucketname;
    }

    public String getDefaultAvatar() {
        return defaultAvatar;
    }

    public void setDefaultAvatar(String defaultAvatar) {
        this.defaultAvatar = defaultAvatar;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getNoProAvatar() {
        return noProAvatar;
    }

    public void setNoProAvatar(String noProAvatar) {
        this.noProAvatar = noProAvatar;
    }

    @Override
    public String toString() {
        return "AliConfEntity{" +
                "endpoint='" + endpoint + '\'' +
                ", keyid='" + keyid + '\'' +
                ", keysecret='" + keysecret + '\'' +
                ", filehost='" + filehost + '\'' +
                ", bucketname='" + bucketname + '\'' +
                ", defaultAvatar='" + defaultAvatar + '\'' +
                ", protocol='" + protocol + '\'' +
                ", noProAvatar='" + noProAvatar + '\'' +
                '}';
    }
}
