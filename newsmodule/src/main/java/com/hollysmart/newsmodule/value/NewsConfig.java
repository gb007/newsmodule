package com.hollysmart.newsmodule.value;

import com.hollysmart.newsmodule.bean.LanmuBean;

import java.util.List;

/**
 *  新闻模块配置文件
 */
public class NewsConfig {

    //服务器地址
    private String BASE_URL;
    //文件服务器地址（获取的图片）
    private String FILE_URL;
    //新闻栏目（id，名称）
    private List<LanmuBean> lanmuBeans;
     //新闻栏目tab字体大小
    private int tabTitleTextSize;
    ///新闻栏目tab字体颜色
    private int titleNormalColor;
    //新闻栏目选中tab字体颜色
    private int titleSelectedColor;
    //新闻栏目tab下划线长度
    private int indicatorWidth;
    //新闻栏目tab下划线高度
    private int indicatorHeight;
    //新闻栏目tab下划线圆角
    private int indicatorRoundRadius;
    //新闻栏目tab下划线颜色
    private int indicatorColor;
    //用户是否登陆
    private boolean isLogin;
    //用户Id
    private String userdId;
    //设备唯一标识
    private String uuid;
    //javascriptObject,新闻详情页面js方法
    private Object javascriptObject;

    public String getBASE_URL() {
        return BASE_URL;
    }

    public void setBASE_URL(String BASE_URL) {
        this.BASE_URL = BASE_URL;
    }

    public String getFILE_URL() {
        return FILE_URL;
    }

    public void setFILE_URL(String FILE_URL) {
        this.FILE_URL = FILE_URL;
    }

    public List<LanmuBean> getLanmuBeans() {
        return lanmuBeans;
    }

    public void setLanmuBeans(List<LanmuBean> lanmuBeans) {
        this.lanmuBeans = lanmuBeans;
    }

    public int getTabTitleTextSize() {
        return tabTitleTextSize;
    }

    public void setTabTitleTextSize(int tabTitleTextSize) {
        this.tabTitleTextSize = tabTitleTextSize;
    }

    public int getTitleNormalColor() {
        return titleNormalColor;
    }

    public void setTitleNormalColor(int titleNormalColor) {
        this.titleNormalColor = titleNormalColor;
    }

    public int getTitleSelectedColor() {
        return titleSelectedColor;
    }

    public void setTitleSelectedColor(int titleSelectedColor) {
        this.titleSelectedColor = titleSelectedColor;
    }

    public int getIndicatorColor() {
        return indicatorColor;
    }

    public void setIndicatorColor(int indicatorColor) {
        this.indicatorColor = indicatorColor;
    }

    public int getIndicatorWidth() {
        return indicatorWidth;
    }

    public void setIndicatorWidth(int indicatorWidth) {
        this.indicatorWidth = indicatorWidth;
    }

    public int getIndicatorHeight() {
        return indicatorHeight;
    }

    public void setIndicatorHeight(int indicatorHeight) {
        this.indicatorHeight = indicatorHeight;
    }

    public int getIndicatorRoundRadius() {
        return indicatorRoundRadius;
    }

    public void setIndicatorRoundRadius(int indicatorRoundRadius) {
        this.indicatorRoundRadius = indicatorRoundRadius;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public String getUserdId() {
        return userdId;
    }

    public void setUserdId(String userdId) {
        this.userdId = userdId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Object getJavascriptObject() {
        return javascriptObject;
    }

    public void setJavascriptObject(Object javascriptObject) {
        this.javascriptObject = javascriptObject;
    }
}
