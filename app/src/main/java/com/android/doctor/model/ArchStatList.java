package com.android.doctor.model;

import java.util.List;

/**
 * Created by Yong on 2016/5/16.
 */
public class ArchStatList {

    /**
     * name : 血红蛋白
     * eng : HGB
     * value : 69
     * unit : g/L
     * range : 130--175
     * begindate : 2015-11-17
     */

    private List<ItemlistEntity> itemlist;

    public List<ItemlistEntity> getItemlist() {
        return itemlist;
    }

    public void setItemlist(List<ItemlistEntity> itemlist) {
        this.itemlist = itemlist;
    }

    public static class ItemlistEntity {
        private String name;
        private String eng;
        private String value;
        private String unit;
        private String range;
        private String begindate;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEng() {
            return eng;
        }

        public void setEng(String eng) {
            this.eng = eng;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getRange() {
            return range;
        }

        public void setRange(String range) {
            this.range = range;
        }

        public String getBegindate() {
            return begindate;
        }

        public void setBegindate(String begindate) {
            this.begindate = begindate;
        }
    }
}
