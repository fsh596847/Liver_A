package com.android.doctor.model;

import java.util.List;

/**
 * Created by Yong on 2016/4/17.
 */
public class PlanRecordList {

    private List<RecordEntity> data;

    public List<RecordEntity> getData() {
        return data;
    }

    public void setData(List<RecordEntity> data) {
        this.data = data;
    }

    public static class RecordEntity {
        private String _id;
        private int pid;
        private String planname;
        private String pname;
        private int duid;
        private String dname;
        private int puid;
        private String contnent;
        private long createtime;
        private int recordtype;
        private List<String> changecontnent;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public String getPlanname() {
            return planname;
        }

        public void setPlanname(String planname) {
            this.planname = planname;
        }

        public String getPname() {
            return pname;
        }

        public void setPname(String pname) {
            this.pname = pname;
        }

        public int getDuid() {
            return duid;
        }

        public void setDuid(int duid) {
            this.duid = duid;
        }

        public String getDname() {
            return dname;
        }

        public void setDname(String dname) {
            this.dname = dname;
        }

        public int getPuid() {
            return puid;
        }

        public void setPuid(int puid) {
            this.puid = puid;
        }

        public String getContnent() {
            return contnent;
        }

        public void setContnent(String contnent) {
            this.contnent = contnent;
        }

        public long getCreatetime() {
            return createtime;
        }

        public void setCreatetime(long createtime) {
            this.createtime = createtime;
        }

        public int getRecordtype() {
            return recordtype;
        }

        public void setRecordtype(int recordtype) {
            this.recordtype = recordtype;
        }

        public List<String> getChangecontnent() {
            return changecontnent;
        }

        public void setChangecontnent(List<String> changecontnent) {
            this.changecontnent = changecontnent;
        }
    }
}
