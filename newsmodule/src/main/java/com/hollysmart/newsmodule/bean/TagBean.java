package com.hollysmart.newsmodule.bean;

import java.io.Serializable;

public class TagBean implements Serializable {

	private static final long serialVersionUID = 1L;

//	"id": "19", // 标签ID
//			"siteId": 1, // 站点ID
//			"name": "走进冰雪", // 标签名称
//			"typeId": 0, // 标签分类
//			"searchCount": 0 // 搜索次数
	private String id;
	private String siteId;
	private String name;
	private String typeId;
	private String searchCount;


	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getSearchCount() {
		return searchCount;
	}

	public void setSearchCount(String searchCount) {
		this.searchCount = searchCount;
	}
}