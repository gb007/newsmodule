
package com.hollysmart.newsmodule.api.modle;

import java.io.Serializable;

/**
 * cai
 */
public class CaiResponse<T> implements Serializable {

    private static final long serialVersionUID = 5213230387175987834L;
    public int total;
    public int code;
    public String msg;
    public T data;

    public int status;

    @Override
    public String toString() {
        return "CaiResponse{" +
                "total=" + total +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
