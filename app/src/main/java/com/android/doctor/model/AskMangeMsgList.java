package com.android.doctor.model;

import java.util.List;

/**
 * Created by Yong on 2016/5/24.
 */
public class AskMangeMsgList {

    private int total;

    private List<AskManageMsgEntity> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<AskManageMsgEntity> getData() {
        return data;
    }

    public void setData(List<AskManageMsgEntity> data) {
        this.data = data;
    }

    public static class AskManageMsgEntity {
        private String _id;
        private String msgid;
        private int msgtype;
        private String submsgtype;
        private String msgtypename;
        private String submsgtypename;
        private String msgcontent;
        /**
         * _id : 573eb7bbb2b0c245058111d7
         * planname : 患2529975[肝病]随访计划2016-05-20
         * puid : 2529975
         * puuid : 2529975
         * pname : 患2529975
         * duid : 124
         * dname : 医生124
         * card : 140103196206281526
         * disease : 肝病
         * maindiagnose : 肝硬化
         * otherdiagnose :
         * treatmentmodality : 药物治疗
         * status : 2
         * sendstatus : 6
         * followtype : 随访计划
         * tplid : 8e6d1c10-8703-4bb2-91fc-2d4245a1c591
         * ref_tplid : 52fcb0d6-51ce-4e7f-8ebd-d4aba01338f0
         * isaccept : 0
         * time : 1463673600000
         * months : [null,201605]
         * lastmonth : 201605
         * lastpubtime : null
         * lastruntime : 1463796810350
         * updlasttime : null
         * type : zy
         * hosid : 7
         * createTime : 1463728059247
         * pid : 6
         * uid : 2529975
         */

        private PlanList.PlanBaseEntity msgparam;
        private String msgtime;
        private String status;
        private int read_status;
        private int from;
        private int fromutype;
        private int to;
        private int toutype;
        private int statustext;
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

        public String getSubmsgtype() {
            return submsgtype;
        }

        public void setSubmsgtype(String submsgtype) {
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

        public PlanList.PlanBaseEntity getMsgparam() {
            return msgparam;
        }

        public void setMsgparam(PlanList.PlanBaseEntity msgparam) {
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

        public int getFrom() {
            return from;
        }

        public void setFrom(int from) {
            this.from = from;
        }

        public int getFromutype() {
            return fromutype;
        }

        public void setFromutype(int fromutype) {
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

        public int getStatustext() {
            return statustext;
        }

        public void setStatustext(int statustext) {
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
            private String _id;
            private String planname;
            private int puid;
            private String puuid;
            private String pname;
            private int duid;
            private String dname;
            private String card;
            private String disease;
            private String maindiagnose;
            private String otherdiagnose;
            private String treatmentmodality;
            private int status;
            private int sendstatus;
            private String followtype;
            private String tplid;
            private String ref_tplid;
            private int isaccept;
            private long time;
            private int lastmonth;
            private Object lastpubtime;
            private long lastruntime;
            private Object updlasttime;
            private String type;
            private String hosid;
            private long createTime;
            private int pid;
            private int uid;
            private List<?> months;

            public String get_id() {
                return _id;
            }

            public void set_id(String _id) {
                this._id = _id;
            }

            public String getPlanname() {
                return planname;
            }

            public void setPlanname(String planname) {
                this.planname = planname;
            }

            public int getPuid() {
                return puid;
            }

            public void setPuid(int puid) {
                this.puid = puid;
            }

            public String getPuuid() {
                return puuid;
            }

            public void setPuuid(String puuid) {
                this.puuid = puuid;
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

            public String getCard() {
                return card;
            }

            public void setCard(String card) {
                this.card = card;
            }

            public String getDisease() {
                return disease;
            }

            public void setDisease(String disease) {
                this.disease = disease;
            }

            public String getMaindiagnose() {
                return maindiagnose;
            }

            public void setMaindiagnose(String maindiagnose) {
                this.maindiagnose = maindiagnose;
            }

            public String getOtherdiagnose() {
                return otherdiagnose;
            }

            public void setOtherdiagnose(String otherdiagnose) {
                this.otherdiagnose = otherdiagnose;
            }

            public String getTreatmentmodality() {
                return treatmentmodality;
            }

            public void setTreatmentmodality(String treatmentmodality) {
                this.treatmentmodality = treatmentmodality;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getSendstatus() {
                return sendstatus;
            }

            public void setSendstatus(int sendstatus) {
                this.sendstatus = sendstatus;
            }

            public String getFollowtype() {
                return followtype;
            }

            public void setFollowtype(String followtype) {
                this.followtype = followtype;
            }

            public String getTplid() {
                return tplid;
            }

            public void setTplid(String tplid) {
                this.tplid = tplid;
            }

            public String getRef_tplid() {
                return ref_tplid;
            }

            public void setRef_tplid(String ref_tplid) {
                this.ref_tplid = ref_tplid;
            }

            public int getIsaccept() {
                return isaccept;
            }

            public void setIsaccept(int isaccept) {
                this.isaccept = isaccept;
            }

            public long getTime() {
                return time;
            }

            public void setTime(long time) {
                this.time = time;
            }

            public int getLastmonth() {
                return lastmonth;
            }

            public void setLastmonth(int lastmonth) {
                this.lastmonth = lastmonth;
            }

            public Object getLastpubtime() {
                return lastpubtime;
            }

            public void setLastpubtime(Object lastpubtime) {
                this.lastpubtime = lastpubtime;
            }

            public long getLastruntime() {
                return lastruntime;
            }

            public void setLastruntime(long lastruntime) {
                this.lastruntime = lastruntime;
            }

            public Object getUpdlasttime() {
                return updlasttime;
            }

            public void setUpdlasttime(Object updlasttime) {
                this.updlasttime = updlasttime;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getHosid() {
                return hosid;
            }

            public void setHosid(String hosid) {
                this.hosid = hosid;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public int getPid() {
                return pid;
            }

            public void setPid(int pid) {
                this.pid = pid;
            }

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public List<?> getMonths() {
                return months;
            }

            public void setMonths(List<?> months) {
                this.months = months;
            }
        }
    }
}
