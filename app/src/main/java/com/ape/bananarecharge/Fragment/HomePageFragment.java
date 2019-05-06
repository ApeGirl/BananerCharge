package com.ape.bananarecharge.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.ape.bananarecharge.Adapter.GridAdapter;
import com.ape.bananarecharge.Controller.GoodsManager;
import com.ape.bananarecharge.Datamodel.GoodsInfo;
import com.ape.bananarecharge.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Util.URLUtils;

public class HomePageFragment extends Fragment {
    private static final String TAG = "HomePageFragment";
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
//        mGoodsInfoList = mGoodsManager.getGoodsList();
//        mAdapter = new GridAdapter(mContext, mGoodsInfoList);
        mProgressBar =view.findViewById(R.id.progress);

//        mGridView.setAdapter(mAdapter);

        mLocalBroadcastManager = LocalBroadcastManager.getInstance(mContext) ;
        IntentFilter intentFilter = new IntentFilter(URLUtils.ACTION_REQUEST_SUCCESS_RECEIVER);
        mReceiver = new RequestSuccessReceiver();
        mLocalBroadcastManager.registerReceiver( mReceiver , intentFilter );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mLocalBroadcastManager != null) {
            mLocalBroadcastManager.unregisterReceiver(mReceiver);
        }
    }

    class RequestSuccessReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();

            mGoodsInfoList = (ArrayList<GoodsInfo>) bundle.getSerializable("list");
            mAdapter = new GridAdapter(mContext, mGoodsInfoList);
            mGridView.setAdapter(mAdapter);

            Log.i("GoodsManager", "mGoodsInfoList : " + mGoodsInfoList);
            if (mGoodsInfoList != null) {
                mProgressBar.setVisibility(View.GONE);
            }
        }
    }
}
