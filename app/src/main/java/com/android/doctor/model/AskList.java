package com.android.doctor.model;

import java.util.List;

/**
 * Created by Yong on 2016/5/19.
 */
public class AskList {


    private int total;
    /**
     * _id : 57397537380994427e2ed8a5
     * uid : 2527961
     * askdoctorid : 124
     * askdoctorname : 郭医生
     * hosid : 7
     * pid : 2527961
     * nickname : 李燕
     * source : web
     * askclass : 乙肝
     * title : 希望好心医生帮我看看我到底什么情况？跪谢了！
     * content : 肝脏有疼痛感，身体体质也不如以前了，而且好像有睡不够的感觉。
     * pubtime : 2016-05-16 15:22:31
     * replies : 9
     * doctorreplies : 8
     * patientreplies : 1
     * lastreplytime : 2016-05-19 12:30:02
     * lastreplyrole : 0
     * lastreplynickname : 郭小青
     * satisfied : 0
     * attachments : []
     * status : 0
     * asktype : 1
     * focus : 0
     * askid : 2
     * relationship : 出院
     */

    private List<AsksEntity> asks;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<AsksEntity> getAsks() {
        return asks;
    }

    public void setAsks(List<AsksEntity> asks) {
        this.asks = asks;
    }

    public static class AsksEntity {
        private String _id;
        private int uid;
        private int askdoctorid;
        private String askdoctorname;
        private int hosid;
        private int pid;
        private String nickname;
        private String source;
        private String askclass;
        private String title;
        private String content;
        private String pubtime;
        private int replies;
        private int doctorreplies;
        private int patientreplies;
        private String lastreplytime;
        private int lastreplyrole;
        private String lastreplynickname;
        private int satisfied;
        private int status;
        private int asktype;
        private int focus;
        private int askid;
        private String relationship;
        private List<AttachmentsEntity> attachments;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getAskdoctorid() {
            return askdoctorid;
        }

        public void setAskdoctorid(int askdoctorid) {
            this.askdoctorid = askdoctorid;
        }

        public String getAskdoctorname() {
            return askdoctorname;
        }

        public void setAskdoctorname(String askdoctorname) {
            this.askdoctorname = askdoctorname;
        }

        public int getHosid() {
            return hosid;
        }

        public void setHosid(int hosid) {
            this.hosid = hosid;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getAskclass() {
            return askclass;
        }

        public void setAskclass(String askclass) {
            this.askclass = askclass;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPubtime() {
            return pubtime;
        }

        public void setPubtime(String pubtime) {
            this.pubtime = pubtime;
        }

        public int getReplies() {
            return replies;
        }

        public void setReplies(int replies) {
            this.replies = replies;
        }

        public int getDoctorreplies() {
            return doctorreplies;
        }

        public void setDoctorreplies(int doctorreplies) {
            this.doctorreplies = doctorreplies;
        }

        public int getPatientreplies() {
            return patientreplies;
        }

        public void setPatientreplies(int patientreplies) {
            this.patientreplies = patientreplies;
        }

        public String getLastreplytime() {
            return lastreplytime;
        }

        public void setLastreplytime(String lastreplytime) {
            this.lastreplytime = lastreplytime;
        }

        public int getLastreplyrole() {
            return lastreplyrole;
        }

        public void setLastreplyrole(int lastreplyrole) {
            this.lastreplyrole = lastreplyrole;
        }

        public String getLastreplynickname() {
            return lastreplynickname;
        }

        public void setLastreplynickname(String lastreplynickname) {
            this.lastreplynickname = lastreplynickname;
        }

        public int getSatisfied() {
            return satisfied;
        }

        public void setSatisfied(int satisfied) {
            this.satisfied = satisfied;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getAsktype() {
            return asktype;
        }

        public void setAsktype(int asktype) {
            this.asktype = asktype;
        }

        public int getFocus() {
            return focus;
        }

        public void setFocus(int focus) {
            this.focus = focus;
        }

        public int getAskid() {
            return askid;
        }

        public void setAskid(int askid) {
            this.askid = askid;
        }

        public String getRelationship() {
            return relationship;
        }

        public void setRelationship(String relationship) {
            this.relationship = relationship;
        }

        public List<?> getAttachments() {
            return attachments;
        }

        public void setAttachments(List<AttachmentsEntity> attachments) {
            this.attachments = attachments;
        }

        public static class AttachmentsEntity {
            private String attachid;
            private String attachurl;
            private int width;
            private int height;

            public String getAttachid() {
                return attachid;
            }

            public void setAttachid(String attachid) {
                this.attachid = attachid;
            }

            public String getAttachurl() {
                return attachurl;
            }

            public void setAttachurl(String attachurl) {
                this.attachurl = attachurl;
            }

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }
        }
    }
}
