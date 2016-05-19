package com.android.doctor.model;

import java.util.List;

/**
 * Created by Yong on 2016/5/8.
 */
public class TopicList {


    private int total;

    private List<TopicEntity> topics;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<TopicEntity> getTopics() {
        return topics;
    }

    public void setTopics(List<TopicEntity> topics) {
        this.topics = topics;
    }

    public static class TopicEntity {
        private String _id;
        private String topicbarid;
        private String title;
        private int createuid;
        private int usertype;
        private String createnickname;
        private String createtime;
        private String content;
        private String source;
        private int replies;
        private int praise;
        private int nextmsgid;
        private int status;
        private String lastreplytime;
        private String lastreplynickname;
        private String topicid;
        private int lastreplyid;
        /**
         * attachid : 21fb0bbf-10ae-4236-b9f2-6bf591cbd5c4
         * attachurl : images/topics/upload_f0d140f6839d92f645dc9991e4e3606f.jpg
         * width : 1280
         * height : 720
         */

        private List<AttachmentsEntity> attachments;

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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getCreateuid() {
            return createuid;
        }

        public void setCreateuid(int createuid) {
            this.createuid = createuid;
        }

        public int getUsertype() {
            return usertype;
        }

        public void setUsertype(int usertype) {
            this.usertype = usertype;
        }

        public String getCreatenickname() {
            return createnickname;
        }

        public void setCreatenickname(String createnickname) {
            this.createnickname = createnickname;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
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

        public int getReplies() {
            return replies;
        }

        public void setReplies(int replies) {
            this.replies = replies;
        }

        public int getPraise() {
            return praise;
        }

        public void setPraise(int praise) {
            this.praise = praise;
        }

        public int getNextmsgid() {
            return nextmsgid;
        }

        public void setNextmsgid(int nextmsgid) {
            this.nextmsgid = nextmsgid;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getLastreplytime() {
            return lastreplytime;
        }

        public void setLastreplytime(String lastreplytime) {
            this.lastreplytime = lastreplytime;
        }

        public String getLastreplynickname() {
            return lastreplynickname;
        }

        public void setLastreplynickname(String lastreplynickname) {
            this.lastreplynickname = lastreplynickname;
        }

        public String getTopicid() {
            return topicid;
        }

        public void setTopicid(String topicid) {
            this.topicid = topicid;
        }

        public int getLastreplyid() {
            return lastreplyid;
        }

        public void setLastreplyid(int lastreplyid) {
            this.lastreplyid = lastreplyid;
        }

        public List<AttachmentsEntity> getAttachments() {
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
