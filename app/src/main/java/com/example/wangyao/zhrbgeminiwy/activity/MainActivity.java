package com.example.wangyao.zhrbgeminiwy.activity;

import android.content.Context;

import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.wangyao.zhrbgeminiwy.R;
import com.example.wangyao.zhrbgeminiwy.adapter.ItemAdapter;
import com.example.wangyao.zhrbgeminiwy.application.MyApplication;
import com.example.wangyao.zhrbgeminiwy.broadcastReceiver.NetworkChangeReceiver;
import com.example.wangyao.zhrbgeminiwy.gson.BeforeNewsBean;
import com.example.wangyao.zhrbgeminiwy.gson.NewsBean;
import com.example.wangyao.zhrbgeminiwy.gson.ZhihuStory;
import com.example.wangyao.zhrbgeminiwy.gson.ZhihuTop;
import com.example.wangyao.zhrbgeminiwy.holder.TopViewPagerHolder;
import com.example.wangyao.zhrbgeminiwy.listener.OnBeforeNewsBeanListener;
import com.example.wangyao.zhrbgeminiwy.listener.OnBottomListener;
import com.example.wangyao.zhrbgeminiwy.listener.OnNewsBeanListener;
import com.example.wangyao.zhrbgeminiwy.util.httpUtil.HttpUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private RecyclerView rcy_View;
    private List<ZhihuStory> list;
    private Context mContext;
    private List<View> views;
    private ViewPager mViewPager;
    private List<ImageView> imageViews;
    private List<ZhihuTop> topstories;
    private ItemAdapter itemAdapter;
    private String date;
    private SwipeRefreshLayout swipeRefreshLayout;
    public static MainActivity mainActivity;
    private NetworkChangeReceiver networkChangeReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBroadcastReceiver();
        mContext = MyApplication.getContext();
        mainActivity = this;
        initUI();
        initListView();
    }


    private void initBroadcastReceiver() {
        networkChangeReceiver = new NetworkChangeReceiver();
        IntentFilter intenFilter = new IntentFilter();
        intenFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(networkChangeReceiver, intenFilter);
    }


    private void initUI() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        rcy_View = (RecyclerView) findViewById(R.id.rcy_View);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    initListView();
                    Toast.makeText(mContext, "最新文章", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }


    /**
     * 请求网络数据并初始化主页面
     */
    private void initListView() {
        imageViews = new ArrayList<>();//顶部Viewpager的图片

        HttpUtil.getLatestArticleList(new OnNewsBeanListener() {
            @Override
            public void onSuccess(NewsBean newsBean) {
                date = newsBean.getDate();
                list = newsBean.getStories();
                topstories = newsBean.getTop_stories();
                OnBottomListener bottomListener = new OnBottomListener() {
                    @Override
                    public void onInitBottom() {
                        getBeforeArticleList();
                    }
                };
                /**
                 * 加载顶部Viewpager
                 */
                for (int i = 0; i < topstories.size(); i++) {
                    if (i > 4) {
                        break;
                    }


                    ImageView imageView = new ImageView(mContext);
                    imageView.setBackgroundResource(R.drawable.ic_menu);
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    Glide.with(mContext).load(topstories.get(i).getImage()).into(imageView);
                    imageViews.add(imageView);
                }

//                newsAdapter.loadTopArticleListener.onSuccess(topstories);//将topstories数据传入接口
                LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                itemAdapter = new ItemAdapter(list, mContext, imageViews, bottomListener, topstories);
                rcy_View.setLayoutManager(layoutManager);
                rcy_View.setAdapter(itemAdapter);
                swipeRefreshLayout.setRefreshing(false);

                getBeforeArticleList();
            }

            @Override
            public void onFailure() {

            }
        });


    }

    private void getBeforeArticleList() {
        HttpUtil.getBeforeArticleList(date, new OnBeforeNewsBeanListener() {
            @Override
            public void onSuccess(BeforeNewsBean beforeNewsBean) {
                itemAdapter.addData(beforeNewsBean.getStories());
                itemAdapter.notifyDataSetChanged();
                date = beforeNewsBean.getDate();
            }

            @Override
            public void onFailure() {

            }
        });
    }




/*    private void initViewPager(){
        List<ZhihuTop> zhihuTops = mNewsBean.getTop_stories();
        List<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(new TopstoryFragment1(mContext,zhihuTops.get(0).getImage()));
        fragments.add(new TopstoryFragment2(mContext,zhihuTops.get(1).getImage()));
        fragments.add(new TopstoryFragment3(mContext,zhihuTops.get(2).getImage()));
        fragments.add(new TopstoryFragment4(mContext,zhihuTops.get(3).getImage()));
        fragments.add(new TopstoryFragment5(mContext,zhihuTops.get(4).getImage()));
        Log.d("Wykuailaikan","ListFragment==>"+fragments);
        HeaderAdapter headerAdapter = new HeaderAdapter(getSupportFragmentManager(),fragments);
        mViewPager.setAdapter(headerAdapter);

    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
    }
}




