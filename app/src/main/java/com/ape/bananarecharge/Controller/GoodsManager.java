package com.ape.bananarecharge.Controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.EditText;

import com.ape.bananarecharge.Datamodel.GoodsInfo;
import com.ape.bananarecharge.Datamodel.PayInfo;
import com.ape.bananarecharge.Datamodel.UsrInfo;
import com.ape.bananarecharge.OkHttpClientInstance;
import com.ape.bananarecharge.PayActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Util.MapUtil;
import Util.URLUtils;
import Util.Utils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by xiaoyue.wang on 2019/5/5.
 */

public class GoodsManager {
    private static final String TAG = "GoodsManager";
    private Context mContext;
    private String mRequestBody;
    private ArrayList<GoodsInfo> mGoodsList;
    private UsrInfo mUsrInfo;
    private UsrMananger mUsrMananger;
    private String mOrderId;

    public GoodsManager(Context context) {
        mContext = context;
    }

    public void getHttp(final String url) {
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mRequestBody = response.body().string();
                String code = json2String(mRequestBody, "code");
                Log.d(TAG, "onResponse: " + mRequestBody + "    code : " + code);
            }
        });
    }

    private String json2String(String jsonString, String param) {
        String result = null;
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            result = jsonObject.getString(param);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void doPostRequest(Map<String, String> map, String url, final URLUtils.RequestType type) {
        String skey = map.keySet().toString();
        String sValue = map.values().toString();
        String key[] = skey.substring(1, skey.length() - 1).split(",");
        String value[] = sValue.substring(1, sValue.length() - 1).split(",");

        Set set = map.keySet();
        Iterator iter = set.iterator();
        FormBody.Builder builder = new FormBody.Builder();
        while (iter.hasNext()) {
            String k = (String) iter.next();
            builder.add(k, map.get(k));
        }
//        for (int i = 0; i < key.length; i++) {
//            builder.add(key[i].trim().replaceAll("\\s", ""), value[i].trim().replaceAll("\\s", ""));
//            Log.i(TAG, "i : " + "    key : " + key[i].replaceAll("\\s", "") + "   value :  " + value[i].replaceAll("\\s", ""));
//        }
        RequestBody body = builder.build();
        OkHttpClient client = OkHttpClientInstance.getInstance();
        final Request request = new Request.Builder()
                .addHeader("Connection", "close")
                .url(url)
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, " onFailure : " + call.toString() + " IOException : " + e.toString());
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                mRequestBody = response.body().string();
                String msg = json2String(mRequestBody, "msg");
                String data = json2String(mRequestBody, "data");
                Log.d(TAG, "onResponse: " + mRequestBody + "    msg : " + msg + "    data : " + data);

                if (msg.equals("success")) {
                    responseDeal(data, type);
                }
            }
        });
    }
    private static final String INFO_LIST = "info_list";
    private static final String GOODS_INFO = "goods_info";
    private void responseDeal(String data, URLUtils.RequestType type) {
        switch (type) {
            case LOGIN:
                mUsrMananger = new UsrMananger(mContext);
                mUsrInfo = mUsrMananger.parseUsrInfoData(data);
                break;
            case GOODS_LIST:
                mGoodsList = parseArrayData(data);
                SharedPreferences sharedPreferences = mContext.getSharedPreferences(GOODS_INFO, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(INFO_LIST, Utils.Object2String(mGoodsList, TAG));
                editor.apply();

                Bundle bundle = new Bundle();
                bundle.putSerializable("list", mGoodsList);
                Intent intent = new Intent(URLUtils.ACTION_REQUEST_SUCCESS_RECEIVER);
                intent.putExtras(bundle);
                LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(mContext);
                localBroadcastManager.sendBroadcast(intent);

                Log.i(TAG, "mGoodsList : " + mGoodsList);
                break;
            case GOODS_INFO:

                break;
            case CREAT_ORDER:
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    mOrderId = jsonObject.getString("orderid");
                    Utils.setOrderId(mOrderId);
                    Log.i(TAG, "orderId : " + mOrderId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case ALI_PAY:

                break;
            case ALI_ORDER_PAY:
                break;
            case WECHAT_ORDER_PAY:
                PayManager payManager = new PayManager();
                PayInfo payInfo = payManager.parsePayInfoData(data);
                Log.i(TAG, "wachat pay payInfo : " + payInfo);
                Bundle payBundle = new Bundle();
                payBundle.putSerializable(Utils.PAY_BUNDLE_KEY, payInfo);
                Intent payIntent = new Intent(URLUtils.ACTION_CREATE_ORDER_RECEIVER);
                payIntent.putExtras(payBundle);
                LocalBroadcastManager payBroadcastManager = LocalBroadcastManager.getInstance(mContext);
                payBroadcastManager.sendBroadcast(payIntent);
                break;
            default:
                break;
        }
    }

    public String getOrderId() {
        return mOrderId;
    }

    private ArrayList<GoodsInfo> parseArrayData(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray jsonArray = jsonObject.getJSONArray("list");
            ArrayList<GoodsInfo> list = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                GoodsInfo goodsInfo = getGoodsInfo(object);
                list.add(goodsInfo);
            }

            for (GoodsInfo info : list) {
                Log.i(TAG, info.toString());
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private GoodsInfo getGoodsInfo(JSONObject object) {
        GoodsInfo goodsInfo = new GoodsInfo();
        try {
            goodsInfo.setTitle(object.getString("title"));
            goodsInfo.setPicUrl(object.getString("pic"));
            goodsInfo.setGoddsid(object.getInt("goddsid"));
            goodsInfo.setIndexPicUrl(object.getString("indexPic"));

            goodsInfo.setStepsStr(object.getString("stepsStr"));
            goodsInfo.setPrice(object.getDouble("price"));
            goodsInfo.setShaPrice(object.getDouble("shaPrice"));
            goodsInfo.setTopUpWay(object.getInt("topUpWay"));
            goodsInfo.setExchangeAddress(object.getString("exchangeAddress"));
            goodsInfo.setCreateTime(object.getString("createTime"));
            goodsInfo.setStatus(object.getInt("status"));
            goodsInfo.setShareUrl(object.getString("shareUrl"));
            goodsInfo.setShareTitle(object.getString("shareTitle"));
            goodsInfo.setShareDesc(object.getString("shareDesc"));
            goodsInfo.setPrompt(object.getString("prompt"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return goodsInfo;
    }
}
