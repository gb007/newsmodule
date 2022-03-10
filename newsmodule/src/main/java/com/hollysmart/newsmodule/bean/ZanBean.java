package com.hollysmart.newsmodule.bean;

import java.io.Serializable;

public class ZanBean implements Serializable {

    private String totalCount;
    private String singleCount;
    private boolean liked;

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public String getSingleCount() {
        return singleCount;
    }

    public void setSingleCount(String singleCount) {
        this.singleCount = singleCount;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }
}



