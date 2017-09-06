package com.example.wangyao.zhrbgeminiwy.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.wangyao.zhrbgeminiwy.R;
import com.example.wangyao.zhrbgeminiwy.application.MyApplication;
import com.example.wangyao.zhrbgeminiwy.db.MyHelperUtil;
import com.example.wangyao.zhrbgeminiwy.gson.ArticleContent;
import com.example.wangyao.zhrbgeminiwy.gson.DBContent;
import com.example.wangyao.zhrbgeminiwy.listener.OnLoadArticleContentListener;
import com.example.wangyao.zhrbgeminiwy.util.httpUtil.HttpUtil;

public class ArticleContentActivity extends AppCompatActivity {
    private WebView mWebView;
    private ImageView mImageView;
    private MyHelperUtil myHelperUtil = MyHelperUtil.getInstence(MyApplication.getContext());;
    private int id;
    private boolean check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_content_layout);
        init();
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            id = bundle.getInt("ID");
            Thread thread1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    check = myHelperUtil.isQueryContent(id);
                }
            });
            thread1.start();

            if (check){
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        myHelperUtil.queryContent(id);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mWebView.loadDataWithBaseURL("x-data://base", DBContent.body, "text/html", "UTF-8", null);
                                Glide.with(ArticleContentActivity.this).load(DBContent.images).into(mImageView);
                            }
                        });
                    }
                });
                thread.start();

            }else {

                HttpUtil.getArticleContent(id, new OnLoadArticleContentListener() {
                    @Override
                    public void onSuccess(ArticleContent articleContent) {
                        myHelperUtil.saveContent(articleContent);
                        mWebView.loadDataWithBaseURL("x-data://base", articleContent.getBody(), "text/html", "UTF-8", null);

                        Glide.with(ArticleContentActivity.this).load(articleContent.getImage()).into(mImageView);
                    }

                    @Override
                    public void onFailure() {

                    }
                });
            }

        }

        }



    private void init() {
         Toolbar toolbar= (Toolbar) getFragmentManager().findFragmentById(R.id.article_content_fragment).getView().findViewById(R.id.article_toolbar);

        toolbar.setTitle("享受阅读的乐趣");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArticleContentActivity.this.finish();
                }
            });

        mWebView = (WebView) getFragmentManager().findFragmentById(R.id.article_content_fragment).getView().findViewById(R.id.webView);
        mImageView = (ImageView) getFragmentManager().findFragmentById(R.id.article_content_fragment).getView().findViewById(R.id.iv_articleContentTop);
//        WebSettings settings = mWebView.getSettings();
//        settings.setBlockNetworkImage(true);
 //        settings.setJavaScriptEnabled(false);
//        settings.setDomStorageEnabled(true);// 开启 DOM storage 功能
//        settings.setAppCacheEnabled(true);// 开启H5缓存功能

//        settings.setCacheMode(WebSettings.LOAD_DEFAULT);

//        settings.setAllowFileAccess(true);// 可以读取文件缓存(manifest生效)

    }

}
