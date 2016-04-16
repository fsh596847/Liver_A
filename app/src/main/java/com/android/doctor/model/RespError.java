package com.android.doctor.model;

/**
 * Created by Yong on 2016/4/9.
 */
public class RespError {


    /**
     * requestid :
     * response_params :
     * error_code : 0
     * error_msg :
     */

    private int requestid;
    private int error_code;
    private String error_msg;

    public int getRequestid() {
        return requestid;
    }

    public void setRequestid(int requestid) {
        this.requestid = requestid;
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
