package com.hollysmart.newsmodule.holder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hollysmart.newsmodule.bean.ContentBean;


public abstract class ItemHolder extends RecyclerView.ViewHolder  {
    public ItemHolder(@NonNull View itemView) {
        super(itemView);
    }

    public abstract void setData(ContentBean contentBean);
}
