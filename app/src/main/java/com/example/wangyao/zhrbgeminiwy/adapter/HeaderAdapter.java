package com.example.wangyao.zhrbgeminiwy.adapter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import com.example.wangyao.zhrbgeminiwy.application.MyApplication;
import com.example.wangyao.zhrbgeminiwy.holder.TopViewPagerHolder;
import com.example.wangyao.zhrbgeminiwy.listener.OnTopItemContentListener;

import java.util.List;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by wangyao on 2017/6/22.
 */

class HeaderAdapter extends PagerAdapter {
    private List<ImageView> imageViews;
    private OnTopItemContentListener onTopItemContentListener;
    private boolean isAutoPlay = true;



    public HeaderAdapter(List<ImageView> imageViews) {


        this.imageViews = imageViews;
        System.out.println("执行了");


    }





    @Override
    public int getCount() {
        return Short.MAX_VALUE;//设置无限大
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        position = position%imageViews.size();//设置position，为除以图片数的余数
        ImageView im = imageViews.get(position);
        ViewParent vp = im.getParent();
        if (vp!=null){
            ViewGroup parent = (ViewGroup)vp;
            parent.removeView(im);
        }
        container.addView(im);
        final int finalPosition = position;
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  onTopItemContentListener.onClick(finalPosition);
            }
        });


        return imageViews.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }

    public void setOnTopItemContentListener(OnTopItemContentListener onTopItemContentListener) {
        this.onTopItemContentListener = onTopItemContentListener;
    }

    public void setIsAutoPlay(boolean isAutoPlay){
        this.isAutoPlay = isAutoPlay;
    }



}
