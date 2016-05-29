package com.android.doctor.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yong on 2016/5/8.
 */
public class TopicReplyList implements Parcelable {

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

    public static class TopicRepliesEntity implements Parcelable {
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


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this._id);
            dest.writeString(this.topicbarid);
            dest.writeString(this.topicid);
            dest.writeInt(this.replyuid);
            dest.writeString(this.usertype);
            dest.writeString(this.replynickname);
            dest.writeString(this.replytime);
            dest.writeString(this.content);
            dest.writeString(this.source);
            dest.writeInt(this.subreplies);
            dest.writeInt(this.replystyle);
            dest.writeString(this.replyid);
            dest.writeInt(this.msgid);
            dest.writeInt(this.pid);
            dest.writeTypedList(this.attachments);
            dest.writeTypedList(this.replies);
        }

        public TopicRepliesEntity() {
        }

        protected TopicRepliesEntity(Parcel in) {
            this._id = in.readString();
            this.topicbarid = in.readString();
            this.topicid = in.readString();
            this.replyuid = in.readInt();
            this.usertype = in.readString();
            this.replynickname = in.readString();
            this.replytime = in.readString();
            this.content = in.readString();
            this.source = in.readString();
            this.subreplies = in.readInt();
            this.replystyle = in.readInt();
            this.replyid = in.readString();
            this.msgid = in.readInt();
            this.pid = in.readInt();
            this.attachments = in.createTypedArrayList(AttachmentsEntity.CREATOR);
            this.replies = in.createTypedArrayList(TopicRepliesEntity.CREATOR);
        }

        public static final Parcelable.Creator<TopicRepliesEntity> CREATOR = new Parcelable.Creator<TopicRepliesEntity>() {
            @Override
            public TopicRepliesEntity createFromParcel(Parcel source) {
                return new TopicRepliesEntity(source);
            }

            @Override
            public TopicRepliesEntity[] newArray(int size) {
                return new TopicRepliesEntity[size];
            }
        };
    }

    public static class AttachmentsEntity implements Parcelable {
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


        public AttachmentsEntity() {
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.attachid);
            dest.writeString(this.attachurl);
            dest.writeInt(this.width);
            dest.writeInt(this.height);
        }

        protected AttachmentsEntity(Parcel in) {
            this.attachid = in.readString();
            this.attachurl = in.readString();
            this.width = in.readInt();
            this.height = in.readInt();
        }

        public static final Creator<AttachmentsEntity> CREATOR = new Creator<AttachmentsEntity>() {
            @Override
            public AttachmentsEntity createFromParcel(Parcel source) {
                return new AttachmentsEntity(source);
            }

            @Override
            public AttachmentsEntity[] newArray(int size) {
                return new AttachmentsEntity[size];
            }
        };
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.total);
        dest.writeTypedList(this.topicreplies);
    }

    public TopicReplyList() {
    }

    protected TopicReplyList(Parcel in) {
        this.total = in.readInt();
        this.topicreplies = in.createTypedArrayList(TopicRepliesEntity.CREATOR);
    }

    public static final Parcelable.Creator<TopicReplyList> CREATOR = new Parcelable.Creator<TopicReplyList>() {
        @Override
        public TopicReplyList createFromParcel(Parcel source) {
            return new TopicReplyList(source);
        }

        @Override
        public TopicReplyList[] newArray(int size) {
            return new TopicReplyList[size];
        }
    };
}
