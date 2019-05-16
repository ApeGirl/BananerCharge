package com.ape.bananarecharge;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.PersistableBundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ape.bananarecharge.Controller.GoodsManager;
import com.ape.bananarecharge.Controller.UsrMananger;
import com.ape.bananarecharge.Datamodel.GoodsInfo;
import com.ape.bananarecharge.Datamodel.UsrInfo;
import com.ape.bananarecharge.Login.LoginActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.drawee.view.SimpleDraweeView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Util.MapUtil;
import Util.PostParamsUtil;
import Util.URLUtils;
import Util.Utils;

public class GoodsDetailActivity extends AppCompatActivity {
    private static final String TAG = "GoodsDetailActivity";
    private static final String SHARE_DIALOG_TAG = "share_dialog";
    private static final String SHARE_STAE = "share_state";

    private SimpleDraweeView mGoodsIndexPic;
    private TextView mGoodsTitle;
    private EditText mRechargeAccount;
    private ImageView mPlusBtn;
    private ImageView mMinusBtn;
    private TextView mPurchaseCount;
    private Button mDirectBuy;
    private Button mShareBuy;
    private ImageView mBack;
    private TextView mTitleContent;
    private ImageView mShare;
    private RelativeLayout mShareDialog;
    private TextView mShareText;
    private ListView mStepList;

    private int count = 1;

    private GoodsInfo mGoodInfo;
    private Map<String, String> goodsParamsMap;
    private Context mContext;
    private GoodsManager mGoodsManager;
    private int type = Utils.NO_TYPE;
    private String mOrderId;
    private MyReceiver mReceiver;
    private LocalBroadcastManager mLocalBroadcastManager;
    private SharedPreferences mSharedPreferences;

