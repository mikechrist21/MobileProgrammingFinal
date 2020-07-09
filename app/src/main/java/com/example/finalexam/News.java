package com.example.finalexam;

public class News {
    public String newsTitle;
    public String newsDescription;
    public String newsContent;
    public String newsURL;
    public String thumbnail;

    public News(){

    }

    public News(String newsTitle, String newsDescription, String newsContent, String newsURL, String thumbnail){
        this.newsTitle = newsTitle;
        this.newsDescription = newsDescription;
        this.newsContent = newsContent;
        this.newsURL = newsURL;
        this.thumbnail = thumbnail;
    }
}

