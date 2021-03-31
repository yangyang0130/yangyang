package com.yangyang.rkq.Body;

import java.util.PropertyResourceBundle;

//创建JAVABean，保存新闻数据。
public class NewsBean {
    private String newsTitle;//新闻标题
    private String newsDate; //新闻发布来源
    private String newsImgUrl; // 新闻图片Url地址
    private String newsUrl; //新闻详情Url地址
    private String newsTime;//新闻发布时间

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsDate() {
        return newsDate;
    }

    public void setNewsDate(String newsDate) {
        this.newsDate = newsDate;
    }

    public String getNewsImgUrl() {
        return newsImgUrl;
    }

    public void setNewsImgUrl(String newsImgUrl) {
        this.newsImgUrl = newsImgUrl;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    public String getNewsTime() {
        return newsTime;
    }

    public void setNewsTime(String newsTime) {
        this.newsTime = newsTime;
    }

    @Override
    public String toString() {
        return "NewsBean{" +
                "newsTitle='" + newsTitle + '\'' +
                ", newsDate='" + newsDate + '\'' +
                ", newsImgUrl='" + newsImgUrl + '\'' +
                ", newsUrl='" + newsUrl + '\'' +
                '}';
    }
}
