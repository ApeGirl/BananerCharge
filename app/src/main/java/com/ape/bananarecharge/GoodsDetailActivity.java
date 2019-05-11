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
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ape.bananarecharge.Controller.GoodsManager;
import com.ape.bananarecharge.Controller.UsrMananger;
import com.ape.bananarecharge.Datamodel.GoodsInfo;
import com.ape.bananarecharge.Datamodel.UsrInfo;
import com.ape.bananarecharge.Login.LoginActivity;
import com.facebook.drawee.view.SimpleDraweeView;

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
    private TextView mStep1;
    private TextView mStep2;
    private TextView mStep3;
    private ImageView mStep1Pic;
    private ImageView mStep2Pic;
    private TextView mWebAddr;
    private ImageView mBack;
    private TextView mTitleContent;
    private ImageView mShare;
    private RelativeLayout mShareDialog;
    private TextView mShareText;

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

        mSharedPreferences = getSharedPreferences(SHARE_DIALOG_TAG, MODE_PRIVATE);
        boolean isShowShare = mSharedPreferences.getBoolean(SHARE_STAE, true);
        if (isShowShare) {
            mShareDialog.setVisibility(View.VISIBLE);
        } else {
            mShareDialog.setVisibility(View.GONE);
        }
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
        mStep1 = findViewById(R.id.step1);
        mStep2 = findViewById(R.id.step2);
        mStep3 = findViewById(R.id.step3);
        mStep1Pic = findViewById(R.id.step1_pic);
        mStep2Pic = findViewById(R.id.step2_pic);
        mWebAddr = findViewById(R.id.web_addr);
        mBack = findViewById(R.id.back);
        goodsParamsMap = new HashMap<>();
        mTitleContent = findViewById(R.id.title_content);
        mShare = findViewById(R.id.more);
        mShareDialog = findViewById(R.id.share_dialog);
        mShareText = findViewById(R.id.share_remind_text);

        mReceiver = new MyReceiver();
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(mContext);

        mGoodsIndexPic.setImageURI(Uri.parse(mGoodInfo.getIndexPicUrl()));
        mGoodsTitle.setText(mGoodInfo.getTitle());
        mPurchaseCount.setText(count + "");
        String directBuy = "￥" + mGoodInfo.getPrice() + "  " + getResources().getString(R.string.direct_buy);
        String shareBuy = "￥" + mGoodInfo.getShaPrice() + "  " + getResources().getString(R.string.share_buy);
        mDirectBuy.setText(directBuy);
        mShareBuy.setText(shareBuy);

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
                case R.id.direct_buy:
                    type = Utils.DIRECT_BUY;
                case R.id.share_buy:
                    Log.i(TAG, " type : " + type);
                    if (type < 0) {
                        type = Utils.SHARE_BUY;
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
                    break;
                case R.id.share_remind_text:
                    mShareDialog.setVisibility(View.GONE);
                    SharedPreferences.Editor editor = mSharedPreferences.edit();
                    editor.putBoolean(SHARE_STAE, false);
                    editor.apply();
                    break;
                default:
                    break;
            }
        }
    };

    private void createOrder(int id) {
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
}
