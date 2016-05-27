package com.android.doctor.model;

import java.util.List;

/**
 * Created by Yong on 2016/5/24.
 */
public class PatientReportMsgList {


    private int total;

    private List<ReportMsgEntity> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ReportMsgEntity> getData() {
        return data;
    }

    public void setData(List<ReportMsgEntity> data) {
        this.data = data;
    }

    public static class ReportMsgEntity {
        private String _id;
        private String msgid;
        private int msgtype;
        private int submsgtype;
        private String msgtypename;
        private String submsgtypename;
        private String msgcontent;
        /**
         * uid : 2533568
         * puid : 2533568
         * pname : 患2533568
         * duid : 124
         * dname : 医生124
         * inouttype : 其它
         * visitdate : 1464048000000
         * visitcard :
         * inpatientoroutpatientcode :
         * process : 0
         * province :
         * city :
         * region :
         * provincename :
         * cityname :
         * regionname :
         * reportingdate : 1464059933264
         * patientdoctorid : 1
         */

        private MsgparamEntity msgparam;
        private String msgtime;
        private String status;
        private int read_status;
        private String from;
        private String fromutype;
        private int to;
        private int toutype;
        private String statustext;
        private String groupmsgid;
        private String lastmsgtime;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getMsgid() {
            return msgid;
        }

        public void setMsgid(String msgid) {
            this.msgid = msgid;
        }

        public int getMsgtype() {
            return msgtype;
        }

        public void setMsgtype(int msgtype) {
            this.msgtype = msgtype;
        }

        public int getSubmsgtype() {
            return submsgtype;
        }

        public void setSubmsgtype(int submsgtype) {
            this.submsgtype = submsgtype;
        }

        public String getMsgtypename() {
            return msgtypename;
        }

        public void setMsgtypename(String msgtypename) {
            this.msgtypename = msgtypename;
        }

        public String getSubmsgtypename() {
            return submsgtypename;
        }

        public void setSubmsgtypename(String submsgtypename) {
            this.submsgtypename = submsgtypename;
        }

        public String getMsgcontent() {
            return msgcontent;
        }

        public void setMsgcontent(String msgcontent) {
            this.msgcontent = msgcontent;
        }

        public MsgparamEntity getMsgparam() {
            return msgparam;
        }

        public void setMsgparam(MsgparamEntity msgparam) {
            this.msgparam = msgparam;
        }

        public String getMsgtime() {
            return msgtime;
        }

        public void setMsgtime(String msgtime) {
            this.msgtime = msgtime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getRead_status() {
            return read_status;
        }

        public void setRead_status(int read_status) {
            this.read_status = read_status;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getFromutype() {
            return fromutype;
        }

        public void setFromutype(String fromutype) {
            this.fromutype = fromutype;
        }

        public int getTo() {
            return to;
        }

        public void setTo(int to) {
            this.to = to;
        }

        public int getToutype() {
            return toutype;
        }

        public void setToutype(int toutype) {
            this.toutype = toutype;
        }

        public Object getStatustext() {
            return statustext;
        }

        public void setStatustext(String statustext) {
            this.statustext = statustext;
        }

        public String getGroupmsgid() {
            return groupmsgid;
        }

        public void setGroupmsgid(String groupmsgid) {
            this.groupmsgid = groupmsgid;
        }

        public String getLastmsgtime() {
            return lastmsgtime;
        }

        public void setLastmsgtime(String lastmsgtime) {
            this.lastmsgtime = lastmsgtime;
        }

        public static class MsgparamEntity {
            private int uid;
            private int puid;
            private String pname;
            private int duid;
            private String dname;
            private String inouttype;
            private String visitdate;
            private String visitcard;
            private String inpatientoroutpatientcode;
            private int process;
            private String province;
            private String city;
            private String region;
            private String provincename;
            private String cityname;
            private String regionname;
            private String reportingdate;
            private int patientdoctorid;

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public int getPuid() {
                return puid;
            }

            public void setPuid(int puid) {
                this.puid = puid;
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

            public String getInouttype() {
                return inouttype;
            }

            public void setInouttype(String inouttype) {
                this.inouttype = inouttype;
            }

            public String getVisitdate() {
                return visitdate;
            }

            public void setVisitdate(String visitdate) {
                this.visitdate = visitdate;
            }

            public String getVisitcard() {
                return visitcard;
            }

            public void setVisitcard(String visitcard) {
                this.visitcard = visitcard;
            }

            public String getInpatientoroutpatientcode() {
                return inpatientoroutpatientcode;
            }

            public void setInpatientoroutpatientcode(String inpatientoroutpatientcode) {
                this.inpatientoroutpatientcode = inpatientoroutpatientcode;
            }

            public int getProcess() {
                return process;
            }

            public void setProcess(int process) {
                this.process = process;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getRegion() {
                return region;
            }

            public void setRegion(String region) {
                this.region = region;
            }

            public String getProvincename() {
                return provincename;
            }

            public void setProvincename(String provincename) {
                this.provincename = provincename;
            }

            public String getCityname() {
                return cityname;
            }

            public void setCityname(String cityname) {
                this.cityname = cityname;
            }

            public String getRegionname() {
                return regionname;
            }

            public void setRegionname(String regionname) {
                this.regionname = regionname;
            }

            public String getReportingdate() {
                return reportingdate;
            }

            public void setReportingdate(String reportingdate) {
                this.reportingdate = reportingdate;
            }

            public int getPatientdoctorid() {
                return patientdoctorid;
            }

            public void setPatientdoctorid(int patientdoctorid) {
                this.patientdoctorid = patientdoctorid;
            }
        }
    }
}
