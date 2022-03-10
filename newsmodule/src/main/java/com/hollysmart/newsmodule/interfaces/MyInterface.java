package com.hollysmart.newsmodule.interfaces;


/**
 * Created by cai on 16/10/11
 */

public class MyInterface {

    /**
     * 只关心对接是否成功
     */
    public interface OnlyStateIF {
        void result(boolean isOk, String msg);
    }

    /**
     * 返回的是列表数据
     * @param <T>
     */
    public interface ListIF<T> {
        void listResult(boolean isOk, String msg, T list);
    }

    /**
     * 返回的是详情数据
     * @param <T>
     */
    public interface DetailIF<T> {
        void detailResult(boolean isOk, String msg, T data);
    }

    /**
     * 返回的String
     */
    public interface StringIF {
        void StringResult(String string);
    }


    public interface PopupIF {
        void onListener();
        void item(int position);
    }


}






















