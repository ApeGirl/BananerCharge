package com.ape.bananarecharge;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ape.bananarecharge.Controller.GoodsManager;
import com.ape.bananarecharge.Datamodel.PayInfo;

import java.util.HashMap;
import java.util.Map;

import Util.URLUtils;
import Util.Utils;

/**
 * Created by xiaoyue.wang on 2019/5/9.
 */

public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "MyReceiver";
    private String mOrderId;
    private Map<String, String> mMap = new HashMap<>();
    private String mUrl;

    @Override
    public void onReceive(Context context, Intent intent) {
        PayInfo payInfo = (PayInfo) intent.getExtras().getSerializable(Utils.PAY_BUNDLE_KEY);
        Utils.setPayInfo(payInfo);
        GoodsManager goodsManager = new GoodsManager(context);
        Map<String, String> map = new HashMap<>();
        RelativeLayout relativeLayout = Utils.getPayWaitState();

        if (Utils.pay_type == Utils.Ali_PAY) {
            mUrl = URLUtils.ALI_PAY;
        } else if (Utils.pay_type == Utils.WACHAT_PAY) {
            mUrl = URLUtils.ORDER_WECHAT_PAY;
        }
        if (relativeLayout != null && relativeLayout.getVisibility() == View.VISIBLE) {
            relativeLayout.setVisibility(View.GONE);
            goodsManager.doPostRequest(map, mUrl, URLUtils.RequestType.ALI_PAY);
        }
        Log.d(TAG, "payInfo : " + payInfo);
    }
}
