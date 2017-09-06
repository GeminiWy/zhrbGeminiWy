package com.example.wangyao.zhrbgeminiwy.broadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by wangyao on 2017/8/18.
 */

public class NetworkChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo!=null && networkInfo.isAvailable()){
            Toast.makeText(context,"network is connected",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context,"network can not be connected",Toast.LENGTH_SHORT).show();
        }
    }
}
