package com.ape.bananarecharge.Controller;

import android.content.Context;
import android.util.Log;

import com.ape.bananarecharge.Datamodel.GoodsInfo;
import com.ape.bananarecharge.Datamodel.UsrInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by xiaoyue.wang on 2019/4/30.
 */

public class UsrMananger {
    private static final String TAG = "UsrMananger";

    public static void Login(Context context) {
        UsrInfo usrInfo = new UsrInfo(context);
        if (usrInfo.isHasLogin()) {

        }
    }

    private ArrayList<UsrInfo> parseUsrInfoData(Context context, String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray jsonArray = jsonObject.getJSONArray("list");
            ArrayList<UsrInfo> list = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                UsrInfo usrInfo = new UsrInfo(context);
                list.add(usrInfo);
            }

            for (UsrInfo info : list) {
                Log.i(TAG, info.toString());
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private UsrInfo getUserInfo(Context context, JSONObject object) {
        UsrInfo usrInfo = new UsrInfo(context);
//        try {
//          usrInfo.setmUseName("");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        return usrInfo;
    }
}
