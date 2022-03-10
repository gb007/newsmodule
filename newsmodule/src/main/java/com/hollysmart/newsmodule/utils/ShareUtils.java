package com.hollysmart.newsmodule.utils;

import android.content.Context;
import android.content.Intent;

public class ShareUtils {

    /**
     * Android原生分享功能
     * 默认选取手机所有可以分享的APP
     */
    public static void allShare(Context context, String title, String content) {
        Intent share_intent = new Intent();
        share_intent.setAction(Intent.ACTION_SEND);//设置分享行为
        share_intent.setType("text/plain");//设置分享内容的类型
        share_intent.putExtra(Intent.EXTRA_SUBJECT, title);//添加分享内容标题
        share_intent.putExtra(Intent.EXTRA_TEXT, title + ":" + content);//添加分享内容
        //创建分享的Dialog
        share_intent = Intent.createChooser(share_intent, "share");
        context.startActivity(share_intent);
    }

}
