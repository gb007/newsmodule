package com.hollysmart.newsmodule.bean;

import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.List;

public class ContentBean implements Serializable {

    private String id;
    private String title;
    private String description;
    private String data;

    private DataBean dataBean;

    private String url;
    private int sort;
    private String text;
    private int categoryId;
    private String cover;
    private String author;
    private String source;
    private String publishDate;
    private String tagIds; // 标签
    private List<TagBean> tags; // 标签
    private List<ThreeImgBean> cmsContentFiles; // 类别是三图时，三图的数据；

    private JsonObject numberJsonObject;
    private boolean canConment = false; // 是否可以评论


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public DataBean getDataBean() {
        return dataBean;
    }

    public void setDataBean(DataBean dataBean) {
        this.dataBean = dataBean;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }


    public JsonObject getNumberJsonObject() {
        return numberJsonObject;
    }

    public void setNumberJsonObject(JsonObject numberJsonObject) {
        this.numberJsonObject = numberJsonObject;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTagIds() {
        return tagIds;
    }

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
    }

    public List<TagBean> getTags() {
        return tags;
    }

    public void setTags(List<TagBean> tags) {
        this.tags = tags;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public boolean isCanConment() {
        return canConment;
    }

    public void setCanConment(boolean canConment) {
        this.canConment = canConment;
    }

    public List<ThreeImgBean> getCmsContentFiles() {
        return cmsContentFiles;
    }

    public void setCmsContentFiles(List<ThreeImgBean> cmsContentFiles) {
        this.cmsContentFiles = cmsContentFiles;
    }
}

