package com.ape.bananarecharge;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ape.bananarecharge.Controller.GoodsManager;

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
    @Override
    public void onReceive(Context context, Intent intent) {
        mOrderId = intent.getStringExtra(Utils.ORDER_ID);
        mMap.put(Utils.ORDER_ID, mOrderId);
        RelativeLayout relativeLayout = Utils.getPayWaitState();
        if (relativeLayout.getVisibility() == View.VISIBLE) {
            Utils.createOrderPay(context, Utils.pay_type, mMap);
            relativeLayout.setVisibility(View.GONE);
        }
        Log.i(TAG, "mOrderId : " + mOrderId);
    }
}
