package com.android.doctor.model;

/**
 * Created by Yong on 2016/4/11.
 */
public class RespEntity<T> {
    private String requestid;
    private T response_params;
    private int error_code;
    private String error_msg;

    public String getRequestid() {
        return requestid;
    }

    public void setRequestid(String requestid) {
        this.requestid = requestid;
    }

    public T getResponse_params() {
        return response_params;
    }

    public void setResponse_params(T response_params) {
        this.response_params = response_params;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }
}
