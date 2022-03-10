package com.hollysmart.newsmodule.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import com.hollysmart.newsmodule.api.callback.JsonCallback;
import com.hollysmart.newsmodule.api.modle.CaiBaseData;
import com.hollysmart.newsmodule.api.modle.CaiResponse;
import com.hollysmart.newsmodule.api.taskpool.INetModel;
import com.hollysmart.newsmodule.bean.ContentBean;
import com.hollysmart.newsmodule.bean.DataBean;
import com.hollysmart.newsmodule.interfaces.MyInterface;

import com.hollysmart.newsmodule.value.Value;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

public class GetListApi implements INetModel {

    private Object tag;
    private JsonObject args;
    private MyInterface.DetailIF<CaiBaseData<ContentBean>> detailIF;

    public GetListApi(Object tag, JsonObject args, MyInterface.DetailIF<CaiBaseData<ContentBean>> detailIF) {
        this.tag = tag;
        this.args = args;
        this.detailIF = detailIF;
    }

    @Override
    public void request() {
        String url = Value.BASE_URL + "/api-search/api/anno/search/category/ids";
        OkGo.<CaiResponse<CaiBaseData<ContentBean>>>post(url)
                .tag(tag)
                .headers("Content-Type", "application/json")
                .upJson(new Gson().toJson(args))
                .execute(new JsonCallback<CaiResponse<CaiBaseData<ContentBean>>>() {
                    @Override
                    public void onSuccess(Response<CaiResponse<CaiBaseData<ContentBean>>> response) {
                        if (response.code() == 200 && response.body() != null && response.body().code == 200) {
                            dealData(response.body().data);
                            detailIF.detailResult(true, response.body().msg, response.body().data);
                        } else {
                            detailIF.detailResult(false, "接口报错", null);
                        }
                    }

                    @Override
                    public void onError(Response<CaiResponse<CaiBaseData<ContentBean>>> response) {
                        super.onError(response);
                        detailIF.detailResult(false, response.message(), null);
                    }
                });
    }

    private void dealData(CaiBaseData<ContentBean> data) {
        if (data.list != null) {
            Gson mGson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();
            for (ContentBean contentBean : data.list) {
                if (contentBean.getDataBean() == null){
                    DataBean dataBean = mGson.fromJson(contentBean.getData(),
                            new TypeToken<DataBean>() {
                            }.getType());
                    contentBean.setDataBean(dataBean);
                }
            }
        }

    }
}







