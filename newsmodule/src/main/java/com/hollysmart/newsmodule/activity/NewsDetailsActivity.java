package com.hollysmart.newsmodule.activity;

import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import com.hollysmart.newsmodule.R;
import com.hollysmart.newsmodule.api.ContentNumberApi;
import com.hollysmart.newsmodule.api.ShouCangApi;
import com.hollysmart.newsmodule.api.ZanApi;
import com.hollysmart.newsmodule.base.CaiBaseActivity;
import com.hollysmart.newsmodule.bean.ContentBean;
import com.hollysmart.newsmodule.bean.ShouCangBean;
import com.hollysmart.newsmodule.bean.ZanBean;
import com.hollysmart.newsmodule.dsbridge.DWebView;
import com.hollysmart.newsmodule.eventbus.EB_ShouCang;
import com.hollysmart.newsmodule.interfaces.MyInterface;
import com.hollysmart.newsmodule.utils.CCM_Delay;
import com.hollysmart.newsmodule.utils.Mlog;
import com.hollysmart.newsmodule.utils.ShareUtils;
import com.hollysmart.newsmodule.value.Value;
import com.hollysmart.newsmodule.view.dialog.LoadingDialog;
import com.hollysmart.newsmodule.view.webview.IVideo;
import com.hollysmart.newsmodule.view.webview.VideoImpl;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 新闻详情页面
 */

public class NewsDetailsActivity extends CaiBaseActivity {

    @Override
    public int layoutResID() {
        return R.layout.news_module_activity_news_details;
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public boolean setTranslucent() {
        return true;
    }

    private ImageView iv_right;
    private DWebView webView;

    private LinearLayout ll_other;

    private LinearLayout ll_dianzan;
    private ImageView iv_dianzan;
    private TextView tv_zan_num;

    private LinearLayout ll_shoucang;
    private ImageView iv_shoucang;
    private TextView tv_shoucang_num;

    @Override
    public void findView() {
        findViewById(R.id.iv_back).setOnClickListener(this);
        iv_right = findViewById(R.id.iv_right);
        iv_right.setVisibility(View.VISIBLE);
        iv_right.setOnClickListener(this::onClick);

        webView = findViewById(R.id.webview);


        ll_other = findViewById(R.id.ll_other);

        ll_dianzan = findViewById(R.id.ll_dianzan);
        iv_dianzan = findViewById(R.id.iv_dianzan);
        tv_zan_num = findViewById(R.id.tv_zan_num);

        ll_shoucang = findViewById(R.id.ll_shoucang);
        iv_shoucang = findViewById(R.id.iv_shoucang);
        tv_shoucang_num = findViewById(R.id.tv_shoucang_num);

        ll_dianzan.setOnClickListener(this::onClick);
        ll_shoucang.setOnClickListener(this::onClick);


    }


    private ContentBean contentBean;
    private LoadingDialog dialog;

    @Override
    public void init() {
        EventBus.getDefault().register(this);
        dialog = new LoadingDialog(mContext, R.style.news_module_dialog, "加载中，请稍等...");
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_back) {
            finish();
        } else if (id == R.id.iv_right) {
            share();
        } else if (id == R.id.ll_dianzan) {
            dianZan();
        } else if (id == R.id.ll_shoucang) {
            shoucang();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessage(ContentBean contentBean) {
        this.contentBean = contentBean;
        load();
        getNumber(contentBean);
    }


    /**
     * 分享
     */
    private void share() {
        String detailUrl;
        if (contentBean.getCategoryId() == 143) {
            detailUrl = Value.FILE_URL + contentBean.getUrl();
        } else {
            detailUrl = String.format(Value.FILE_URL + "app/articleDetails.html?id=%s&isapp=false", contentBean.getId());
        }
        ShareUtils.allShare(mContext, contentBean.getTitle(), detailUrl);
    }


    private void load() {

        webView.getSettings().setJavaScriptEnabled(true);

        webView.getSettings().setSavePassword(false);
        webView.getSettings().setSaveFormData(false);
        webView.getSettings().setDefaultTextEncodingName("UTF-8");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBlockNetworkImage(false);//解决图片不显示
        webView.getSettings().setBlockNetworkLoads(false);
        webView.getSettings().setTextZoom(100);//设置字体占屏幕宽度
//        webView.getSettings().setLayoutAlgorithm( WebSettings.LayoutAlgorithm.SINGLE_COLUMN ); // 图片大小
        webView.clearCache(true);
        webView.setVerticalScrollBarEnabled(false); //垂直不显示
        webView.getSettings().setMediaPlaybackRequiresUserGesture(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //允许混合（http，https）
            //websettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }
        String detailUrl;
        if (contentBean.getCategoryId() == 143) {
            //社团详情
            detailUrl = Value.FILE_URL + contentBean.getUrl();
        } else {
            detailUrl = String.format(Value.FILE_URL + "app/articleDetails.html?id=%s&isapp=true", contentBean.getId());
        }
        Mlog.d("详情：" + detailUrl);

        //TODO
//        webView.addJavascriptObject(new CaiJsApi(this), null);

        webView.loadUrl(detailUrl);

        webView.setWebViewClient(webViewClient);
        webView.setWebChromeClient(webChromeClient);
    }


    /**
     * 网页内点击超链接
     */
    WebViewClient webViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url != null) {
                Mlog.d("详情==" + url);
                webView.loadUrl(url);
            }
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }
    };


