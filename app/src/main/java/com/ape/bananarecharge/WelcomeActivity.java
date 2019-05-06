package com.ape.bananarecharge;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.ape.bananarecharge.Controller.GoodsManager;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import Util.URLUtils;

public class WelcomeActivity extends AppCompatActivity {
    private Handler mHandler;
    private int time = 0;
    public static final int COUNTDOWN_TIME_CODE = 99999;
    public static final int DELAY_MILLIS = 1000;
    public static final int MAX_COUNT = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GoodsManager manager = new GoodsManager(this);
        manager.doPostRequest(new HashMap<String, String>(), URLUtils.GOODS_LIST);


//        CountdownTimeHandler handler = new CountdownTimeHandler(this);
//        //新建一个message
//        Message message = Message.obtain();
//        message.what = COUNTDOWN_TIME_CODE;
//        message.arg1 = MAX_COUNT;
//        //第一次发送message
//        handler.sendMessageDelayed(message, DELAY_MILLIS);
    }

    public static class CountdownTimeHandler extends Handler {
        /**
         * 倒计时最小值
         */
        public static final int MIN_COUNT = 0;
        //创建MainActivity弱引用
        final WeakReference<WelcomeActivity> mWeakReference;

        public CountdownTimeHandler(WelcomeActivity activity) {
            this.mWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //获取对WelcomeActivity的弱引用
            WelcomeActivity activity = mWeakReference.get();
            switch (msg.what) {
                case COUNTDOWN_TIME_CODE:
                    int value = msg.arg1;
                    //循环发送消息的控制
                    if (value >= MIN_COUNT) {
                        Message message = Message.obtain();
                        message.what = COUNTDOWN_TIME_CODE;
                        message.arg1 = value;
                        if (value-- <= MAX_COUNT) {
                            Intent intent = new Intent(activity, MainActivity.class);
                            activity.startActivity(intent);
                        }
                        sendMessageDelayed(message, DELAY_MILLIS);
                    }
                    break;
            }
        }
    }
}
