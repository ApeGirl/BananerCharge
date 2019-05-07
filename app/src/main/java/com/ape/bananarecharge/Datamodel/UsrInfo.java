package com.ape.bananarecharge.Datamodel;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Created by xiaoyue.wang on 2019/4/30.
 */

public class UsrInfo {
    private static final String USRINFO_TABLE = "usrInfo";

    private SharedPreferences mSharedPreferences;
    private Context mContext;
    private String mUseName;
    private String mPsw;

    public UsrInfo(Context context) {
        mContext = context;
        mSharedPreferences = context.getSharedPreferences(USRINFO_TABLE, Context.MODE_PRIVATE);
    }

    public String getmUseName() {
        return mUseName;
    }

    public void setmUseName(String mUseName) {
        this.mUseName = mUseName;
    }

    public String getmPsw() {
        return mPsw;
    }

    public void setmPsw(String mPsw) {
        this.mPsw = mPsw;
    }

    public void setUsrInfo(Context context, UsrInfo usrInfo) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences(USRINFO_TABLE, Context.MODE_PRIVATE);
        }
        Gson gson = new Gson();
        String json = gson.toJson(usrInfo);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("usrinfo", json);
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
        return false;
    }
}
