package Util;

/**
 * Created by xiaoyue.wang on 2019/4/30.
 */

public class URLUtils {
    private static final String TAG = "URLUtils";
    public static final String LOGIN_URL = "http://39.97.180.130:8080/renren-fast/app/login";
    public static final String GOODS_LIST = "http://39.97.180.130:8080/renren-fast/app/goods/listPage";
    public static final String GET_VERTIFICATION_CODE = "http://39.97.180.130:8080/renren-fast/app/sendcode";
    public static final String GOODS_INFO = "http://39.97.180.130:8080/renren-fast/app/goods/info";
    public static final String ORDER_ALIPAY = "http://39.97.180.130:8080/renren-fast/app/order/aliPay";
    private static final String ORDER_ALI_SUCCESS = "http://39.97.180.130:8080/renren-fast/app/order/aliSuccess";
    public static final String CREATE_ORDER = "http://39.97.180.130:8080/renren-fast/app/order/createOrder";
    public static final String ORDER_INFO = "http://39.97.180.130:8080/renren-fast/app/order/info";
    public static final String ORDER_LIST = "http://39.97.180.130:8080/renren-fast/app/order/listPage";
    public static final String ORDER_WECHAT_PAY = "http://39.97.180.130:8080/renren-fast/app/order/wechatPay";
    public static final String ORDER_WECHAT_SUCCESS = "http://39.97.180.130:8080/renren-fast/app/order/wechatSuccess";

    public static final String ACTION_REQUEST_SUCCESS_RECEIVER = "action_request_success";
    public static final String ACTION_CREATE_ORDER_RECEIVER = "action_create_order";
    public enum RequestType{
        GOODS_LIST, LOGIN, GOODS_INFO, SEND_CODE, ALI_PAY, WECHAT_PAY, GET_ALI_PAY_INFO, GET_WECHAT_PAY_INFO, CREAT_ORDER, ORDER_INFO, ORDER_LIST
    }

    public static final String USER_INFO = "user_info";
}
