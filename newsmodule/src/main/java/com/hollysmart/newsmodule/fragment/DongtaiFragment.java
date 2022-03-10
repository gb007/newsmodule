package com.hollysmart.newsmodule.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.hollysmart.newsmodule.R;
import com.hollysmart.newsmodule.bean.LanmuBean;
import com.hollysmart.newsmodule.eventbus.EB_DongTai;
import com.hollysmart.newsmodule.utils.CaiUtils;
import com.hollysmart.newsmodule.utils.Utils;
import com.hollysmart.newsmodule.value.NewsConfig;
import com.hollysmart.newsmodule.value.Value;
import com.hollysmart.newsmodule.view.indicator.ScaleTransitionPagerTitleView;
//import com.hollysmart.smart_sports.SearchActivity;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;


public class DongtaiFragment extends CaiBaseFragment{

    private NewsConfig config;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.news_module_fragment_dong_tai, container, false);
        findView(root);
        init();
        Utils.init(getActivity().getApplication());
        getLanmu();
        return root;
    }


    private MagicIndicator indicator;
    private ViewPager viewPager;
    private void findView(View root){
        root.findViewById(R.id.iv_right).setOnClickListener(onClickListener);
        indicator = root.findViewById(R.id.indicator);
        viewPager = root.findViewById(R.id.viewPage);

    }

    private List<LanmuBean> lanmuBeans;
    private void init() {
        EventBus.getDefault().register(this);
        Value.BASE_URL = config.getBASE_URL();
        Value.FILE_URL = config.getFILE_URL();
        Value.IS_LOGIN = config.isLogin();
        Value.USER_ID = config.getUserdId();
        Value.UUID = config.getUuid();

        this.lanmuBeans = config.getLanmuBeans();

    }

    @Override
    void lazyInit() {
        getLanmu();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (CaiUtils.isFastClick())
                return;
            //TODO
//            startActivity(new Intent(getContext(), SearchActivity.class));
        }
    };



    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getIndex(EB_DongTai dongTai){
        viewPager.setCurrentItem(dongTai.index);
    }

    private void getLanmu() {

//GB
//        lanmuBeans = new ArrayList<>();
//        lanmuBeans.add(new LanmuBean("34", "推荐"));
//        lanmuBeans.add(new LanmuBean("35", "体育资讯"));
//        lanmuBeans.add(new LanmuBean("100", "赛事活动"));
//        lanmuBeans.add(new LanmuBean("143", "协会"));
//        lanmuBeans.add(new LanmuBean("36", "健康知识"));




//        lanmuBeans.add(new LanmuBean("35", "体育资讯"));
//        lanmuBeans.add(new LanmuBean("35", "赛事活动"));
//        lanmuBeans.add(new LanmuBean("35", "社团"));
//        lanmuBeans.add(new LanmuBean("35", "健康知识"));

        BasePagerAdapter adapter = new BasePagerAdapter(getChildFragmentManager(), lanmuBeans);
        viewPager.setAdapter(adapter);
        initMagicIndicator();

    }

    private void initMagicIndicator() {
        indicator.setBackgroundColor(Color.WHITE);
        CommonNavigator commonNavigator = new CommonNavigator(getContext());
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return lanmuBeans.size();
            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public IPagerTitleView getTitleView(Context context, int index) {
                SimplePagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
                simplePagerTitleView.setText(lanmuBeans.get(index).getName());

                //GB
//                simplePagerTitleView.setTextSize(18);
//                simplePagerTitleView.setNormalColor(getContext().getColor(R.color.news_module_tab_normal));
//                simplePagerTitleView.setSelectedColor(getContext().getColor(R.color.news_module_tab_selected));
                simplePagerTitleView.setTextSize(config.getTabTitleTextSize());
                simplePagerTitleView.setNormalColor(config.getTitleNormalColor());
                simplePagerTitleView.setSelectedColor(config.getTitleSelectedColor());

                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);

                indicator.setLineHeight(UIUtil.dip2px(context, 3));
                indicator.setLineWidth(UIUtil.dip2px(context, 26));
                indicator.setRoundRadius(UIUtil.dip2px(context, 1));
                //GB
//                indicator.setLineHeight(UIUtil.dip2px(context, 3));
//                indicator.setLineWidth(UIUtil.dip2px(context, 26));
//                indicator.setRoundRadius(UIUtil.dip2px(context, 1));
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));

                indicator.setColors(config.getIndicatorColor());
                //GB
//                indicator.setColors(getContext().getColor(R.color.news_module_blue));
                return indicator;
            }
        });
        indicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(indicator, viewPager);
    }

    class BasePagerAdapter extends FragmentStatePagerAdapter {
        private List<LanmuBean> lanmuBeans;

        public BasePagerAdapter(FragmentManager fm, List<LanmuBean> lanmuBeans) {
            super(fm);
            this.lanmuBeans = lanmuBeans;
        }

        @Override
        public Fragment getItem(int position) {
            ListFragment mainListFragment = new ListFragment();
            Bundle bundle = new Bundle();
            bundle.putString("typeId", lanmuBeans.get(position).getId());
            mainListFragment.setArguments(bundle);
            return mainListFragment;
        }

        @Override
        public int getCount() {
            return lanmuBeans.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return lanmuBeans.get(position).getName();
        }
    }

    public NewsConfig getConfig() {
        return config;
    }

    public void setConfig(NewsConfig config) {
        this.config = config;
    }
}
