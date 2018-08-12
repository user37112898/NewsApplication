package com.example.yt.newsapplication;

/**
 * Created by yt on 03-Mar-18.
 */

public class News {
    String img;
    String title;
    String desc;
    String source;

    public News(String iimg, String ititle, String idesc, String isource){
        img=iimg;
        title=ititle;
        desc=idesc;
        source=isource;
    }

    public String getImg() {
        return img;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getSource() {
        return source;
    }
}
