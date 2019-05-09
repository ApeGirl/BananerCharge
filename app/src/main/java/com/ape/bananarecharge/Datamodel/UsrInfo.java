package com.ape.bananarecharge.Datamodel;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by xiaoyue.wang on 2019/4/30.
 */

public class UsrInfo implements Serializable{
    private String phone;
    private String createTime;
    private String lastTime;
    private int id;
    private String platform;
    private String brisk;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getBrisk() {
        return brisk;
    }

    public void setBrisk(String brisk) {
        this.brisk = brisk;
    }

    @Override
    public String toString() {
        return "UsrInfo{" +
                " phone='" + phone + '\'' +
                ", createTime='" + createTime + '\'' +
                ", lastTime='" + lastTime + '\'' +
                ", id=" + id +
                ", platform='" + platform + '\'' +
                ", brisk='" + brisk + '\'' +
                '}';
    }
}
