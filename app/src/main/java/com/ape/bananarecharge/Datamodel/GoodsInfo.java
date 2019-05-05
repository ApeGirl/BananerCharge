package com.ape.bananarecharge.Datamodel;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class GoodsInfo {
    private Bitmap goodsPicture;
    private String goodsName;

    public Bitmap getGoodsPicture() {
        return goodsPicture;
    }

    public void setGoodsPicture(Bitmap goodsPicture) {
        this.goodsPicture = goodsPicture;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
}
