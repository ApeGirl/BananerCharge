package com.ape.bananarecharge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

public class LeftToRightBack extends AppCompatActivity {
    View decorView;
    float downX, downY;
    float screenWidth, screenHeight;


    boolean allow = false;

    public void InitLeftToRightBack() {
        // 获得decorView
        decorView = getWindow().getDecorView();


        // 获得手机屏幕的宽度和高度，单位像素
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;
        allow = true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        TouchEvent(event);
        return super.onTouchEvent(event);
    }

    boolean isSide = false;

    public void TouchEvent(MotionEvent event) {
        if (allow) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {// 当按下时
                // 获得按下时的X坐标
                downX = event.getX();
                isSide = downX < screenWidth / 10;
            } else if (event.getAction() == MotionEvent.ACTION_MOVE && isSide) {// 当手指滑动时
                // 获得滑过的距离
                float moveDistanceX = event.getX() - downX;
                if (moveDistanceX > 0) {// 如果是向右滑动
                    decorView.setX(moveDistanceX); // 设置界面的X到滑动到的位置
                }


            } else if (event.getAction() == MotionEvent.ACTION_UP && isSide) {// 当抬起手指时
                // 获得滑过的距离
                float moveDistanceX = event.getX() - downX;
                if (moveDistanceX > screenWidth / 3) {
                    // 如果滑动的距离超过了手机屏幕的1/3, 结束当前Activity
                    finish();
                } else { // 如果滑动距离没有超过一半
                    // 恢复初始状态
                    decorView.setX(0);
                }
            }
        }
    }
}
