package com.ape.bananarecharge.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.net.Uri;
import android.support.annotation.NonNull;
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
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.xiaweizi.cornerslibrary.CornersProperty;
import com.xiaweizi.cornerslibrary.RoundCornersTransformation;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class GridAdapter extends BaseAdapter {
    private static final String TAG = "GridAdapter";
    private List<GoodsInfo> lists;
    private Context mContext;

    public GridAdapter(Context context, List<GoodsInfo> arrayList) {
        mContext = context;
        lists = arrayList;
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
        ViewHolder holder = new ViewHolder();

        view = LayoutInflater.from(mContext).inflate(R.layout.home_page_item_layout, null);
        ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                (int) mContext.getResources().getDimension(R.dimen.gridview_item_height));
        view.setLayoutParams(new GridView.LayoutParams(params));

        holder.goodsPicture = view.findViewById(R.id.item_image);
        holder.goodsTitle = view.findViewById(R.id.item_title);

        if (lists != null) {
            Uri uri = Uri.parse(lists.get(i).getPicUrl());
            loadImage(uri, holder);
            holder.goodsTitle.setText(lists.get(i).getTitle());
        }

        return view;
    }

    private void loadImage(Uri uri, ViewHolder holder) {
        CornersProperty cornersProperty = new CornersProperty();
        cornersProperty.setCornersRadius(50);
        cornersProperty.setCornersType(CornersProperty.CornerType.ALL);
        RoundCornersTransformation transformation = new RoundCornersTransformation(mContext, cornersProperty);
        Glide.with(mContext)
                .load(uri)
                .bitmapTransform(transformation) //设置圆角
                .into(holder.goodsPicture);
    }

    class ViewHolder {
        public TextView goodsTitle;
        public ImageView goodsPicture;
    }

}
