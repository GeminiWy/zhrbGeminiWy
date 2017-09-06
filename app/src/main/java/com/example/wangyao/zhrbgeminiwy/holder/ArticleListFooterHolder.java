package com.example.wangyao.zhrbgeminiwy.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.wangyao.zhrbgeminiwy.R;

/**
 * Created by wangyao on 2017/7/9.
 */

public class ArticleListFooterHolder extends RecyclerView.ViewHolder {
    private TextView footerTitle;

    public ArticleListFooterHolder(View itemView) {
        super(itemView);
        footerTitle = (TextView) itemView.findViewById(R.id.footerTitle);
    }
}
