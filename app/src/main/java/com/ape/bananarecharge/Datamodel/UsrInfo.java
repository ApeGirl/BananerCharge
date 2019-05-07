package com.ape.bananarecharge.Datamodel;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;

/**
 * Created by xiaoyue.wang on 2019/4/30.
 */

public class UsrInfo implements Serializable{
    private static final String USRINFO_TABLE = "usrInfo";

    private SharedPreferences mSharedPreferences;
    private Context mContext;
    private String phone;
    private String createTime;
    private String lastTime;
    private int id;
    private String platform;
    private String brisk;

    public UsrInfo(Context context) {
        mContext = context;
        mSharedPreferences = context.getSharedPreferences(USRINFO_TABLE, Context.MODE_PRIVATE);
    }

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

    public void saveUsrInfo(Context context) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences(USRINFO_TABLE, Context.MODE_PRIVATE);
        }
//        Gson gson = new Gson();
//        String json = gson.toJson(usrInfo);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
//        editor.putString("usrinfo", json);
        editor.apply();
    }

    public UsrInfo getUsrInfo(Context context) {
        UsrInfo usrInfo;
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences(USRINFO_TABLE, Context.MODE_PRIVATE);
        }
        String json = mSharedPreferences.getString("usrinfo", null);
        Gson gson = new Gson();
        Type type = new TypeToken<UsrInfo>() {
        }.getType();
        usrInfo = gson.fromJson(json, type);
        return usrInfo;
    }

    public boolean isHasLogin() {
        return (getUsrInfo(mContext).phone != null);
    }
}
