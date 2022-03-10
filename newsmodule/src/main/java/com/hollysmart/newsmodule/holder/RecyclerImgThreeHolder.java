package com.hollysmart.newsmodule.holder;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.hollysmart.newsmodule.R;
import com.hollysmart.newsmodule.activity.NewsDetailsActivity;
import com.hollysmart.newsmodule.bean.ContentBean;
import com.hollysmart.newsmodule.bean.TagBean;
import com.hollysmart.newsmodule.bean.ThreeImgBean;
import com.hollysmart.newsmodule.utils.CaiUtils;
import com.hollysmart.newsmodule.utils.CenterCropRoundCornerTransform;
import com.hollysmart.newsmodule.utils.SizeUtils;
import com.hollysmart.newsmodule.value.Value;


import org.greenrobot.eventbus.EventBus;

import java.util.List;


/**
 * Created by guoshuyu on 2017/1/9.
 */

public class RecyclerImgThreeHolder extends ItemHolder {

    public final static String TAG = "RecyclerView2List";

    protected Context context = null;

    private TextView tv_title;
    private LinearLayout ll_item;
    private ImageView imgleft;
    private ImageView imgcenter;
    private ImageView imgright;
    private TextView tv_new_date;
    private TextView tv_tag;

    public RecyclerImgThreeHolder(Context context, View v) {
        super(v);
        this.context = context;
        tv_title = v.findViewById(R.id.tv_title);
        ll_item = v.findViewById(R.id.ll_item);
        imgleft = v.findViewById(R.id.iv_left);
        imgcenter = v.findViewById(R.id.iv_center);
        tv_new_date = v.findViewById(R.id.tv_new_date);
        tv_tag = v.findViewById(R.id.tv_tag);
        imgright = v.findViewById(R.id.iv_right);
    }


    private void setImgResource(List<ThreeImgBean> cmsImageList, int i, ImageView imgleft) {
        Glide.with(context)
                .load(Value.FILE_URL + cmsImageList.get(i).getFilePath())
                .error(R.drawable.news_module_bg_yuanjiao_danlan_6dp)
                .placeholder(R.drawable.news_module_bg_yuanjiao_danlan_6dp)
                .dontAnimate()
                .centerCrop()
                .transform(new CenterCrop(), new CenterCropRoundCornerTransform(SizeUtils.dp2px(6)))
                .into(imgleft);
    }

    @Override
    public void setData(ContentBean contentBean) {
        if (!CaiUtils.isEmpty(contentBean.getTitle())) {
            tv_title.setText(contentBean.getTitle());
        }
        List<TagBean> tagBeans = contentBean.getTags();
        String tags = "";
        if (tagBeans != null && tagBeans.size() > 0) {
            String names = tagBeans.get(0).getName();
            String[] arr = names.split("\\s+");
            tags = arr[0];
        }
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

        List<ThreeImgBean> cmsImageList = contentBean.getCmsContentFiles();

        if ((cmsImageList != null && cmsImageList.size() > 0) && cmsImageList.size() == 3) {
            setImgResource(cmsImageList, 0, imgleft);
            setImgResource(cmsImageList, 1, imgcenter);
            setImgResource(cmsImageList, 2, imgright);

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