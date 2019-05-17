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
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.Toast;

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

public class HomePageFragment extends Fragment implements AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
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
    private SwipeRefreshLayout mRefresh;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mContext = getActivity();
        View view = inflater.inflate(R.layout.home_page_fragment_layout, null);

        initView(view);

        return view;
    }

    private void initView(View view) {
        mIdArray = new ArrayList<>();
        mGoodsList = new ArrayList<>();
        mGoodsInfoList = new ArrayList<>();
        mGridView = view.findViewById(R.id.home_page_grid);
        mGoodsManager = new GoodsManager(mContext);
        mProgressBar =view.findViewById(R.id.progress);
        mRefresh = view.findViewById(R.id.refresh);

        mLocalBroadcastManager = LocalBroadcastManager.getInstance(mContext) ;
        IntentFilter intentFilter = new IntentFilter(URLUtils.ACTION_REQUEST_SUCCESS_RECEIVER);
        mReceiver = new RequestSuccessReceiver();
        mLocalBroadcastManager.registerReceiver( mReceiver , intentFilter );

//        mAdapter = new GridAdapter(mContext, mGoodsInfoList);
//        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(this);
        mRefresh.setOnRefreshListener(this);
        mRefresh.setColorScheme(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);

        SharedPreferences sharedPreferences = mContext.getSharedPreferences(GOODS_INFO, Context.MODE_PRIVATE);
        String str = sharedPreferences.getString(INFO_LIST, null);
        mGoodsInfoList = (ArrayList<GoodsInfo>) Utils.String2Object(str);
        if (mGoodsInfoList != null) {
            mAdapter = new GridAdapter(mContext, mGoodsInfoList);
            mGridView.setAdapter(mAdapter);
        }else {
            Toast.makeText(mContext, "网络错误，请刷新重试", Toast.LENGTH_SHORT).show();
        }
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

    @Override
    public void onRefresh() {
        Log.i(TAG, "onRefresh");
        initData();
    }
    private void initData() {
        GoodsManager manager = new GoodsManager(mContext);
        manager.doPostRequest(new HashMap<String, String>(), URLUtils.GOODS_LIST, URLUtils.RequestType.GOODS_LIST);
    }

    class RequestSuccessReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            mGoodsInfoList = (ArrayList<GoodsInfo>) bundle.getSerializable("list");
            mAdapter = new GridAdapter(mContext, mGoodsInfoList);
            mGridView.setAdapter(mAdapter);
            mRefresh.setRefreshing(false);

            SharedPreferences sharedPreferences = mContext.getSharedPreferences(GOODS_INFO, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(INFO_LIST, Utils.Object2String(mGoodsInfoList, TAG));
            editor.apply();

            Log.i(TAG, "mGoodsInfoList : " + mGoodsInfoList);
            if (mGoodsInfoList != null) {
                mProgressBar.setVisibility(View.GONE);
            }
        }
    }
}
