package com.ape.bananarecharge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ape.bananarecharge.Datamodel.GoodsInfo;
import com.facebook.drawee.view.SimpleDraweeView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import Util.Utils;

public class PayActivity extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        initViews();
    }

    private void initViews() {
        Bundle bundle = getIntent().getExtras();
        mGoodsInfo = (GoodsInfo) bundle.getSerializable(Utils.GOODS_INFO);
        mUsrAccount = bundle.getString(Utils.UER_ACCOUNT);
        String buyCounts = bundle.getString(Utils.BUY_COUNT);
        int buyType = bundle.getInt(Utils.BUY_TYPE);

        mGoodsPic = findViewById(R.id.goods_pic);
        mGoodTitle = findViewById(R.id.goods_title);
        mBuyCount = findViewById(R.id.count_num);
        mGoodsPrice = findViewById(R.id.goods_price);
        mBottomPrice = findViewById(R.id.bottom_price);
        mBuyBtn = findViewById(R.id.buy_btn);
        mWachatCheck = findViewById(R.id.wachat_buy);
        mAliCheck = findViewById(R.id.ali_buy);

        mBuyCount.setText(buyCounts);
        double price = (buyType == Utils.DIRECT_BUY) ? mGoodsInfo.getPrice() : mGoodsInfo.getShaPrice();
        String price2 = "￥" + price;
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

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.wachat_buy:
                    mPayType = Utils.WACHAT_PAY;
                    mWachatCheck.setImageResource(R.drawable.selected);
                    break;
                case R.id.ali_buy:
                    mPayType = Utils.Ali_PAY;
                    mAliCheck.setImageResource(R.drawable.selected);
                    break;
                case R.id.buy_btn:

                    break;
                default:
                    break;
            }
        }
    };

    private void createOrderPay(int type) {
        if (type == Utils.WACHAT_PAY) {

        } else if (type == Utils.Ali_PAY) {

        } else {
            Toast.makeText(PayActivity.this, "请选择支付方式", Toast.LENGTH_SHORT).show();
        }
    }
}
