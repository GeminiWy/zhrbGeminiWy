package com.example.wangyao.zhrbgeminiwy.gson;

import java.util.List;

/**
 * Created by wangyao on 2017/5/29.
 */

public class ZhihuStory {
    private int id;
    private int type;
    private String title;
    private List<String> images;
    private String ga_prefix;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }
}
