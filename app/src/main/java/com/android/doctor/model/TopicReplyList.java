package com.android.doctor.model;

import java.util.List;

/**
 * Created by Yong on 2016/5/8.
 */
public class TopicReplyList {

    private int total;

    private List<TopicRepliesEntity> topicreplies;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<TopicRepliesEntity> getTopicreplies() {
        return topicreplies;
    }

    public void setTopicreplies(List<TopicRepliesEntity> topicreplies) {
        this.topicreplies = topicreplies;
    }

    public static class TopicRepliesEntity {
        private String _id;
        private String topicbarid;
        private String topicid;
        private int replyuid;
        private String usertype;
        private String replynickname;
        private String replytime;
        private String content;
        private String source;
        private int subreplies;
        private int replystyle;
        private String replyid;
        private int msgid;

        private int pid;
        /**
         * attachid : f66da80f-3b89-4acd-bab7-5aa833141d2b
         * attachurl : images/topics/upload_a1baf2c0afde68dd72ab550bee3cbc2c.jpg
         * width : 640
         * height : 1138
         */

        private List<AttachmentsEntity> attachments;

        private List<TopicRepliesEntity> replies;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getTopicbarid() {
            return topicbarid;
        }

        public void setTopicbarid(String topicbarid) {
            this.topicbarid = topicbarid;
        }

        public String getTopicid() {
            return topicid;
        }

        public void setTopicid(String topicid) {
            this.topicid = topicid;
        }

        public int getReplyuid() {
            return replyuid;
        }

        public void setReplyuid(int replyuid) {
            this.replyuid = replyuid;
        }

        public String getUsertype() {
            return usertype;
        }

        public void setUsertype(String usertype) {
            this.usertype = usertype;
        }

        public String getReplynickname() {
            return replynickname;
        }

        public void setReplynickname(String replynickname) {
            this.replynickname = replynickname;
        }

        public String getReplytime() {
            return replytime;
        }

        public void setReplytime(String replytime) {
            this.replytime = replytime;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public int getSubreplies() {
            return subreplies;
        }

        public void setSubreplies(int subreplies) {
            this.subreplies = subreplies;
        }

        public int getReplystyle() {
            return replystyle;
        }

        public void setReplystyle(int replystyle) {
            this.replystyle = replystyle;
        }

        public String getReplyid() {
            return replyid;
        }

        public void setReplyid(String replyid) {
            this.replyid = replyid;
        }

        public int getMsgid() {
            return msgid;
        }

        public void setMsgid(int msgid) {
            this.msgid = msgid;
        }

        public List<AttachmentsEntity> getAttachments() {
            return attachments;
        }

        public void setAttachments(List<AttachmentsEntity> attachments) {
            this.attachments = attachments;
        }

        public List<TopicRepliesEntity> getReplies() {
            return replies;
        }

        public void setReplies(List<TopicRepliesEntity> replies) {
            this.replies = replies;
        }

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

    /*public static class RepliesEntity extends TopicRepliesEntity{
        *//*private String _id;
        private int topicbarid;
        private int topicid;
        private int replyuid;
        private String usertype;
        private String replynickname;
        private String replytime;
        private String content;
        private int subreplies;*//*
        private int pid;
        *//*private int replyid;
        private int msgid;*//*
        *//**
         * _id : 5704ac863c1b486a206ef0a5
         * topicbarid : 61
         * topicid : 55
         * replyuid : 124
         * usertype : 0
         * replynickname : 郭小青
         * replytime : 2016-04-06 14:28:22
         * content : 那你
         * subreplies : 0
         * pid : 287
         * replyid : 298
         * msgid : 1
         *//*

        private List<RepliesEntity> replies;

        public RepliesEntity() {
        }

        public RepliesEntity(TopicRepliesEntity tpcReply) {
            set_id(tpcReply._id);
            setTopicbarid(tpcReply.getTopicbarid());
            setTopicid(tpcReply.getTopicid());
            setReplyuid(tpcReply.getReplyuid());
            setUsertype(tpcReply.getUsertype());
            setReplynickname(tpcReply.getReplynickname());
            setReplytime(tpcReply.getReplytime());
            setContent(tpcReply.getContent());
            setSubreplies(tpcReply.getSubreplies());
            setReplyid(tpcReply.getReplyid());
            setMsgid(tpcReply.getMsgid());
        }


        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }


        public List<RepliesEntity> getReplies() {
            return replies;
        }

        public void setReplies(List<RepliesEntity> replies) {
            this.replies = replies;
        }

    }*/
}
