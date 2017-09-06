package com.example.wangyao.zhrbgeminiwy.listener;

import com.example.wangyao.zhrbgeminiwy.gson.ArticleContent;

/**
 * Created by wangyao on 2017/7/9.
 */

public interface OnLoadArticleContentListener {
    void onSuccess(ArticleContent articleContent);
    void onFailure();
}
