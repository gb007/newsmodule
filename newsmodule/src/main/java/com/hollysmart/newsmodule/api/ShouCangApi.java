package com.hollysmart.newsmodule.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import com.hollysmart.newsmodule.api.callback.JsonCallback;
import com.hollysmart.newsmodule.api.modle.CaiResponse;
import com.hollysmart.newsmodule.api.taskpool.INetModel;
import com.hollysmart.newsmodule.bean.ShouCangBean;
import com.hollysmart.newsmodule.interfaces.MyInterface;
import com.hollysmart.newsmodule.value.Value;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

public class ShouCangApi implements INetModel {

    private Object tag;
    private JsonObject args;
    private MyInterface.DetailIF<ShouCangBean> detailIF;

    public ShouCangApi(Object tag, JsonObject args, MyInterface.DetailIF<ShouCangBean> detailIF) {
        this.tag = tag;
        this.args = args;
        this.detailIF = detailIF;
    }

    @Override
    public void request() {
        String url = Value.BASE_URL + "/api-search/api/content/store/user";
        OkGo.<CaiResponse<ShouCangBean>>post(url)
                .tag(tag)
                .headers("Content-Type", "application/json")
                .upJson(new Gson().toJson(args))
                .execute(new JsonCallback<CaiResponse<ShouCangBean>>() {
                    @Override
                    public void onSuccess(Response<CaiResponse<ShouCangBean>> response) {
                        if (response.code() == 200 && response.body().code == 200) {
                            detailIF.detailResult(true, response.body().msg, response.body().data);
                        } else {
                            detailIF.detailResult(false, response.body().msg, null);
                        }
                    }

                    @Override
                    public void onError(Response<CaiResponse<ShouCangBean>> response) {
                        super.onError(response);
                        detailIF.detailResult(false, response.message(), null);
                    }
                });
    }
}







