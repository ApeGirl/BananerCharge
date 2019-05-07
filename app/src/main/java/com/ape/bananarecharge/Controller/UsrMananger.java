package com.ape.bananarecharge.Controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

import com.ape.bananarecharge.Datamodel.GoodsInfo;
import com.ape.bananarecharge.Datamodel.UsrInfo;
import com.ape.bananarecharge.Login.LoginActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

import Util.Utils;

/**
 * Created by xiaoyue.wang on 2019/4/30.
 */

public class UsrMananger {
    private static final String TAG = "UsrMananger";
    private static final String USRINFO_TABLE = "usrInfo";
    private UsrInfo mUsrInfo;
    private Context mContext;

    public UsrMananger(Context context) {
        mUsrInfo = new UsrInfo(context);
        mContext = context;
    }

    public UsrInfo parseUsrInfoData(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            setUserInfo(jsonObject);
            saveUsrInfo(mContext, mUsrInfo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mUsrInfo;
    }

    private void saveUsrInfo(Context context, UsrInfo usrInfo) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USRINFO_TABLE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("usrinfo", Utils.Object2String(usrInfo, TAG));
        editor.apply();
    }

    public UsrInfo getUsrInfoFromDataBase(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USRINFO_TABLE, Context.MODE_PRIVATE);
        String info = sharedPreferences.getString("usrinfo", null);
        if (info == null) {
            return null;
        }

        return (UsrInfo) Utils.String2Object(info);
    }




    private void setUserInfo(JSONObject object) {
        try {
            mUsrInfo.setId(object.getInt("id"));
            mUsrInfo.setPhone(object.getString("phone"));
            mUsrInfo.setBrisk(object.getString("brisk"));
            mUsrInfo.setCreateTime(object.getString("createTime"));
            mUsrInfo.setLastTime(object.getString("lastTime"));
            mUsrInfo.setPlatform(object.getString("platform"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private UsrInfo getUserInfo() {
        return mUsrInfo;
    }
}
