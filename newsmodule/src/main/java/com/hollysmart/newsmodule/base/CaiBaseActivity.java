package com.hollysmart.newsmodule.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.hollysmart.newsmodule.R;
import com.hollysmart.newsmodule.value.Value;
import com.hollysmart.newsmodule.view.statusbar.StatusBarUtil;
import com.lzy.okgo.OkGo;


public abstract class CaiBaseActivity extends AppCompatActivity implements View.OnClickListener{
    public Context mContext;
    public int animType;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        if (savedInstanceState != null) {
            animType = savedInstanceState.getInt(Value.ANIM_TYPE);
        }
        animType = getIntent().getIntExtra(Value.ANIM_TYPE, 0);

        setContentView(layoutResID());

        if(setTranslucent()){
            //这里注意下 调用setRootViewFitsSystemWindows 里面 winContent.getChildCount()=0 导致代码无法继续
            //是因为你需要在setContentView之后才可以调用 setRootViewFitsSystemWindows
            //当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
            StatusBarUtil.setRootViewFitsSystemWindows(this,false);
            //设置状态栏透明
            StatusBarUtil.setTranslucentStatus(this);
            StatusBarUtil.setStatusBarDarkTheme(this, false);
        }
        findView();
        init();
    }

    @Override
    protected void onDestroy() {
        OkGo.getInstance().cancelTag(this);
        super.onDestroy();
    }

    /**
     * layout绑定
     */
    public abstract int layoutResID();

    /**
     * 设置状态栏透明
     * @return
     */
    public abstract boolean setTranslucent();

    /**
     * 控件绑定
     */
    public abstract void findView();

    /**
     * 逻辑操作
     */
    public abstract void init();



    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        animEnter(intent.getIntExtra(Value.ANIM_TYPE, 0));
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        animEnter(intent.getIntExtra(Value.ANIM_TYPE, 0));
    }

    @Override
    public void finish() {
        super.finish();
        animExit();
    }
    private void animEnter(int animType){
        switch (animType) {
            case Value.ANIM_TYPE_SHANG:
                overridePendingTransition(R.anim.news_module_activity_enter_shang, R.anim.news_module_activity_yuandian);
                break;
            case Value.ANIM_TYPE_XIA:
                overridePendingTransition(R.anim.news_module_activity_enter_xia, R.anim.news_module_activity_yuandian);
                break;
            case Value.ANIM_TYPE_LEFT:
                overridePendingTransition(R.anim.news_module_activity_enter_left, R.anim.news_module_activity_yuandian);
                break;
            case Value.ANIM_TYPE_RIGHT:
                overridePendingTransition(R.anim.news_module_activity_enter_right, R.anim.news_module_activity_yuandian);
                break;
            case Value.ANIM_TYPE_SUOFANG:
                overridePendingTransition(R.anim.news_module_activity_enter_suofang, R.anim.news_module_activity_yuandian);
                break;
            case Value.ANIM_TYPE_LONG_LEFT:
                overridePendingTransition(R.anim.news_module_activity_enter_long_left, R.anim.news_module_activity_yuandian);
                break;
            case Value.ANIM_TYPE_LONG_RIGHT:
                overridePendingTransition(R.anim.news_module_activity_enter_long_right, R.anim.news_module_activity_yuandian);
                break;
            case Value.ANIM_TYPE_ALPHA:
                overridePendingTransition(R.anim.news_module_activity_enter_alpha, R.anim.news_module_activity_yuandian);
                break;
        }
    }

    private void animExit(){
        switch (animType) {
            case Value.ANIM_TYPE_SHANG:
                overridePendingTransition(R.anim.news_module_activity_yuandian, R.anim.news_module_activity_exit_shang);
                break;
            case Value.ANIM_TYPE_XIA:
                overridePendingTransition(R.anim.news_module_activity_yuandian, R.anim.news_module_activity_exit_xia);
                break;
            case Value.ANIM_TYPE_LEFT:
                overridePendingTransition(R.anim.news_module_activity_yuandian, R.anim.news_module_activity_exit_left);
                break;
            case Value.ANIM_TYPE_RIGHT:
                overridePendingTransition(R.anim.news_module_activity_yuandian, R.anim.news_module_activity_exit_right);
                break;
            case Value.ANIM_TYPE_SUOFANG:
                overridePendingTransition(R.anim.news_module_activity_yuandian, R.anim.news_module_activity_exit_suofang);
                break;
            case Value.ANIM_TYPE_LONG_LEFT:
                overridePendingTransition(R.anim.news_module_activity_yuandian, R.anim.news_module_activity_exit_long_left);
                break;
            case Value.ANIM_TYPE_LONG_RIGHT:
                overridePendingTransition(R.anim.news_module_activity_yuandian, R.anim.news_module_activity_exit_long_right);
                break;
            case Value.ANIM_TYPE_ALPHA:
                overridePendingTransition(R.anim.news_module_activity_yuandian, R.anim.news_module_activity_exit_alpha);
                break;
        }
    }

}









