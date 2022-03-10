package com.hollysmart.newsmodule.adapter;

import android.text.TextUtils;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.hollysmart.newsmodule.bean.ContentBean;
import com.hollysmart.newsmodule.holder.ItemHolder;
import com.hollysmart.newsmodule.holder.ItemHolderFactory;
import com.hollysmart.newsmodule.utils.CaiUtils;
import com.hollysmart.newsmodule.value.Config;




import java.util.List;


public class ContentAdapter extends RecyclerView.Adapter<ItemHolder>{
    private List<ContentBean> contentBeanList;
    private boolean showTags; // 是否显示标签
    public ContentAdapter(List<ContentBean> contentBeanList, boolean showTags) {
        this.contentBeanList = contentBeanList;
        this.showTags = showTags;
    }
    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       return ItemHolderFactory.getItemHolder(parent, viewType, showTags);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        holder.setData(contentBeanList.get(position));
    }

    @Override
    public int getItemCount() {
        return contentBeanList.size();
    }


    @Override
    public int getItemViewType(int position) {
        int itemtype = 0;
        ContentBean contentBean = contentBeanList.get(position);
        if (contentBean.getDataBean() == null) {
            return Config.TEXT_TYPE;
        }
        String sftp = contentBean.getDataBean().getSftp();
        String appListPic = contentBean.getDataBean().getAppListPic();

        if (TextUtils.isEmpty(sftp)) {
            return Config.TEXT_TYPE;
        }

        switch (sftp) {
            case "image":
                if (CaiUtils.isEmpty(appListPic)) {
                    itemtype = Config.IMG_TYPE_TEXT_LEFT_IMG_RIGHT;
                } else {
                    switch (appListPic) {
                        case "1":
                            itemtype = Config.IMG_TYPE_TEXT_LEFT_IMG_RIGHT;
                            break;
                        case "2":
                            itemtype= Config.IMG_TYPE_BIG;
                            break;
                        case "3":
                            itemtype= Config.IMG_TYPE_THREE_IMG;
                            break;
                    }

                }
                break;
            case "text":
            default:
                itemtype= Config.TEXT_TYPE;
            break;
        }
        return itemtype;
    }
}
