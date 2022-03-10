package com.hollysmart.newsmodule.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import com.hollysmart.newsmodule.R;
import com.hollysmart.newsmodule.adapter.ContentAdapter;
import com.hollysmart.newsmodule.api.GetListApi;
import com.hollysmart.newsmodule.api.modle.CaiBaseData;
import com.hollysmart.newsmodule.bean.ContentBean;
import com.hollysmart.newsmodule.interfaces.MyInterface;
import com.hollysmart.newsmodule.utils.CaiUtils;

import com.hollysmart.newsmodule.utils.Mlog;

import com.hollysmart.newsmodule.value.Value;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends CaiBaseFragment implements View.OnClickListener, OnRefreshListener, OnLoadMoreListener {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.news_module_fragment_onle_list, container, false);
        findView(root);
        init();
        return root;
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private LinearLayout ll_nodata;
    private TextView tv_tisi;

    private SmartRefreshLayout refreshLayout;
    private RecyclerView rv_data;
    private LinearLayoutManager llm;


    private void findView(View root) {

        ll_nodata = root.findViewById(R.id.ll_nodata);
        tv_tisi = root.findViewById(R.id.tv_tisi);

        refreshLayout = root.findViewById(R.id.smart_refresh);
        rv_data = root.findViewById(R.id.rv_data);
        llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv_data.setLayoutManager(llm);

        //添加刷新监听
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setEnableLoadMore(true);
        refreshLayout.setEnableHeaderTranslationContent(true);//是否下拉Header的时候向下平移列表或者内容
        refreshLayout.setEnableFooterTranslationContent(true);//是否上拉Footer的时候向上平移列表或者内容
        refreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));
        refreshLayout.setOnLoadMoreListener(this);
        refreshLayout.setOnRefreshListener(this);
    }

    private int page = 1;
    private boolean isRefresh;
    private boolean isMore;

    private List<ContentBean> contentBeanList;
    private ContentAdapter contentAdapter;
    private String typeId;

    private void init() {
        typeId = getArguments().getString("typeId");
        contentBeanList = new ArrayList<>();
        contentAdapter = new ContentAdapter( contentBeanList,true);
        rv_data.setAdapter(contentAdapter);
        getData();
    }


    @Override
    void lazyInit() {

    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onRefresh(final RefreshLayout refreshLayout) {
        isRefresh = true;
        page = 1;
        Mlog.d("刷新了页面");
        contentBeanList.clear();
        getData();
    }

    @Override
    public void onLoadMore(final RefreshLayout refreshLayout) {
        isMore = true;
        page++;
        getData();
    }

    private void getData() {
        JsonObject args = new JsonObject();
        args.addProperty("currentPage", page);
        args.addProperty("pageSize", Value.PAGE_SIZE);

        JsonArray categoryIds = new JsonArray();
        categoryIds.add(typeId);
        args.add("categoryIds", categoryIds);

        JsonArray sorts = new JsonArray();
        JsonObject sort1 = new JsonObject();

        JsonObject sort2 = new JsonObject();
        sort2.addProperty("sortName", "sort");
        sort2.addProperty("sortType", "desc");
        sorts.add(sort2);


        sort1.addProperty("sortName", "publishDate");
        sort1.addProperty("sortType", "desc");
        sorts.add(sort1);


        args.add("searchSorts", sorts);
        new GetListApi(this, args, detailIF).request();
    }

    MyInterface.DetailIF<CaiBaseData<ContentBean>> detailIF = new MyInterface.DetailIF<CaiBaseData<ContentBean>>() {
        @Override
        public void detailResult(boolean isOk, String msg, CaiBaseData<ContentBean> data) {
            if (isOk) {
                if (isRefresh) {
                    contentBeanList.clear();
                }
                if (contentBeanList.size() > 0 || data.list.size() > 0) {
                    ll_nodata.setVisibility(View.GONE);
                } else {
                    ll_nodata.setVisibility(View.VISIBLE);
                    tv_tisi.setText("暂无数据");
                }
                if (page + 1 > data.totalPage) {
                    refreshLayout.setEnableLoadMore(false);
                    refreshLayout.finishLoadMore();
                    refreshLayout.finishLoadMoreWithNoMoreData();
                } else {
                    refreshLayout.setEnableLoadMore(true);
                    refreshLayout.setNoMoreData(false);//恢复没有更多数据的原始状态 1.0.5
                }
                contentBeanList.addAll(data.list);
                contentAdapter.notifyDataSetChanged();
            }else {
                CaiUtils.showToast(getContext(), msg);
            }
            if (isRefresh) {
                refreshLayout.finishRefresh();
                isRefresh = false;
            }
            if (isMore) {
                refreshLayout.finishLoadMore();
                isMore = false;
            }
        }
    };

}






















