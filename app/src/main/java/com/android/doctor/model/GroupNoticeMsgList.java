package com.android.doctor.model;

import java.util.List;

/**
 * Created by Yong on 2016/4/23.
 */
public class GroupNoticeMsgList {

    private int total;
    /**
     * _id : 56fde02bcc2795fc78f84f5c
     * msgid : 07d3c3ab-3ee0-4c21-b850-69e1b7ff589a
     * msgtype : 500
     * submsgtype : 500015
     * msgtypename : 群组消息
     * submsgtypename : 用户同意邀请消息
     * msgcontent : 周巧云 同意了您邀请，加入群 我们都
     * msgparam : {"groupId":"gg80010536139"}
     * msgtime : 1459478571212
     * status : -1
     * read_status : 0
     * from : 2
     * fromutype : 0
     * to : 450015
     * toutype : 1
     * statustext : null
     * groupmsgid : 2-450015
     * lastmsgtime : 2016-04-01 10:42:51
     */

    private List<MsgEntity> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<MsgEntity> getData() {
        return data;
    }

    public void setData(List<MsgEntity> data) {
        this.data = data;
    }

    public static class MsgEntity {
        private String _id;
        private String msgid;
        private String msgtype;
        private String submsgtype;
        private String msgtypename;
        private String submsgtypename;
        private String msgcontent;
        /**
         * groupId : gg80010536139
         */

        private MsgparamEntity msgparam;
        private String msgtime;
        private String status;
        private String read_status;
        private String from;
        private String fromutype;
        private String to;
        private String toutype;
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

        public String getMsgtype() {
            return msgtype;
        }

        public void setMsgtype(String msgtype) {
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

        public String getRead_status() {
            return read_status;
        }

        public void setRead_status(String read_status) {
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

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public String getToutype() {
            return toutype;
        }

        public void setToutype(String toutype) {
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
            private String groupId;
            private String groupname;
            private String display;
            private String owneruid;
            private int ownertype;


            public String getGroupId() {
                return groupId;
            }

            public void setGroupId(String groupId) {
                this.groupId = groupId;
            }

            public String getGroupname() {
                return groupname;
            }

            public void setGroupname(String groupname) {
                this.groupname = groupname;
            }

            public String getDisplay() {
                return display;
            }

            public void setDisplay(String display) {
                this.display = display;
            }

            public String getOwneruid() {
                return owneruid;
            }

            public void setOwneruid(String owneruid) {
                this.owneruid = owneruid;
            }

            public int getOwnertype() {
                return ownertype;
            }

            public void setOwnertype(int ownertype) {
                this.ownertype = ownertype;
            }
        }
    }
}
