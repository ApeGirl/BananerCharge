package Util;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ape.bananarecharge.Controller.GoodsManager;
import com.ape.bananarecharge.Datamodel.GoodsInfo;
import com.ape.bananarecharge.Datamodel.PayInfo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

/**
 * Created by xiaoyue.wang on 2019/5/7.
 */

public class Utils {
    public static final String GOODS_INFO = "goods_info";
    public static final String BUY_COUNT = "buy_count";
    public static final String UER_ACCOUNT = "usr_account";
    public static final String BUY_TYPE = "usr_account";
    public static final String ORDER_ID = "orderid";
    public static final String PAY_BUNDLE_KEY = "pay_info";

    public static final int NO_TYPE = -1;
    public static final int DIRECT_BUY = 1;
    public static final int SHARE_BUY = 2;
    public static final int WACHAT_PAY = 3;
    public static final int Ali_PAY = 4;
    public static int pay_type = -1;

    private static RelativeLayout state;
    private static final String TAG = "Utils2";
    private static String mOrderId;
    private static PayInfo mPayInfo;

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

    public static void createOrderPay(Context context, int type, Map map){
        GoodsManager goodsManager = new GoodsManager(context);
        if (type == WACHAT_PAY) {
            goodsManager.doPostRequest(map, URLUtils.ORDER_WECHAT_PAY, URLUtils.RequestType.WECHAT_ORDER_PAY);
        } else if (type == Ali_PAY) {
            goodsManager.doPostRequest(map, URLUtils.ORDER_ALIPAY, URLUtils.RequestType.ALI_ORDER_PAY);
        } else {
            Looper.prepare();
            Toast.makeText(context, "请选择支付方式", Toast.LENGTH_SHORT).show();
            Looper.loop();
        }
    }

    public static void setPayWaitLayout(RelativeLayout progressBar) {
        state = progressBar;
    }

    public static RelativeLayout getPayWaitState() {
        return state;
    }

    public static PayInfo getPayInfo() {
        return mPayInfo;
    }

    public static void setPayInfo(PayInfo mPayInfo) {
        Utils.mPayInfo = mPayInfo;
    }

    public static String getOrderId() {
        return mOrderId;
    }

    public static void setOrderId(String mOrderId) {
        Utils.mOrderId = mOrderId;
    }
}


