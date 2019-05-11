package com.ape.bananarecharge.Controller;

import android.util.Log;

import com.ape.bananarecharge.Datamodel.PayInfo;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2019/5/11.
 */

public class PayManager {
    private static final String TAG = "PayManager";
    private PayInfo mPayInfo;

    public PayManager() {
        mPayInfo = new PayInfo();
    }

    public PayInfo parsePayInfoData(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            parsePayInfo(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mPayInfo;
    }

    private void parsePayInfo(JSONObject object) {
        try {
            mPayInfo.setAppid(object.getString("appid"));
            mPayInfo.setNoncestr(object.getString("noncestr"));
            mPayInfo.setPackageName(object.getString("package"));
            mPayInfo.setPartnerid(object.getString("partnerid"));
            mPayInfo.setPrepayid(object.getString("prepayid"));
            mPayInfo.setSign(object.getString("sign"));
            mPayInfo.setTimestamp(object.getString("timestamp"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
