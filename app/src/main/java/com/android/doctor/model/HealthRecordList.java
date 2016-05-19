package com.android.doctor.model;

import java.util.List;

/**
 * Created by Yong on 2016/5/16.
 */
public class HealthRecordList {

    private List<HealthrecordEntity> healthrecords;

    public List<HealthrecordEntity> getHealthrecords() {
        return healthrecords;
    }

    public void setHealthrecords(List<HealthrecordEntity> healthrecords) {
        this.healthrecords = healthrecords;
    }

    public static class HealthrecordEntity {
        private String _id;
        private int visitid;
        private String rid;
        private int pid;
        private String card;
        private int recordtype;
        private int recordsubtype;
        private int datastyle;
        private String begindate;
        private String enddate;
        private String prid;
        private String title;
        private String subtitle;
        private int hosid;
        private String hosname;
        private String doctor;
        private String deptname;
        private String content;
        private int createuid;
        private long createdatetime;
        private String source;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public int getVisitid() {
            return visitid;
        }

        public void setVisitid(int visitid) {
            this.visitid = visitid;
        }

        public String getRid() {
            return rid;
        }

        public void setRid(String rid) {
            this.rid = rid;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public String getCard() {
            return card;
        }

        public void setCard(String card) {
            this.card = card;
        }

        public int getRecordtype() {
            return recordtype;
        }

        public void setRecordtype(int recordtype) {
            this.recordtype = recordtype;
        }

        public int getRecordsubtype() {
            return recordsubtype;
        }

        public void setRecordsubtype(int recordsubtype) {
            this.recordsubtype = recordsubtype;
        }

        public int getDatastyle() {
            return datastyle;
        }

        public void setDatastyle(int datastyle) {
            this.datastyle = datastyle;
        }

        public String getBegindate() {
            return begindate;
        }

        public void setBegindate(String begindate) {
            this.begindate = begindate;
        }

        public String getEnddate() {
            return enddate;
        }

        public void setEnddate(String enddate) {
            this.enddate = enddate;
        }

        public String getPrid() {
            return prid;
        }

        public void setPrid(String prid) {
            this.prid = prid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public int getHosid() {
            return hosid;
        }

        public void setHosid(int hosid) {
            this.hosid = hosid;
        }

        public String getHosname() {
            return hosname;
        }

        public void setHosname(String hosname) {
            this.hosname = hosname;
        }

        public String getDoctor() {
            return doctor;
        }

        public void setDoctor(String doctor) {
            this.doctor = doctor;
        }

        public String getDeptname() {
            return deptname;
        }

        public void setDeptname(String deptname) {
            this.deptname = deptname;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getCreateuid() {
            return createuid;
        }

        public void setCreateuid(int createuid) {
            this.createuid = createuid;
        }

        public long getCreatedatetime() {
            return createdatetime;
        }

        public void setCreatedatetime(long createdatetime) {
            this.createdatetime = createdatetime;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }
    }
}
