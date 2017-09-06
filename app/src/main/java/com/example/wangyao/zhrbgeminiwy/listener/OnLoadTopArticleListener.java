package com.example.wangyao.zhrbgeminiwy.listener;

import com.example.wangyao.zhrbgeminiwy.gson.ZhihuTop;

import java.util.List;

/**
 * Created by wangyao on 2017/7/1.
 */

public interface OnLoadTopArticleListener {

        void onSuccess(List<ZhihuTop> topStoriesList);

        void onFailure();
    }

