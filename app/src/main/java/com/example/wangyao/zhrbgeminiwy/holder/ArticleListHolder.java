package com.example.wangyao.zhrbgeminiwy.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wangyao.zhrbgeminiwy.R;
import com.example.wangyao.zhrbgeminiwy.listener.OnArticleItemClickListener;

/**
 * Created by wangyao on 2017/7/9.
 */

public class ArticleListHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView tvNewsTitle;
    public ImageView iv_lastestNews;
    private OnArticleItemClickListener itemClickListener;

    public ArticleListHolder(View itemView) {
        super(itemView);
        tvNewsTitle = (TextView) itemView.findViewById(R.id.tv_newsTitle);
        iv_lastestNews = (ImageView) itemView.findViewById(R.id.iv_lastestNews);
        tvNewsTitle.setOnClickListener(this);
        iv_lastestNews.setOnClickListener(this);
    }
    public void setItemClickListener(OnArticleItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        if (itemClickListener != null){
            itemClickListener.onItemClickListener(getAdapterPosition());
        }
    }
}
