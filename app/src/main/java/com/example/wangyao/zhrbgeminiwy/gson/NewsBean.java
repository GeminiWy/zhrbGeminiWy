package com.example.wangyao.zhrbgeminiwy.gson;

import java.util.List;

/**
 * Created by wangyao on 2017/5/28.
 */


public class NewsBean {
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

    public List<ZhihuTop> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<ZhihuTop> top_stories) {
        this.top_stories = top_stories;
    }

    private String date;

    private List<ZhihuStory> stories;

   private List<ZhihuTop> top_stories;






}
