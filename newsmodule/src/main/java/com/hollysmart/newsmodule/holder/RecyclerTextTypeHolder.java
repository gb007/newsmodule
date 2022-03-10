package com.hollysmart.newsmodule.holder;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hollysmart.newsmodule.R;
import com.hollysmart.newsmodule.activity.NewsDetailsActivity;
import com.hollysmart.newsmodule.bean.ContentBean;
import com.hollysmart.newsmodule.bean.TagBean;
import com.hollysmart.newsmodule.utils.CaiUtils;



import org.greenrobot.eventbus.EventBus;

import java.util.List;


/**
 * Created by guoshuyu on 2017/1/9.
 */

public class RecyclerTextTypeHolder extends ItemHolder {

    public final static String TAG = "RecyclerView2List";
    protected Context context = null;
    private TextView tv_title;
    private TextView tv_tag;
    private TextView tv_new_date;
    private LinearLayout ll_item;

    public RecyclerTextTypeHolder(Context context, View v) {
        super(v);
        this.context = context;
        tv_title = v.findViewById(R.id.tv_title);
        tv_tag = v.findViewById(R.id.tv_tag);
        tv_new_date = v.findViewById(R.id.tv_new_date);
        ll_item = v.findViewById(R.id.ll_item);

    }

    @Override
    public void setData(ContentBean contentBean) {
        List<TagBean> tagBeans = contentBean.getTags();
        String tags = "";
        if (tagBeans != null && tagBeans.size() > 0) {
            String names = tagBeans.get(0).getName();
            String[] arr = names.split("\\s+");
            tags = arr[0];

        }
        tv_title.setText(contentBean.getTitle());
        if (TextUtils.isEmpty(tags)) {
            tv_tag.setVisibility(View.GONE);
        } else {
            tv_tag.setVisibility(View.VISIBLE);
            tv_tag.setText(tags);
        }

        if (contentBean.getCategoryId() == 143){
            //143为社团，社团显示简介
            tv_new_date.setText(contentBean.getDescription());
        }else {
            String laiyuan = "";
            if (!CaiUtils.isEmpty(contentBean.getSource())) {
                laiyuan = contentBean.getSource();
            } else if (!CaiUtils.isEmpty(contentBean.getAuthor())) {
                laiyuan = contentBean.getAuthor();
            } else {
                laiyuan = "未知";
            }
            if (!CaiUtils.isEmpty(contentBean.getPublishDate())) {
                String publishDate = contentBean.getPublishDate();
                try {
                    String s = CaiUtils.timeStamp2Date(publishDate, "yyyy-MM-dd");
                    if (CaiUtils.isEmpty(laiyuan)) {
                        laiyuan += s;
                    } else {
                        laiyuan += " " + s;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            tv_new_date.setText(laiyuan);
        }



        ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().postSticky(contentBean);
                context.startActivity(new Intent(context, NewsDetailsActivity.class));
            }
        });
    }
}