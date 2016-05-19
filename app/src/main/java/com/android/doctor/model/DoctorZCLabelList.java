package com.android.doctor.model;

import java.util.List;

/**
 * Created by Yong on 2016/4/22.
 */
public class DoctorZCLabelList {


    private int total;
    /**
     * _id : 55a32a8049729c803af1cc03
     * code : 1001
     * name : 护士33
     * level : 1
     * imgurl :
     * pcode :
     * order :
     * D10004id : 1
     * requestid : 1111
     * response_params : {}
     * error_code : 0
     * error_msg : add dict item ok
     */

    private List<ZCLabelEntity> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ZCLabelEntity> getData() {
        return data;
    }

    public void setData(List<ZCLabelEntity> data) {
        this.data = data;
    }

    public static class ZCLabelEntity {
        private String _id;
        private String code;
        private String name;
        private String level;
        private String imgurl;
        private String pcode;
        private String order;
        private String D10004id;
        private String requestid;
        private String error_code;
        private String error_msg;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public String getPcode() {
            return pcode;
        }

        public void setPcode(String pcode) {
            this.pcode = pcode;
        }

        public String getOrder() {
            return order;
        }

        public void setOrder(String order) {
            this.order = order;
        }

        public String getD10004id() {
            return D10004id;
        }

        public void setD10004id(String D10004id) {
            this.D10004id = D10004id;
        }

        public String getRequestid() {
            return requestid;
        }

        public void setRequestid(String requestid) {
            this.requestid = requestid;
        }

        public String getError_code() {
            return error_code;
        }

        public void setError_code(String error_code) {
            this.error_code = error_code;
        }

        public String getError_msg() {
            return error_msg;
        }

        public void setError_msg(String error_msg) {
            this.error_msg = error_msg;
        }
    }
}
