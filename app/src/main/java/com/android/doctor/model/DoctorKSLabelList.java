package com.android.doctor.model;

import java.util.List;

/**
 * Created by Yong on 2016/4/22.
 */
public class DoctorKSLabelList {


    private int total;
    /**
     * _id : 564c59f15fecb7c83225081c
     * H10003id : 341
     * deptid : 341
     * code : 341
     * name : 应急办
     * pcode : 341
     * pycode : yjb
     * level :
     * order :
     * base : {"introduction":""}
     * imgurl :
     * hosid : 7
     * introduce : 应急办
     */

    private List<KSLabelEntity> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<KSLabelEntity> getData() {
        return data;
    }

    public void setData(List<KSLabelEntity> data) {
        this.data = data;
    }

    public static class KSLabelEntity {
        private String _id;
        private String H10003id;
        private String deptid;
        private String code;
        private String name;
        private String pcode;
        private String pycode;
        private String level;
        private String order;
        /**
         * introduction :
         */

        private BaseEntity base;
        private String imgurl;
        private int hosid;
        private String introduce;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getH10003id() {
            return H10003id;
        }

        public void setH10003id(String H10003id) {
            this.H10003id = H10003id;
        }

        public String getDeptid() {
            return deptid;
        }

        public void setDeptid(String deptid) {
            this.deptid = deptid;
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

        public String getPcode() {
            return pcode;
        }

        public void setPcode(String pcode) {
            this.pcode = pcode;
        }

        public String getPycode() {
            return pycode;
        }

        public void setPycode(String pycode) {
            this.pycode = pycode;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getOrder() {
            return order;
        }

        public void setOrder(String order) {
            this.order = order;
        }

        public BaseEntity getBase() {
            return base;
        }

        public void setBase(BaseEntity base) {
            this.base = base;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public int getHosid() {
            return hosid;
        }

        public void setHosid(int hosid) {
            this.hosid = hosid;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public static class BaseEntity {
            private String introduction;

            public String getIntroduction() {
                return introduction;
            }

            public void setIntroduction(String introduction) {
                this.introduction = introduction;
            }
        }
    }
}
