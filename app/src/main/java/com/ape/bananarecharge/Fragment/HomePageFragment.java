package com.ape.bananarecharge.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.ape.bananarecharge.Adapter.GridAdapter;
import com.ape.bananarecharge.Datamodel.GoodsInfo;
import com.ape.bananarecharge.R;

import java.util.ArrayList;

public class HomePageFragment extends Fragment {
    private Context mContext;
    private GridView mGridView;
    private GridAdapter mAdapter;
    private ArrayList<GoodsInfo> mGoodsInfoList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        View view = inflater.inflate(R.layout.home_page_fragment_layout, null);
        mGridView = view.findViewById(R.id.home_page_grid);
//        mAdapter = new GridAdapter(mContext, mGoodsInfoList);
//        mGridView.setAdapter(mAdapter);
        return view;
    }
}
