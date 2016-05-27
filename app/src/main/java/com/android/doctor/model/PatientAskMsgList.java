package com.android.doctor.model;

import java.util.List;

/**
 * Created by Yong on 2016/5/24.
 */
public class PatientAskMsgList {

    private int total;

    private List<PAskMsgEntity> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<PAskMsgEntity> getData() {
        return data;
    }

    public void setData(List<PAskMsgEntity> data) {
        this.data = data;
    }

    public static class PAskMsgEntity {
        private String _id;
        private String msgid;
        private int msgtype;
        private int submsgtype;
        private String msgtypename;
        private String submsgtypename;
        private String msgcontent;
        /**
         * askid : 1
         * title : 我测试的
         */

        private MsgparamEntity msgparam;
        private String msgtime;
        private String status;
        private int read_status;
        private int from;
        private int fromutype;
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

        public String getStatustext() {
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
            private String askid;
            private String title;

            public String getAskid() {
                return askid;
            }

            public void setAskid(String askid) {
                this.askid = askid;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}
