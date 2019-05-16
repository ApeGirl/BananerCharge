package com.ape.bananarecharge;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.nfc.Tag;
import android.util.Log;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import static com.umeng.socialize.utils.DeviceConfig.context;

/**
 * Created by xiaoyue.wang on 2019/5/7.
 */

public class BananaRechargeApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        UMConfigure.setLogEnabled(true);
        UMConfigure.setEncryptEnabled(true);

        UMConfigure.init(this, "5cdbaa570cafb24b32000fb8", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, null);
        PlatformConfig.setWeixin("wx18d7dfac27c65f01", "924f17ff635f1ea650cf2e009c37e5c9");
    }
}
