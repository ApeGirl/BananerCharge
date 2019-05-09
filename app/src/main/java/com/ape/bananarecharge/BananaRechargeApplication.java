package com.ape.bananarecharge;

import android.app.Application;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by xiaoyue.wang on 2019/5/7.
 */

public class BananaRechargeApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
