package com.android.doctor.model;

import java.util.List;

/**
 * Created by Yong on 2016/4/18.
 */
public class DiagList {

    private List<DiagEntity> list;

    public List<DiagEntity> getList() {
        return list;
    }

    public void setList(List<DiagEntity> list) {
        this.list = list;
    }

    public static class DiagEntity {
        private String _id;
        private String name;
        private String spell;
        private String icd10;
        private int hosid;
        private String dept;
        private String keyword;
        private int orderno;

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

        public String getSpell() {
            return spell;
        }

        public void setSpell(String spell) {
            this.spell = spell;
        }

        public String getIcd10() {
            return icd10;
        }

        public void setIcd10(String icd10) {
            this.icd10 = icd10;
        }

        public int getHosid() {
            return hosid;
        }

        public void setHosid(int hosid) {
            this.hosid = hosid;
        }

        public String getDept() {
            return dept;
        }

        public void setDept(String dept) {
            this.dept = dept;
        }

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public int getOrderno() {
            return orderno;
        }

        public void setOrderno(int orderno) {
            this.orderno = orderno;
        }
    }
}
