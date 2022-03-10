package com.hollysmart.newsmodule.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.lzy.okgo.OkGo;

public abstract class CaiBaseFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
    }

    @Override
    public void onDestroy() {
        OkGo.getInstance().cancelTag(this);
        super.onDestroy();
    }

    /**
     * 是否执行懒加载
     */
    private boolean isLoaded = false;

    /**
     * 当前Fragment是否对用户可见
     */
    private boolean isVisibleToUser = false;

    /**
     * 当使用ViewPager+Fragment形式会调用该方法时，setUserVisibleHint会优先Fragment生命周期函数调用，
     * 所以这个时候就,会导致在setUserVisibleHint方法执行时就执行了懒加载，
     * 而不是在onResume方法实际调用的时候执行懒加载。所以需要这个变量
     */
    private boolean isCallResume = false;


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        isVisibleToUser = !hidden;
        judgeLazyInit();
    }


    @Override
    public void onResume() {
        super.onResume();
        isCallResume = true;
//        judgeLazyInit();
    }

    private void judgeLazyInit() {
        if (!isLoaded && isVisibleToUser && isCallResume) {
            lazyInit();
            isLoaded = true;
        }
    }
    abstract void lazyInit();



}
