package com.ape.bananarecharge.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ape.bananarecharge.Datamodel.GoodsInfo;
import com.ape.bananarecharge.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GridAdapter extends BaseAdapter {
    private static final String TAG = "GridAdapter";
    private List<GoodsInfo> lists;
    private Context mContext;
    private Bitmap mBitmap;

    public GridAdapter(Context context, List<GoodsInfo> arrayList) {
        mContext = context;
        lists = arrayList;
        Log.i(TAG, "list : " + lists);
    }

    @Override
    public int getCount() {
        if (lists == null) {
            return 0;
        }
        return lists.size();
    }

    @Override
    public Object getItem(int i) {
        if (lists == null) {
            return null;
        }
        return lists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.home_page_item_layout, null);
            holder.goodsPicture = view.findViewById(R.id.item_image);
            holder.goodsTitle = view.findViewById(R.id.item_title);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                (int) mContext.getResources().getDimension(R.dimen.gridview_item_height));
        view.setLayoutParams(new GridView.LayoutParams(params));

        if (lists != null) {
            Uri uri = Uri.parse(lists.get(i).getPicUrl());
            Glide.with(mContext).load(uri).diskCacheStrategy(DiskCacheStrategy.NONE).into(holder.goodsPicture);
        }
        holder.goodsTitle.setText(lists.get(i).getTitle());
        Log.i(TAG, "title : " + lists.get(i).getTitle() + "   url : " + lists.get(i).getPicUrl());
        return view;
    }

    class ViewHolder {
        public TextView goodsTitle;
        public ImageView goodsPicture;
    }


    private Bitmap getBitMap(final String url) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                URL imageurl = null;

                try {
                    imageurl = new URL(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    HttpURLConnection conn = (HttpURLConnection) imageurl.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    mBitmap = BitmapFactory.decodeStream(is);
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return mBitmap;
    }
}
