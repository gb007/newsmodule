package com.hollysmart.newsmodule.holder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hollysmart.newsmodule.R;
import com.hollysmart.newsmodule.value.Config;


public class ItemHolderFactory {

    public static ItemHolder getItemHolder(ViewGroup parent, int viewType, boolean showTags) {
        switch (viewType) {
            case Config.IMG_TYPE_BIG:
                //大图
                View imgbigView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_module_item_imgbig_viewholder, parent, false);
                ItemHolder imgBigHolder = new RecyclerImgBigHolder(parent.getContext(), imgbigView, showTags);
                return imgBigHolder;
            case Config.IMG_TYPE_TEXT_LEFT_IMG_RIGHT:
                //右图左文
                View imgrigtextlefview = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_module_item_imgrigtextlef_viewholder, parent, false);
                ItemHolder imgRigTextLefHolder = new RecyclerImgRigTextLefHolder(parent.getContext(), imgrigtextlefview);
                return imgRigTextLefHolder;
            case Config.IMG_TYPE_THREE_IMG:
                //三张图片
                View imgthreeview = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_module_item_imgthree_viewholder, parent, false);
                ItemHolder threeHolder = new RecyclerImgThreeHolder(parent.getContext(), imgthreeview);
                return threeHolder;

            case Config.TEXT_TYPE:
            //纯文本
                View zhidingview = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_module_item_text_viewholder, parent, false);
                RecyclerTextTypeHolder textTypeHolder = new RecyclerTextTypeHolder(parent.getContext(), zhidingview);
                return textTypeHolder;
        }
        return null;
    }
}
