package com.ape.bananarecharge;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ape.bananarecharge.Controller.GoodsManager;
import com.ape.bananarecharge.Datamodel.GoodsInfo;
import com.ape.bananarecharge.Fragment.HomePageFragment;
import com.facebook.drawee.view.SimpleDraweeView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Util.URLUtils;
import Util.Utils;

public class PayActivity extends AppCompatActivity {
    private static final String TAG = "PayActivity";
    private SimpleDraweeView mGoodsPic;
    private TextView mGoodTitle;
    private TextView mBuyCount;
    private TextView mGoodsPrice;
    private TextView mBottomPrice;
    private Button mBuyBtn;
    private ImageView mWachatCheck;
    private ImageView mAliCheck;
    private ImageView mBack;
    private TextView mTitleContent;
    private GoodsInfo mGoodsInfo;
    private String mUsrAccount;
    private int mPayType = -1;
    private boolean mSelected = false;
    private boolean isAliSelected = false;
    private GoodsManager mGoodManager;
    private Context mContext;
    private MyReceiver mReceiver;
    private LocalBroadcastManager mLocalBroadcastManager;
    private String mOrderId;
    private Map<String, String> mMap;
    private RelativeLayout mPayWait;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        initViews();
    }

    private void initViews() {
        mContext = PayActivity.this;
        Bundle bundle = getIntent().getExtras();
        mGoodsInfo = (GoodsInfo) bundle.getSerializable(Utils.GOODS_INFO);
        mUsrAccount = bundle.getString(Utils.UER_ACCOUNT);
        String buyCounts = bundle.getString(Utils.BUY_COUNT);
        int buyType = bundle.getInt(Utils.BUY_TYPE);
        mGoodManager = new GoodsManager(mContext);
        mMap = new HashMap<>();

        mGoodsPic = findViewById(R.id.goods_pic);
        mGoodTitle = findViewById(R.id.goods_title);
        mBuyCount = findViewById(R.id.count_num);
        mGoodsPrice = findViewById(R.id.goods_price);
        mBottomPrice = findViewById(R.id.bottom_price);
        mBuyBtn = findViewById(R.id.buy_btn);
        mWachatCheck = findViewById(R.id.wachat_buy);
        mAliCheck = findViewById(R.id.ali_buy);
        mPayWait = findViewById(R.id.pay_wait_layout);

        mBuyCount.setText(buyCounts);
        double price = (buyType == Utils.DIRECT_BUY) ? mGoodsInfo.getPrice() : mGoodsInfo.getShaPrice();
        String price2 = "￥" + price * Integer.parseInt(buyCounts);
        mGoodsPrice.setText(price2);
        mBottomPrice.setText(price2);
        mGoodTitle.setText(mGoodsInfo.getTitle());
        mGoodsPic.setImageURI(mGoodsInfo.getIndexPicUrl());

        mBack = findViewById(R.id.back);
        mTitleContent = findViewById(R.id.title_content);
        mBack.setVisibility(View.VISIBLE);
        mTitleContent.setText(getResources().getString(R.string.pay));

        mWachatCheck.setOnClickListener(onClickListener);
        mAliCheck.setOnClickListener(onClickListener);
        mBuyBtn.setOnClickListener(onClickListener);
        mBack.setOnClickListener(onClickListener);

        mReceiver = new MyReceiver();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.wachat_buy:
                    Utils.pay_type = Utils.WACHAT_PAY;
                    changePayState(mWachatCheck, Utils.pay_type );
                    break;
                case R.id.ali_buy:
                    Utils.pay_type = Utils.Ali_PAY;
                    changePayState(mAliCheck, Utils.pay_type );
                    break;
                case R.id.buy_btn:
                    if (mOrderId == null) {
                        mPayWait.setVisibility(View.VISIBLE);
                        Utils.setPayWaitLayout(mPayWait);
                    } else {
                        mMap.put(Utils.ORDER_ID, mOrderId);
                        Utils.createOrderPay(mContext, Utils.pay_type, mMap);
                    }
                    break;
                case R.id.back:
                    finish();
                    break;
                default:
                    break;
            }
        }
    };

    private void changePayState(ImageView view, int type) {
        if (type == Utils.Ali_PAY) {
            if (!mSelected) {
                view.setImageResource(R.drawable.selected);
                isAliSelected = true;
            } else {
                view.setImageResource(R.drawable.select);
                isAliSelected = false;
            }
            mSelected = !mSelected;
        } else {
            if (!isAliSelected) {
                if (!mSelected) {
                    view.setImageResource(R.drawable.selected);
                } else {
                    view.setImageResource(R.drawable.select);
                }
                mSelected = !mSelected;
            }
        }
    }
}