    private boolean isShareSuccess = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);

        Intent intent = getIntent();
        mGoodInfo = (GoodsInfo) intent.getSerializableExtra(Utils.GOODS_INFO);
        Log.i(TAG, "mGoodInfo : " + mGoodInfo);
        mContext = GoodsDetailActivity.this;
        mGoodsManager = new GoodsManager(mContext);
        initViews();


    }

    private void initViews() {
        mGoodsIndexPic = findViewById(R.id.goods_index_pic);
        mGoodsTitle = findViewById(R.id.goods_title);
        mRechargeAccount = findViewById(R.id.enter_account);
        mPlusBtn = findViewById(R.id.plus);
        mMinusBtn = findViewById(R.id.minus);
        mPurchaseCount = findViewById(R.id.count_num);
        mDirectBuy = findViewById(R.id.direct_buy);
        mShareBuy = findViewById(R.id.share_buy);

        mBack = findViewById(R.id.back);
        goodsParamsMap = new HashMap<>();
        mTitleContent = findViewById(R.id.title_content);
        mShare = findViewById(R.id.more);
        mShareDialog = findViewById(R.id.share_dialog);
        mShareText = findViewById(R.id.share_remind_text);
        mStepList = findViewById(R.id.steps_list);
        StepsAdapter adapter = new StepsAdapter();
        mStepList.setAdapter(adapter);

        mReceiver = new MyReceiver();
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(mContext);

        mGoodsIndexPic.setImageURI(Uri.parse(mGoodInfo.getIndexPicUrl()));
        mGoodsTitle.setText(mGoodInfo.getTitle());
        mPurchaseCount.setText(count + "");
        String directBuy = "￥" + mGoodInfo.getPrice() + "  " + getResources().getString(R.string.direct_buy);
        String shareBuy = "￥" + mGoodInfo.getShaPrice() + "  " + getResources().getString(R.string.share_buy);
        mDirectBuy.setText(directBuy);
        mShareBuy.setText(shareBuy);
        mRechargeAccount.setHint(mGoodInfo.getPrompt());

        mBack.setVisibility(View.VISIBLE);
        mShare.setVisibility(View.VISIBLE);
        mTitleContent.setText(getResources().getString(R.string.detail));

        mPlusBtn.setOnClickListener(clickListener);
        mMinusBtn.setOnClickListener(clickListener);
        mDirectBuy.setOnClickListener(clickListener);
        mShareBuy.setOnClickListener(clickListener);
        mBack.setOnClickListener(clickListener);
        mShare.setOnClickListener(clickListener);
        mShareText.setOnClickListener(clickListener);
        mShareDialog.setOnClickListener(clickListener);

        mLocalBroadcastManager = LocalBroadcastManager.getInstance(mContext);
        IntentFilter intentFilter = new IntentFilter(URLUtils.ACTION_CREATE_ORDER_RECEIVER);
        mLocalBroadcastManager.registerReceiver(mReceiver, intentFilter);
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.plus:
                    count++;
                    mPurchaseCount.setText(count + "");
                    break;
                case R.id.minus:
                    if (count > 1) {
                        count--;
                        mPurchaseCount.setText(count + "");
                    } else {
                        Toast.makeText(mContext, "不能再少啦", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.share_buy:
                    if (!isShareSuccess) {
                        mShareDialog.setVisibility(View.VISIBLE);
                        break;
                    } else {
                        if (mShareDialog.getVisibility() == View.VISIBLE) {
                            mShareDialog.setVisibility(View.GONE);
                        }
                        type = Utils.SHARE_BUY;
                    }
                case R.id.direct_buy:
                    Log.i(TAG, " type : " + type);
                    if (type < 0) {
                        type = Utils.DIRECT_BUY;
                    }
                    UsrMananger usrMananger = new UsrMananger(mContext);
                    UsrInfo info = usrMananger.getUsrInfoFromDataBase(mContext);
                    usrMananger.setUsrInfo(info);
                    Log.i(TAG, " info : " + info);
                    if (info == null) {
                        Intent intent = new Intent(mContext, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Log.i(TAG, " usrMananger.isHasLogin() : " + usrMananger.isHasLogin());
                        if (usrMananger.isHasLogin()) {
                            if (mRechargeAccount.getText().toString() == null) {
                                Toast.makeText(mContext, "请输入充值账号", Toast.LENGTH_SHORT).show();
                                break;
                            }
                            createOrder(info.getId());
                            mGoodsManager.doPostRequest(goodsParamsMap, URLUtils.CREATE_ORDER, URLUtils.RequestType.CREAT_ORDER);

                            Bundle bundle1 = new Bundle();
                            bundle1.putSerializable(Utils.GOODS_INFO, mGoodInfo);
                            bundle1.putString(Utils.BUY_COUNT, mPurchaseCount.getText().toString());
                            bundle1.putString(Utils.UER_ACCOUNT, mRechargeAccount.getText().toString());
                            bundle1.putInt(Utils.BUY_TYPE, type);
                            Intent intent1 = new Intent(mContext, PayActivity.class);
                            intent1.putExtras(bundle1);
                            mContext.startActivity(intent1);
                        }
                    }
                    type = Utils.NO_TYPE;
                    break;
                case R.id.back:
                    finish();
                    break;
                case R.id.more:
                    share();
                    mShareDialog.setVisibility(View.GONE);
                    break;
                case R.id.share_remind_text:
                    mShareDialog.setVisibility(View.GONE);
                    SharedPreferences.Editor editor = mSharedPreferences.edit();
                    editor.putBoolean(SHARE_STAE, false);
                    editor.apply();
                    break;
                case R.id.share_dialog:
                    mShareDialog.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        }
    };

    private void share() {
        UMWeb web = new UMWeb("http://39.97.180.130:8080/renren-fast/file/6fa93db4cdf140bb8bf23e2e1c0bce90.jpg");
        web.setTitle(mGoodInfo.getShareTitle());//标题
        web.setDescription(mGoodInfo.getShareDesc());//描述
        new ShareAction(GoodsDetailActivity.this)
                .withMedia(web)
                .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                .setCallback(shareListener)
                .share();

    }

    private void share2() {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain"); //分享的是文本类型
        shareIntent.putExtra(Intent.EXTRA_TEXT, "http://39.97.180.130:8080/renren-fast/file/6fa93db4cdf140bb8bf23e2e1c0bce90.jpg");//分享出去的内容
        startActivity(Intent.createChooser(shareIntent, mGoodInfo.getShareTitle()));    //注意这里的变化
    }

    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            isShareSuccess = true;
            Toast.makeText(GoodsDetailActivity.this, "成功了", Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(GoodsDetailActivity.this, "失败" + t.getMessage(), Toast.LENGTH_LONG).show();
            Log.i(TAG, "error : " + t.getMessage());
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(GoodsDetailActivity.this, "取消了", Toast.LENGTH_LONG).show();

        }
    };

    private void createOrder(int id) {
        Log.i(TAG, " userId : " + id
                + "  goodsid : " + String.valueOf(mGoodInfo.getGoddsid())
                + "    ");
        goodsParamsMap.clear();
        goodsParamsMap.put("goodsid", String.valueOf(mGoodInfo.getGoddsid()));
        goodsParamsMap.put("count", mPurchaseCount.getText().toString());
        goodsParamsMap.put("userId", String.valueOf(id));
        goodsParamsMap.put("account", mRechargeAccount.getText().toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLocalBroadcastManager != null) {
            mLocalBroadcastManager.unregisterReceiver(mReceiver);
        }
        if (mSharedPreferences != null) {
            mSharedPreferences = null;
        }
    }

    class CreateOderReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            mOrderId = intent.getStringExtra(Utils.ORDER_ID);
            goodsParamsMap.put(Utils.ORDER_ID, mOrderId);
            Log.i(TAG, "mOrderId : " + mOrderId);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //QQ或者新浪分享
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    class StepsAdapter extends BaseAdapter{
        private TextView mStepText;
        private ImageView mStepPic;

        @Override
        public int getCount() {
            return mGoodInfo.getSteps().length;
        }

        @Override
        public Object getItem(int position) {
            return mGoodInfo.getSteps()[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.steps_list_item_layout, null);
            mStepText = convertView.findViewById(R.id.step_text);
            mStepPic = convertView.findViewById(R.id.step_pic);

            mStepText.setText(mGoodInfo.getSteps()[position]);
            Glide.with(mContext).load(mGoodInfo.getSteps()[position]).diskCacheStrategy(DiskCacheStrategy.NONE).into(mStepPic);
            return convertView;
        }
    }
}
