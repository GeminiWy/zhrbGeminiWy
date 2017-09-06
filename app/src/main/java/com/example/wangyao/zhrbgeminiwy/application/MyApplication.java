package com.example.wangyao.zhrbgeminiwy.application;

import android.app.Application;
import android.content.Context;

/**
 * Created by wangyao on 2017/7/23.
 */

public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
    public static Context getContext(){
        return context;
    }
}
