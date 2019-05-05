package com.ape.bananarecharge.Datamodel;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by xiaoyue.wang on 2019/4/30.
 */

public class UsrInfo {
    private static final String USRINFO_TABLE = "usrInfo";
    private static final String USR_NAME = "usrName";
    private static final String USR_PSW = "usrPsw";

    private SharedPreferences mSharedPreferences;
    private Context mContext;

    public UsrInfo(Context context) {
        mContext = context;
        mSharedPreferences = context.getSharedPreferences(USRINFO_TABLE,Context.MODE_PRIVATE);
    }

    public void setUsrName(Context context, String usrName) {
        setUsrInfo(context, USR_NAME, usrName);
    }

    public void setUsrPsw(Context context, String psw) {
        setUsrInfo(context, USR_PSW, psw);
    }

    public String getUsrName(Context context) {
        return getUsrInfo(context, USR_NAME);
    }

    public String getUsrPsw(Context context) {
        return getUsrInfo(context, USR_PSW);
    }

    private void setUsrInfo(Context context, String key, String value) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences(USRINFO_TABLE,Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    private String getUsrInfo(Context context, String key) {
        String usrInfo;
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences(USRINFO_TABLE,Context.MODE_PRIVATE);
        }
        usrInfo = mSharedPreferences.getString(key, null);
        return usrInfo;
    }

    public boolean isHasLogin() {
        return (getUsrName(mContext) != null) && (getUsrPsw(mContext) != null);
    }
}
