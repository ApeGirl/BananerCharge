package com.ape.bananarecharge.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.ape.bananarecharge.Adapter.GridAdapter;
import com.ape.bananarecharge.Controller.GoodsManager;
import com.ape.bananarecharge.Datamodel.GoodsInfo;
import com.ape.bananarecharge.GoodsDetailActivity;
import com.ape.bananarecharge.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Util.URLUtils;
import Util.Utils;

public class HomePageFragment extends Fragment implements AdapterView.OnItemClickListener {
    private static final String TAG = "HomePageFragment";
    private static final String GOODS_INFO = "goods_info";
    private static final String INFO_LIST = "info_list";
    private Context mContext;
    private GridView mGridView;
    private GridAdapter mAdapter;
    private ArrayList<GoodsInfo> mGoodsInfoList;
    private List<GoodsInfo> mGoodsList;
    private ArrayList<Integer> mIdArray;
    private GoodsManager mGoodsManager;
    private RequestSuccessReceiver mReceiver;
    private LocalBroadcastManager mLocalBroadcastManager;
    private RelativeLayout mProgressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mContext = getActivity();
        View view = inflater.inflate(R.layout.home_page_fragment_layout, null);

        initView(view);

        if (savedInstanceState != null) {
            if (mIdArray.size()  == 0) {
                mProgressBar.setVisibility(View.VISIBLE);
            } else {
                for (int i = 0; i < mIdArray.size(); i++) {
                    String picUrl = "picurl" + mIdArray.get(i);
                    String title = "title" + mIdArray.get(i);
                    mGoodsList.get(i).setPicUrl(savedInstanceState.getString(picUrl));
                    mGoodsList.get(i).setTitle(savedInstanceState.getString(title));
                }
            }
        }
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(GOODS_INFO, Context.MODE_PRIVATE);
        String str = sharedPreferences.getString(INFO_LIST, null);
        mGoodsInfoList = (ArrayList<GoodsInfo>) Utils.String2Object(str);
        if (mGoodsInfoList != null) {
            mAdapter = new GridAdapter(mContext, mGoodsInfoList);
            mGridView.setAdapter(mAdapter);
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mGoodsInfoList.size() != 0) {
            for (int i = 0; i < mGoodsInfoList.size(); i++) {
                String picUrl = "picurl" + mGoodsInfoList.get(i).getGoddsid();
                String title = "title" + mGoodsInfoList.get(i).getGoddsid();
                outState.putString(picUrl , mGoodsInfoList.get(i).getPicUrl());
                outState.putString(title , mGoodsInfoList.get(i).getTitle());
                mIdArray.add(i, mGoodsInfoList.get(i).getGoddsid());
            }
        }
    }

    private void initView(View view) {
        mIdArray = new ArrayList<>();
        mGoodsList = new ArrayList<>();
        mGoodsInfoList = new ArrayList<>();
        mGridView = view.findViewById(R.id.home_page_grid);
        mGoodsManager = new GoodsManager(mContext);
        mProgressBar =view.findViewById(R.id.progress);

        mLocalBroadcastManager = LocalBroadcastManager.getInstance(mContext) ;
        IntentFilter intentFilter = new IntentFilter(URLUtils.ACTION_REQUEST_SUCCESS_RECEIVER);
        mReceiver = new RequestSuccessReceiver();
        mLocalBroadcastManager.registerReceiver( mReceiver , intentFilter );
        mAdapter = new GridAdapter(mContext, mGoodsInfoList);
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mLocalBroadcastManager != null) {
            mLocalBroadcastManager.unregisterReceiver(mReceiver);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(mContext, GoodsDetailActivity.class);
        intent.putExtra(Utils.GOODS_INFO, mGoodsInfoList.get(position));
        mContext.startActivity(intent);
    }

    class RequestSuccessReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            mGoodsInfoList = (ArrayList<GoodsInfo>) bundle.getSerializable("list");
            mAdapter = new GridAdapter(mContext, mGoodsInfoList);
            mGridView.setAdapter(mAdapter);

            SharedPreferences sharedPreferences = mContext.getSharedPreferences(GOODS_INFO, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(INFO_LIST, Utils.Object2String(mGoodsInfoList, TAG));
            editor.apply();

            Log.i("GoodsManager", "mGoodsInfoList : " + mGoodsInfoList);
            if (mGoodsInfoList != null) {
                mProgressBar.setVisibility(View.GONE);
            }
        }
    }
}
