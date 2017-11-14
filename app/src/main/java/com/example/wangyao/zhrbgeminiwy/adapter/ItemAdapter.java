package com.example.wangyao.zhrbgeminiwy.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.wangyao.zhrbgeminiwy.R;
import com.example.wangyao.zhrbgeminiwy.activity.ArticleContentActivity;
import com.example.wangyao.zhrbgeminiwy.activity.MainActivity;
import com.example.wangyao.zhrbgeminiwy.gson.ZhihuStory;
import com.example.wangyao.zhrbgeminiwy.gson.ZhihuTop;
import com.example.wangyao.zhrbgeminiwy.holder.ArticleListFooterHolder;
import com.example.wangyao.zhrbgeminiwy.holder.ArticleListHolder;
import com.example.wangyao.zhrbgeminiwy.holder.TopViewPagerHolder;
import com.example.wangyao.zhrbgeminiwy.listener.OnArticleItemClickListener;
import com.example.wangyao.zhrbgeminiwy.listener.OnBottomListener;
import com.example.wangyao.zhrbgeminiwy.listener.OnTopItemContentListener;


import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * 主体列表adapter
 * Created by wangyao on 2017/7/9.
 */

public class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ZhihuStory> zhihuStories;

    private Context mContext;

    private final int TYPE_TOP = 0;

    private final int TYPE_ARTICLE = 1;

    private final int TYPE_FOOTER = 2;

    private LayoutInflater inflater;

    private List<ImageView> imageViews;

    private OnBottomListener bottomListener;

    private List<ZhihuTop> zhihuTops;
    private int currentItem ;

    private ScheduledExecutorService scheduledExecutorService;
    private Handler handler = new Handler(){//收到
        @Override
        public void handleMessage(Message msg) {
            handler = new Handler(){
                @Override
                public void handleMessage(Message msg) {//当收到0的时候，页数加1
                                Log.d("wykuailaikan", "currentItem2=" + currentItem);
                                if (currentItem % imageViews.size() == 0) {//判断是不是第一张，第一张的时候不设置翻页动画
                                    topViewPagerHolder.vp_Top.setCurrentItem(currentItem, false);//不设置翻页动画
                                } else {
                                    topViewPagerHolder.vp_Top.setCurrentItem(currentItem);
                                }
                }
            };
        }
    };

    private TopViewPagerHolder topViewPagerHolder;
    private HeaderAdapter headerAdapter;


    public ItemAdapter(List<ZhihuStory> zhihuStories, Context mContext, List<ImageView> imageViews, OnBottomListener onBottomListener, List<ZhihuTop> zhihuTops){
        this.zhihuStories = zhihuStories;
        this.mContext = mContext;
        this.imageViews = imageViews;
        this.bottomListener = onBottomListener;
        this.zhihuTops = zhihuTops;
        inflater = LayoutInflater.from(mContext);
        this.currentItem = ((Short.MAX_VALUE / 2)/imageViews.size())*imageViews.size();
    }



    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_TOP;
        }
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        }
        return TYPE_ARTICLE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = null;
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType){
            case TYPE_ARTICLE:
                view = LayoutInflater.from(mContext).inflate(R.layout.news_item,parent,false);
                viewHolder = new ArticleListHolder(view);
                break;
            case TYPE_FOOTER:
                view = LayoutInflater.from(mContext).inflate(R.layout.footer_layout,parent,false);
                viewHolder = new ArticleListFooterHolder(view);
                break;
            case TYPE_TOP:
                view = LayoutInflater.from(mContext).inflate(R.layout.top_vp_layout,parent,false);
                viewHolder = new TopViewPagerHolder(view);
                break;
        }
        return viewHolder;
        /*if (viewType == TYPE_ARTICLE) {
        view = inflater.inflate(R.layout.news_item,parent,false);
            return new ArticleListHolder(view);
        }else if (viewType == TYPE_FOOTER) {
            view = inflater.inflate(R.layout.footer_layout,parent,false);
            return new ArticleListFooterHolder(view);
        }
        view = inflater.inflate(R.layout.top_vp_layout,parent,false);

        return new TopViewPagerHolder(view);*/
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_ARTICLE:
                ZhihuStory zhihuStory = zhihuStories.get(position);
                ArticleListHolder articleListHolder = (ArticleListHolder) holder;
                articleListHolder.tvNewsTitle.setText(zhihuStory.getTitle());
                /**
                 * 添加监听事件，传入id和启动详细新闻
                 */
                articleListHolder.setItemClickListener(new OnArticleItemClickListener() {
                    @Override
                    public void onItemClickListener(int position) {
                        int id = zhihuStories.get(position).getId();
                        Intent intent = new Intent(MainActivity.mainActivity, ArticleContentActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("ID",id);
                        intent.putExtras(bundle);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                        Log.d("Wykuailaikan","id==>"+id);
                    }
                });
                Glide.with(mContext).load(zhihuStory.getImages().get(0)).into(articleListHolder.iv_lastestNews);
                break;
            case TYPE_FOOTER:
                //只有当文章数量不为零时才启用事件监听
                if (bottomListener != null && zhihuStories != null && zhihuStories.size() > 0) {
                    bottomListener.onInitBottom();
                }
                break;


            case TYPE_TOP:
                topViewPagerHolder = (TopViewPagerHolder) holder;
                topViewPagerHolder.vp_Top.setCurrentItem(currentItem, false);
                                                                             //除以imageviews.size再乘，将余数去除，保证是在中间的第一张。

                headerAdapter = new HeaderAdapter(imageViews);
                topViewPagerHolder.vp_Top.setAdapter(headerAdapter);

                topViewPagerHolder.vp_Top.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        currentItem = position;
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {


                    }
                });
                scheduledExecutorService = Executors.newScheduledThreadPool(1);
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        synchronized (topViewPagerHolder.vp_Top) {
                            Log.d("wykuailaikan", "执行了两次");
                            currentItem = currentItem+1;
                            handler.obtainMessage().sendToTarget();
                            Log.d("wykuailaikan", "topViewPagerHolder.vp_Top.getCurrentItem()=" + topViewPagerHolder.vp_Top.getCurrentItem());
                        }
                    }
                };
                scheduledExecutorService.scheduleAtFixedRate(runnable, 1000, 3000, TimeUnit.MILLISECONDS);

                headerAdapter.setOnTopItemContentListener(new OnTopItemContentListener() {
                    @Override
                    public void onClick(int id) {
                        id = zhihuTops.get(id).getId();
                        Bundle bundle = new Bundle();
                        bundle.putInt("ID",id);
                        Intent intent = new Intent(MainActivity.mainActivity,ArticleContentActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtras(bundle);
                        mContext.startActivity(intent);
                    }
                });
                break;
        }

    }



    @Override
    public int getItemCount() {
        return zhihuStories.size();
    }
    //加载下一页文章内容时使用
    public void addData(List<ZhihuStory> zhihuStories){
        this.zhihuStories.addAll(zhihuStories);
        Log.d("Wykuailaikan","zhihuStories==>"+zhihuStories);
    }

}



