package com.ape.bananarecharge.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ape.bananarecharge.R;

import java.util.List;

public class CountryIdAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> lists;

    public CountryIdAdapter(Context context, List<String> list) {
        mContext = context;
        lists = list;
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
        view = LayoutInflater.from(mContext).inflate(R.layout.country_id_item_layout, null);
        TextView country_id = view.findViewById(R.id.country_id_item);
        country_id.setText(lists.get(i));
        return view;
    }
}
