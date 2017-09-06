package com.example.wangyao.zhrbgeminiwy.util.httpUtil;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.example.wangyao.zhrbgeminiwy.activity.MainActivity;
import com.example.wangyao.zhrbgeminiwy.gson.ArticleContent;
import com.example.wangyao.zhrbgeminiwy.gson.BeforeNewsBean;
import com.example.wangyao.zhrbgeminiwy.gson.NewsBean;
import com.example.wangyao.zhrbgeminiwy.listener.OnBeforeNewsBeanListener;
import com.example.wangyao.zhrbgeminiwy.listener.OnLoadArticleContentListener;
import com.example.wangyao.zhrbgeminiwy.listener.OnNewsBeanListener;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


/**
 * 请求各种数据
 * Created by wangyao on 2017/6/26.
 */

public class HttpUtil {
    private static Gson gson;
    public static MainActivity mainActivity = new MainActivity();


    /**
     * 得到最新文章消息并回调到主线程
     * @param onNewsBeanListener
     */
    public static void getLatestArticleList(final OnNewsBeanListener onNewsBeanListener){
        OkhttpUtil.sendGetHttp(Constant.LatestArticleUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                /*mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        onNewsBeanListener.onFailure();
                    }
                });*/

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String result = null;
                final NewsBean mNewsBean;
                try {
                    result = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                gson = new Gson();
                mNewsBean = gson.fromJson(result,NewsBean.class);
                mainActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        onNewsBeanListener.onSuccess(mNewsBean);
                    }
                });
                /*mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        String result = null;
                        try {
                            result = response.body().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        gson = new Gson();
                NewsBean mNewsBean = gson.fromJson(result,NewsBean.class);
                onNewsBeanListener.onSuccess(mNewsBean);
            }
        });*/
            }
        });




    }

    /**
     * 请求过去新闻
     * @param date
     * @param onBeforeNewsBeanListener
     */
    public static void getBeforeArticleList(String date,final OnBeforeNewsBeanListener onBeforeNewsBeanListener){
        OkhttpUtil.sendGetHttp(Constant.BeforeArticleUrl + date, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
               /* mHandler1.post(new Runnable() {
                    @Override
                    public void run() {
                        onBeforeNewsBeanListener.onFailure();
                    }
                });
*/
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String result = null;
                final BeforeNewsBean beforeNewsBean;
                try {
                    result = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                beforeNewsBean = gson.fromJson(result,BeforeNewsBean.class);
                mainActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        onBeforeNewsBeanListener.onSuccess(beforeNewsBean);
                        Log.d("Wykuailaikan","BeforeNewsBean==>"+beforeNewsBean);
                    }
                });
                /*mHandler1.post(new Runnable() {
                    @Override
                    public void run() {

                    }
                });*/


            }
        });
    }

    /**
     * 请求新闻文章内容
     * @param id
     * @param onLoadArticleContentListener
     */
    public static void getArticleContent(int id , final OnLoadArticleContentListener onLoadArticleContentListener){
        OkhttpUtil.sendGetHttp(Constant.ArticleContentUrl + id, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                /*mHandler2.post(new Runnable() {
                    @Override
                    public void run() {
                        if (onLoadArticleContentListener != null) {
                            onLoadArticleContentListener.onFailure();
                        }

                    }
                });*/

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String result = null;
                final ArticleContent articleContent;
                try {
                    result = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                articleContent = gson.fromJson(result,ArticleContent.class);
                if (articleContent != null && !TextUtils.isEmpty(articleContent.getBody())){
                    StringBuilder sb = new StringBuilder();
                    sb.append("<html><head>");
                    sb.append("<link rel=\"stylesheet\" href=\"file:///android_asset/src.css\" type=\"text/css\">");
                    sb.append("</head><body>");
                    sb.append(articleContent.getBody());
                    sb.append("</body></html>");
                    String html = sb.toString().replace("<div class=\"img-place-holder\">","");

//                    String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/src.css\" type=\"text/css\">";
//                    String html = "<html><head>"+ css +"</head><body>"+ articleContent.getBody() +"</body></html>";
//                    html = html.replace("<div class=\"img-place-holder\">","");
                    articleContent.setBody(html);
                mainActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                            if (onLoadArticleContentListener != null) {
                                onLoadArticleContentListener.onSuccess(articleContent);
                            }
                        }
                    });
                }
               /* mHandler2.post(new Runnable() {
                    @Override
                    public void run() {

                };


            });*/
        }
    });

}
}
