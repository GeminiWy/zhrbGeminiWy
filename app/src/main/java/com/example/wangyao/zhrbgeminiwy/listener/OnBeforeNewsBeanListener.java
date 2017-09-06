package com.example.wangyao.zhrbgeminiwy.listener;

import com.example.wangyao.zhrbgeminiwy.gson.BeforeNewsBean;

/**
 * Created by wangyao on 2017/7/9.
 */

public interface OnBeforeNewsBeanListener {
    void onSuccess(BeforeNewsBean beforeNewsBean);
    void onFailure();
}
