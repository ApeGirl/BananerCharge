package com.ape.bananarecharge.Datamodel;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.io.Serializable;

public class GoodsInfo implements Serializable {
    private int goddsid;
    private String title;
    private String picUrl;
    private String indexPicUrl;
    private String steps;
    private String stepsStr;
    private double price;
    private double shaPrice;
    private int topUpWay;
    private String exchangeAddress;
    private String createTime;
    private int status;
    private String shareUrl;
    private String shareTitle;
    private String shareDesc;
    private String prompt;


    public int getGoddsid() {
        return goddsid;
    }

    public void setGoddsid(int goddsid) {
        this.goddsid = goddsid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getIndexPicUrl() {
        return indexPicUrl;
    }

    public void setIndexPicUrl(String indexPicUrl) {
        this.indexPicUrl = indexPicUrl;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public String getStepsStr() {
        return stepsStr;
    }

    public void setStepsStr(String stepsStr) {
        this.stepsStr = stepsStr;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getShaPrice() {
        return shaPrice;
    }

    public void setShaPrice(double shaPrice) {
        this.shaPrice = shaPrice;
    }

    public int getTopUpWay() {
        return topUpWay;
    }

    public void setTopUpWay(int topUpWay) {
        this.topUpWay = topUpWay;
    }

    public String getExchangeAddress() {
        return exchangeAddress;
    }

    public void setExchangeAddress(String exchangeAddress) {
        this.exchangeAddress = exchangeAddress;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public String getShareDesc() {
        return shareDesc;
    }

    public void setShareDesc(String shareDesc) {
        this.shareDesc = shareDesc;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }


    @Override
    public String toString() {
        return "GoodsInfo{" +
                ", goddsid=" + goddsid +
                ", title='" + title + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", indexPicUrl='" + indexPicUrl + '\'' +
                ", steps='" + steps + '\'' +
                ", stepsStr='" + stepsStr + '\'' +
                ", price=" + price +
                ", shaPrice=" + shaPrice +
                ", topUpWay=" + topUpWay +
                ", exchangeAddress='" + exchangeAddress + '\'' +
                ", createTime='" + createTime + '\'' +
                ", status=" + status +
                ", shareUrl='" + shareUrl + '\'' +
                ", shareTitle='" + shareTitle + '\'' +
                ", shareDesc='" + shareDesc + '\'' +
                ", prompt='" + prompt + '\'' +
                '}';
    }
}
