package com.android.doctor.model;

import java.util.List;

/**
 * Created by Yong on 2016/4/17.
 */
public class MedicClassify {

    /**
     * _id : 5699ff0a7dd8ce077410b26c
     * code : 1
     * name : 抗病毒
     */

    private List<MedicEntity> list;

    public List<MedicEntity> getList() {
        return list;
    }

    public void setList(List<MedicEntity> list) {
        this.list = list;
    }

    public static class MedicEntity {
        private String _id;
        private int code;
        private String name;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
