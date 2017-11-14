package com.example.wangyao.zhrbgeminiwy.holder;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.wangyao.zhrbgeminiwy.R;

/**
 * Created by wangyao on 2017/7/9.
 */

public class TopViewPagerHolder extends RecyclerView.ViewHolder {
    public ViewPager vp_Top;

    public TopViewPagerHolder(View itemView) {
        super(itemView);
        System.out.println("执行了");
        vp_Top = (ViewPager) itemView.findViewById(R.id.vp_topView);
    }
}
