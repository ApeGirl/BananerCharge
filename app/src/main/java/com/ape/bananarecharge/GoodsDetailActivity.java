package com.ape.bananarecharge;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ape.bananarecharge.Controller.GoodsManager;
import com.ape.bananarecharge.Controller.UsrMananger;
import com.ape.bananarecharge.Datamodel.GoodsInfo;
import com.ape.bananarecharge.Datamodel.UsrInfo;
import com.ape.bananarecharge.Login.LoginActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Util.MapUtil;
import Util.PostParamsUtil;
import Util.URLUtils;

public class GoodsDetailActivity extends AppCompatActivity {
    private static final String TAG = "GoodsDetailActivity";

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

    private int count = 1;

    private GoodsInfo mGoodInfo;
    private Map<String, String> goodsParamsMap;
    private Context mContext;
    private GoodsManager mGoodsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        Intent intent = getIntent();
        mGoodInfo = (GoodsInfo) intent.getSerializableExtra("goodsInfo");
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
        mStep1 = findViewById(R.id.step1);
        mStep2 = findViewById(R.id.step2);
        mStep3 = findViewById(R.id.step3);
        mStep1Pic = findViewById(R.id.step1_pic);
        mStep2Pic = findViewById(R.id.step2_pic);
        mWebAddr = findViewById(R.id.web_addr);
        mBack = findViewById(R.id.back);
        goodsParamsMap = new HashMap<>();

        mGoodsIndexPic.setImageURI(Uri.parse(mGoodInfo.getIndexPicUrl()));
        mGoodsTitle.setText(mGoodInfo.getTitle());
        mPurchaseCount.setText(count + "");
        String directBuy = "￥" + mGoodInfo.getPrice() + "  " + getResources().getString(R.string.direct_buy);
        String shareBuy = "￥" + mGoodInfo.getShaPrice() + "  " + getResources().getString(R.string.share_buy);
        mDirectBuy.setText(directBuy);
        mShareBuy.setText(shareBuy);

        mPlusBtn.setOnClickListener(clickListener);
        mMinusBtn.setOnClickListener(clickListener);
        mDirectBuy.setOnClickListener(clickListener);
        mShareBuy.setOnClickListener(clickListener);
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String rechargeAccount = mRechargeAccount.getText().toString();
            String buyCount = mPurchaseCount.getText().toString();
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
                case R.id.share_buy:
                    UsrMananger usrMananger = new UsrMananger(mContext);
                    UsrInfo info = usrMananger.getUsrInfoFromDataBase(mContext);
                    if (info == null) {
                        Intent intent = new Intent(mContext, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        if (info.isHasLogin()) {
                            createOrder(info.getId());
                            mGoodsManager.doPostRequest(goodsParamsMap, URLUtils.CREATE_ORDER, URLUtils.RequestType.CREAT_ORDER);
                        }
                    }
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
}
