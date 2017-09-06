package com.example.wangyao.zhrbgeminiwy.gson;

import java.util.List;

/**
 * Created by wangyao on 2017/7/9.
 */

public class BeforeNewsBean {
    private String date;

    private List<ZhihuStory> stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<ZhihuStory> getStories() {
        return stories;
    }

    public void setStories(List<ZhihuStory> stories) {
        this.stories = stories;
    }
}
