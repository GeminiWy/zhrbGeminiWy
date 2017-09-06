package com.example.wangyao.zhrbgeminiwy.adapter;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import com.example.wangyao.zhrbgeminiwy.holder.TopViewPagerHolder;
import com.example.wangyao.zhrbgeminiwy.listener.OnTopItemContentListener;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by wangyao on 2017/6/22.
 */

class HeaderAdapter extends PagerAdapter {
    private List<ImageView> imageViews;
    private OnTopItemContentListener onTopItemContentListener;
    private TopViewPagerHolder topViewPagerHolder;
    private int currentItem = 0 ;
    private Handler handler = new Handler(){//收到
        @Override
        public void handleMessage(Message msg) {
            handler = new Handler(){
                @Override
                public void handleMessage(Message msg) {//当收到0的时候，页数加1
                    switch (msg.what){
                        case 0:
                            currentItem++;
                            if (currentItem%imageViews.size() == 0){//判断是不是第一张，第一张的时候不设置翻页动画
                                topViewPagerHolder.vp_Top.setCurrentItem(currentItem,false);//不设置翻页动画
                            }else {
                                topViewPagerHolder.vp_Top.setCurrentItem(currentItem);
                            }
                            break;
                        default:
                            break;
                    }
                    super.handleMessage(msg);
                }
            };
        }
    };


    public HeaderAdapter(List<ImageView> imageViews,  TopViewPagerHolder topViewPagerHolder){
        this.topViewPagerHolder = topViewPagerHolder;
        this.imageViews = imageViews;

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {//用timer每隔三秒发送一次空数据
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        },1000,3*1000);
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
}
