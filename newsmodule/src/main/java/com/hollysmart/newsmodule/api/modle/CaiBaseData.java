package com.hollysmart.newsmodule.api.modle;

import java.io.Serializable;
import java.util.List;

public class CaiBaseData<T> implements Serializable {

    public int pageNo;
    public int pageSize;
    public int count;
    public long pages;
    public long totalPage;
    public int total;
    public long totalCount;
    public int currentPage;
    public List<T> list;
    public List<T> content;
    public List<T> contents;
    public List<T> commentList;

}
