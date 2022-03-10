package com.hollysmart.newsmodule.bean;

public class ThreeImgBean {

    private String id;                   //": "3",  // 新闻图片ID
    private String contentId;                   //": "406",  // 所属新闻ID
    private String userId;                   //": "1", // 用户ID
    private String filePath;                   //": "upload/2020/11-21/18-50-1307331232415248.jpg",  // 新闻图片路径
    private String image;                   //": false,
    private String size;                   //": 0,
    private String clicks;                   //": 0, // 点击量
    private String sort;                   //": 2, // 优先级
    private String description;                   //": "22" // 文件描述

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getClicks() {
        return clicks;
    }

    public void setClicks(String clicks) {
        this.clicks = clicks;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
