package com.ape.bananarecharge.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ape.bananarecharge.Datamodel.GoodsInfo;
import com.ape.bananarecharge.R;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {
    private ArrayList<GoodsInfo> lists;
    private Context mContext;

    public GridAdapter(Context context, ArrayList<GoodsInfo> arrayList){
        mContext = context;
        lists = arrayList;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int i) {
        return lists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(mContext).inflate(R.layout.home_page_item_layout, null);
        ImageView goodsImage = view.findViewById(R.id.item_image);
        TextView goodsName = view.findViewById(R.id.item_title);

        goodsImage.setImageBitmap(lists.get(i).getGoodsPicture());
        goodsName.setText(lists.get(i).getGoodsName());
        return view;
    }
}
