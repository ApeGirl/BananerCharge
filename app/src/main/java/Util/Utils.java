package Util;

import android.content.Intent;
import android.util.Base64;
import android.util.Log;

import com.ape.bananarecharge.Datamodel.GoodsInfo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by xiaoyue.wang on 2019/5/7.
 */

public class Utils {
    public static final String GOODS_INFO = "goods_info";
    public static final String BUY_COUNT = "buy_count";
    public static final String UER_ACCOUNT = "usr_account";
    public static final String BUY_TYPE = "usr_account";

    public static final int NO_TYPE = -1;
    public static final int DIRECT_BUY = 1;
    public static final int SHARE_BUY = 2;
    public static final int WACHAT_PAY = 3;
    public static final int Ali_PAY = 4;

    private static final String TAG = "Utils2";
    public static String Object2String(Object object, String activity) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            String string = new String(Base64.encode(byteArrayOutputStream.toByteArray(), Base64.DEFAULT));
            objectOutputStream.close();
            Log.i(TAG, "string  : " + string + " activity : " + activity);
            return string;
        } catch (IOException e) {
            Log.i(TAG, "IOException : " + e.toString() + " activity : " + activity);
            e.printStackTrace();
            return null;
        }
    }

    public static Object String2Object(String objectString) {
        if (objectString == null) {
            return null;
        }
        byte[] mobileBytes = Base64.decode(objectString.getBytes(), Base64.DEFAULT);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(mobileBytes);
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Object object = objectInputStream.readObject();
            objectInputStream.close();
            Log.i(TAG, "object : " + object);
            return object;
        } catch (Exception e) {
            Log.i(TAG, "Exception : " + e.toString());
            e.printStackTrace();
            return null;
        }

    }

}


