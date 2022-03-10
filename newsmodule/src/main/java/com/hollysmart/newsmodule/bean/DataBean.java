package com.hollysmart.newsmodule.bean;

import java.io.Serializable;

public class DataBean implements Serializable {
    private String zburl;
    private String xwrm;
    private String bj;
    private String zrbj;
    private String sftp;   // livevideo 直播 ， video 视频 //image// 图文  null 纯文本;
    private String xwfbt;
    private String lbimg;

    private String syimg;
    private String zbjxzb;
    private String haibao;
    private String appheng;
    private String appshu;
    private String applistpic;// 如果sftp 是否图片 是image 要根据该字段区分是否是大图，小图，三图；  1.小图，2大图，3 三张图

    private String ggurl;
    private String gglx;


    public String getZburl() {
        return zburl;
    }

    public void setZburl(String zburl) {
        this.zburl = zburl;
    }

    public String getXwrm() {
        return xwrm;
    }

    public void setXwrm(String xwrm) {
        this.xwrm = xwrm;
    }

    public String getBj() {
        return bj;
    }

    public void setBj(String bj) {
        this.bj = bj;
    }

    public String getZrbj() {
        return zrbj;
    }

    public void setZrbj(String zrbj) {
        this.zrbj = zrbj;
    }

    public String getSftp() {
        return sftp;
    }

    public void setSftp(String sftp) {
        this.sftp = sftp;
    }

    public String getXwfbt() {
        return xwfbt;
    }

    public void setXwfbt(String xwfbt) {
        this.xwfbt = xwfbt;
    }

    public String getLbimg() {
        return lbimg;
    }

    public void setLbimg(String lbimg) {
        this.lbimg = lbimg;
    }

    public String getSyimg() {
        return syimg;
    }

    public void setSyimg(String syimg) {
        this.syimg = syimg;
    }

    public String getZbjxzb() {
        return zbjxzb;
    }

    public void setZbjxzb(String zbjxzb) {
        this.zbjxzb = zbjxzb;
    }

    public String getHaibao() {
        return haibao;
    }

    public void setHaibao(String haibao) {
        this.haibao = haibao;
    }

    public String getAppheng() {
        return appheng;
    }

    public void setAppheng(String appheng) {
        this.appheng = appheng;
    }

    public String getAppshu() {
        return appshu;
    }

    public void setAppshu(String appshu) {
        this.appshu = appshu;
    }

    public String getGgurl() {
        return ggurl;
    }

    public void setGgurl(String ggurl) {
        this.ggurl = ggurl;
    }

    public String getGglx() {
        return gglx;
    }

    public void setGglx(String gglx) {
        this.gglx = gglx;
    }

    public String getAppListPic() {
        return applistpic;
    }

    public void setAppListPic(String appListPic) {
        this.applistpic = appListPic;
    }
}