    /**
     * 网页加载进度
     */
    WebChromeClient webChromeClient = new WebChromeClient() {
        IVideo mIVideo = new VideoImpl(NewsDetailsActivity.this, webView);

        @Override
        public void onProgressChanged(WebView webView, int i) {
            super.onProgressChanged(webView, i);
            if (i == 100) {
                ll_other.setVisibility(View.VISIBLE);
                new CCM_Delay(800, new CCM_Delay.DelayIF() {
                    @Override
                    public void operate() {
                    }
                });
            }

        }

        /*** 视频播放相关的方法 **/
        @Override
        public View getVideoLoadingProgressView() {
            FrameLayout frameLayout = new FrameLayout(NewsDetailsActivity.this);
            frameLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return frameLayout;
        }

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            mIVideo.onShowCustomView(view, callback);
        }

        @Override
        public void onHideCustomView() {
            mIVideo.onHideCustomView();
        }
    };


    private boolean isLiked = false;

    private void dianZan() {
        Mlog.d("点赞了");
        JsonObject args = new JsonObject();
        if (Value.IS_LOGIN) {
            args.addProperty("userId", Value.USER_ID);
        } else {
            args.addProperty("userId", Value.UUID);
        }
        args.addProperty("contentId", contentBean.getId());
        new ZanApi(this, args, new MyInterface.DetailIF<ZanBean>() {
            @Override
            public void detailResult(boolean isOk, String msg, ZanBean data) {
                if (isOk) {
                    tv_zan_num.setText(data.getSingleCount());
                    isLiked = data.isLiked();
                    if (data.isLiked()) {
                        iv_dianzan.setImageResource(R.drawable.news_module_icon_zan_a);
                        ll_dianzan.setBackgroundResource(R.drawable.news_module_bg_stock_red_15dp);
                        tv_zan_num.setTextColor(getResources().getColor(R.color.news_module_select_red));
                    } else {
                        iv_dianzan.setImageResource(R.drawable.news_module_icon_zan_b);
                        ll_dianzan.setBackgroundResource(R.drawable.news_module_bg_stock_grey_15dp);
                        tv_zan_num.setTextColor(getResources().getColor(R.color.news_module_grey));
                    }
                }
            }
        }).request();
    }


    private void shoucang() {
        JsonObject args = new JsonObject();
        if (Value.IS_LOGIN) {
            args.addProperty("userId", Value.USER_ID);
        } else {
            args.addProperty("userId", Value.UUID);
        }
        args.addProperty("contentId", contentBean.getId());
        new ShouCangApi(this, args, new MyInterface.DetailIF<ShouCangBean>() {
            @Override
            public void detailResult(boolean isOk, String msg, ShouCangBean data) {
                if (isOk) {
                    EventBus.getDefault().post(new EB_ShouCang());
                    tv_shoucang_num.setText(data.getSingleStoreCount());
                    if (data.isStatus()) {

                        iv_shoucang.setImageResource(R.drawable.news_module_icon_shoucang_a);
                        ll_shoucang.setBackgroundResource(R.drawable.news_module_bg_stock_yellow_15dp);
                        tv_shoucang_num.setTextColor(getResources().getColor(R.color.news_module_select_yellow));
                    } else {
                        iv_shoucang.setImageResource(R.drawable.news_module_icon_shoucang_b);
                        ll_shoucang.setBackgroundResource(R.drawable.news_module_bg_stock_grey_15dp);
                        tv_shoucang_num.setTextColor(getResources().getColor(R.color.news_module_grey));
                    }
                }
            }
        }).request();
    }

    /**
     * 获取文章相关数量包含 点赞、收藏
     *
     * @param contentBean
     */
    private void getNumber(ContentBean contentBean) {
        JsonObject args = new JsonObject();
        JsonArray categoryIds = new JsonArray();
        categoryIds.add(contentBean.getId());
        args.add("contentIds", categoryIds);

        if (Value.IS_LOGIN) {
            args.addProperty("userId", Value.USER_ID);
        } else {
            args.addProperty("userId", Value.UUID);
        }
        new ContentNumberApi(this, args, new MyInterface.DetailIF<Object>() {
            @Override
            public void detailResult(boolean isOk, String msg, Object data) {
                if (isOk) {
                    JsonObject jsonObject = new Gson().toJsonTree(data).getAsJsonObject();
                    contentBean.setNumberJsonObject(jsonObject.getAsJsonObject(contentBean.getId()));
                    int zan_number = contentBean.getNumberJsonObject().get("singleLikedCount").getAsInt();
                    int shoucang_number = contentBean.getNumberJsonObject().get("singleStoreCount").getAsInt();
                    Mlog.d("点赞数：" + zan_number);
                    tv_zan_num.setText(String.valueOf(zan_number));
                    tv_shoucang_num.setText(String.valueOf(shoucang_number));
                    if (contentBean.getNumberJsonObject().get("liked").getAsBoolean()) {
                        iv_dianzan.setImageResource(R.drawable.news_module_icon_zan_a);
                        ll_dianzan.setBackgroundResource(R.drawable.news_module_bg_stock_red_15dp);
                        tv_zan_num.setTextColor(getResources().getColor(R.color.news_module_select_red));
                    }
                    if (contentBean.getNumberJsonObject().get("stored").getAsBoolean()) {
                        iv_shoucang.setImageResource(R.drawable.news_module_icon_shoucang_a);
                        ll_shoucang.setBackgroundResource(R.drawable.news_module_bg_stock_yellow_15dp);
                        tv_shoucang_num.setTextColor(getResources().getColor(R.color.news_module_select_yellow));
                    }
                }
            }
        }).request();
    }

}