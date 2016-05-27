package com.android.doctor.model;

import java.util.List;

/**
 * Created by Yong on 2016/5/19.
 */
public class AskReplyList {

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
     * lastreplytime : 1463632202596
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

    private AskList.AsksEntity ask;
    /**
     * _id : 5739770c380994427e2ed8aa
     * askid : 2
     * replyuid : 124
     * content : 建议到你们当地传染病专科医院就诊，可以考虑进行抗病毒治疗。
     * replytime : 2016-05-16 15:30:20
     * replynickname : 郭医生
     * replyrole : 0
     * source : web
     * attachments : []
     * replyid : 2
     */

    private List<RepliesEntity> replies;

    public AskList.AsksEntity getAsk() {
        return ask;
    }

    public void setAsk(AskList.AsksEntity ask) {
        this.ask = ask;
    }

    public List<RepliesEntity> getReplies() {
        return replies;
    }

    public void setReplies(List<RepliesEntity> replies) {
        this.replies = replies;
    }


    public static class RepliesEntity {
        private String _id;
        private int askid;
        private int replyuid;
        private String content;
        private String replytime;
        private String replynickname;
        private int replyrole;
        private String source;
        private int replyid;
        private List<?> attachments;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public int getAskid() {
            return askid;
        }

        public void setAskid(int askid) {
            this.askid = askid;
        }

        public int getReplyuid() {
            return replyuid;
        }

        public void setReplyuid(int replyuid) {
            this.replyuid = replyuid;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getReplytime() {
            return replytime;
        }

        public void setReplytime(String replytime) {
            this.replytime = replytime;
        }

        public String getReplynickname() {
            return replynickname;
        }

        public void setReplynickname(String replynickname) {
            this.replynickname = replynickname;
        }

        public int getReplyrole() {
            return replyrole;
        }

        public void setReplyrole(int replyrole) {
            this.replyrole = replyrole;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public int getReplyid() {
            return replyid;
        }

        public void setReplyid(int replyid) {
            this.replyid = replyid;
        }

        public List<?> getAttachments() {
            return attachments;
        }

        public void setAttachments(List<?> attachments) {
            this.attachments = attachments;
        }
    }
}
