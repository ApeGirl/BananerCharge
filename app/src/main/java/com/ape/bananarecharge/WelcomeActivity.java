package com.ape.bananarecharge;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ape.bananarecharge.Controller.GoodsManager;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import Util.URLUtils;

public class WelcomeActivity extends AppCompatActivity {
    public static final int COUNTDOWN_TIME_CODE = 99999;
    private static final int MAX_COUNT = 5;

    private RelativeLayout open_screen;
    private int value = 0;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        open_screen = findViewById(R.id.open_screen);

        initData();
        mHandler = new Handler();
        mHandler.postDelayed(runnable, 1000);
    }

    private void initData() {
        GoodsManager manager = new GoodsManager(this);
        manager.doPostRequest(new HashMap<String, String>(), URLUtils.GOODS_LIST, URLUtils.RequestType.GOODS_LIST);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            value++;
            Log.i("wxy", "value " + value);
            if (value >= 3) {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                mHandler.postDelayed(runnable, 1000);
            }
        }
    };
}
