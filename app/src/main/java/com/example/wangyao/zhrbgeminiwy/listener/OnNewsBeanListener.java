package com.example.wangyao.zhrbgeminiwy.listener;

import com.example.wangyao.zhrbgeminiwy.gson.NewsBean;

/**
 * Created by wangyao on 2017/6/26.
 */

public interface OnNewsBeanListener {
    void onSuccess(NewsBean newsBean);
    void onFailure();
}
