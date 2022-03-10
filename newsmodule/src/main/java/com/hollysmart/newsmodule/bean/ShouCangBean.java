package com.hollysmart.newsmodule.bean;

import java.io.Serializable;

public class ShouCangBean implements Serializable {

    private String singleStoreCount;
    private String totalStoreCount;
    private boolean status;

    public String getSingleStoreCount() {
        return singleStoreCount;
    }

    public void setSingleStoreCount(String singleStoreCount) {
        this.singleStoreCount = singleStoreCount;
    }

    public String getTotalStoreCount() {
        return totalStoreCount;
    }

    public void setTotalStoreCount(String totalStoreCount) {
        this.totalStoreCount = totalStoreCount;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}



