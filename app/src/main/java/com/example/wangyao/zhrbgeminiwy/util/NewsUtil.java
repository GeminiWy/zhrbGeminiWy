package com.example.wangyao.zhrbgeminiwy.util;

/**得到新闻数据
 * Created by wangyao on 2017/5/28.
 */

/*
public class NewsUtil {
    private static NewsBean newsBean;
    private static List<ZhihuStory> list;
    public NewsUtil(Handler mHandler){

    }


    public static List<ZhihuStory> getNewsBeanFromNetWork(){

        final Request request = new Request.Builder().url("http://news-at.zhihu.com/api/4/news/latest").build();
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String result = response.body().string();
                Log.d("WykuailaiLook", "result:== "+result);
                        Gson gson = new Gson();
                        newsBean = gson.fromJson(result,NewsBean.class);
                        Log.d("WykuailaiLook", "newsBean:== "+newsBean);
                   list = newsBean.getStories();
                  Log.d("WykuailaiLook", "List<ZhihuStory>:== "+list);


                    }
                });


                //将数据缓存到数据库


            }
        };
*/






