package com.example.wanandroid.bean;

import java.io.Serializable;

/**
 * @className: ArticleBean
 * @author: Voyager
 * @description: 简化版文章数据类，便于RecyclerView的使用
 * @date: 2023/6/25
 **/
public class ArticleBean implements Serializable {
    public static final String TAG = "articleBean";
    /**
     * 文章作者
     */
    private String author;
    /**
     * 文章分享人（这是一篇转载文章的话转载）
     */
    private String shareUser;
    /**
     * 文章标题
     */
    private String title;
    /**
     * 文章链接
     */
    private String url;
    /**
     * 文章发布时间
     */
    private String date;
    /**
     * 文章种类：置顶的文章--1.普通文章--0
     */
    private int type;

    /**
     * 文章章节名称
     */
    private String chapterName;

    /**
     * 文章id，可用来进行收藏相关的操作
     */
    private int id;

    /**
     * 收藏文章列表中储存文章原本id
     */
    private int originId;


    public ArticleBean(String author, String shareUser, String title, String url, String date, int type, String chapterName) {
        this.author = author;
        this.shareUser = shareUser;
        this.title = title;
        this.url = url;
        this.date = date;
        this.type = type;
        this.chapterName = chapterName;
    }

    public ArticleBean() {
    }

    public int getOriginId() {
        return originId;
    }

    public void setOriginId(int originId) {
        this.originId = originId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getShareUser() {
        return shareUser;
    }

    public void setShareUser(String shareUser) {
        if (shareUser != null) {
            this.shareUser = shareUser;
        } else {
            this.shareUser = "0";
        }

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
