package com.ape.bananarecharge;

import android.annotation.SuppressLint;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ape.bananarecharge.Adapter.GridAdapter;
import com.ape.bananarecharge.Adapter.MyFragmentAdapter;
import com.ape.bananarecharge.Controller.GoodsManager;
import com.ape.bananarecharge.Datamodel.GoodsInfo;
import com.ape.bananarecharge.Fragment.HomePageFragment;
import com.ape.bananarecharge.Fragment.OrderFragment;
import com.ape.bananarecharge.Login.LoginActivity;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Util.URLUtils;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ViewPager mViewPager;
    private ArrayList<Fragment> fragments;

    private LinearLayout mHome;
    private LinearLayout mOrder;
    private ImageView mHomeImg;
    private ImageView mOrderImg;
    private TextView mHomeText;
    private TextView mOrderText;
    private TextView mTitle;
    private Map<String, String> mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_main);
        initTabBar();
        initViewPager();
        initData();
    }

    private void initViewPager() {
        mViewPager = findViewById(R.id.viewpager);
        fragments = new ArrayList<>();
        fragments.add(new HomePageFragment());
        fragments.add(new OrderFragment());

        FragmentManager fragmentManager = getSupportFragmentManager();
        MyFragmentAdapter adapter = new MyFragmentAdapter(fragmentManager, fragments);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(0);
        setTabImageColor(0);
        mViewPager.setOnPageChangeListener(new MyPageChangeListener());
        mMap = new HashMap<>();
    }

    private void initTabBar() {
        mTitle = findViewById(R.id.title_content);
        mHome = findViewById(R.id.home_layout);
        mHomeImg = findViewById(R.id.home_img);
        mHomeText = findViewById(R.id.home_text);
        mOrder = findViewById(R.id.order_layout);
        mOrderImg = findViewById(R.id.order_img);
        mOrderText = findViewById(R.id.order_text);

        mHome.setOnClickListener(listener);
        mOrder.setOnClickListener(listener);
    }

    private void initData() {
        GoodsManager manager = new GoodsManager(this);
        manager.doPostRequest(new HashMap<String, String>(), URLUtils.GOODS_LIST);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @SuppressLint("ResourceAsColor")
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.home_layout:
                    mViewPager.setCurrentItem(0);
                    setTabImageColor(0);
                    break;
                case R.id.order_layout:
                    mViewPager.setCurrentItem(1);
                    setTabImageColor(1);
                    break;
            }
        }
    };

    class MyPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @SuppressLint("ResourceAsColor")
        @Override
        public void onPageSelected(int position) {
            setTabImageColor(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private void setTabImageColor(int item) {
        switch (item) {
            case 0:
                mTitle.setText(R.string.home_page);
                mHomeImg.setImageResource(R.drawable.home_selected);
                mHomeText.setTextColor(getResources().getColor(R.color.home_page_selected_text_color));
                mOrderImg.setImageResource(R.drawable.order_grey);
                mOrderText.setTextColor(getResources().getColor(R.color.home_page_normal_text_color));
                break;
            case 1:
                mTitle.setText(R.string.order_page);
                mHomeImg.setImageResource(R.drawable.home_grey);
                mHomeText.setTextColor(getResources().getColor(R.color.home_page_normal_text_color));
                mOrderImg.setImageResource(R.drawable.order_selected);
                mOrderText.setTextColor(getResources().getColor(R.color.home_page_selected_text_color));
                break;
        }
    }
}
