package com.android.doctor.model;

import java.util.List;

/**
 * Created by Yong on 2016/4/28.
 */
public class DiseaseClass {

    /**
     * _id : 55813c0d5332f033513d667c
     * name : 乙肝
     */

    private List<DisClassEntity> data;

    public List<DisClassEntity> getData() {
        return data;
    }

    public void setData(List<DisClassEntity> data) {
        this.data = data;
    }

    public static class DisClassEntity {
        private String _id;
        private String name;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
