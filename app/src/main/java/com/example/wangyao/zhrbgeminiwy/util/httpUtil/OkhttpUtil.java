package com.example.wangyao.zhrbgeminiwy.util.httpUtil;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * 发送okhttp的工具类
 * Created by wangyao on 2017/6/16.
 */

class OkhttpUtil {

    public static void sendGetHttp(String url , Callback callback){
        Request request = new Request.Builder().url(url).build();
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(callback);
    }

}
