package com.android.doctor.model;

import java.util.List;

/**
 * Created by Yong on 2016/5/8.
 */
public class TopicBarList {

    private int total;

    private List<TopicbarsEntity> topicbars;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<TopicbarsEntity> getTopicbars() {
        return topicbars;
    }

    public void setTopicbars(List<TopicbarsEntity> topicbars) {
        this.topicbars = topicbars;
    }

    public static class TopicbarsEntity {
        private String _id;
        private String topicbarname;
        private String slug;
        private String topicbarstyle;
        private int owneruid;
        private String bgimgurl;
        private int followers;
        private int topics;
        private String imgurl;
        private String createtime;
        private String topicbarid;
        private List<String> topicclass;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getTopicbarname() {
            return topicbarname;
        }

        public void setTopicbarname(String topicbarname) {
            this.topicbarname = topicbarname;
        }

        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }

        public String getTopicbarstyle() {
            return topicbarstyle;
        }

        public void setTopicbarstyle(String topicbarstyle) {
            this.topicbarstyle = topicbarstyle;
        }

        public int getOwneruid() {
            return owneruid;
        }

        public void setOwneruid(int owneruid) {
            this.owneruid = owneruid;
        }

        public String getBgimgurl() {
            return bgimgurl;
        }

        public void setBgimgurl(String bgimgurl) {
            this.bgimgurl = bgimgurl;
        }

        public int getFollowers() {
            return followers;
        }

        public void setFollowers(int followers) {
            this.followers = followers;
        }

        public int getTopics() {
            return topics;
        }

        public void setTopics(int topics) {
            this.topics = topics;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getTopicbarid() {
            return topicbarid;
        }

        public void setTopicbarid(String topicbarid) {
            this.topicbarid = topicbarid;
        }

        public List<String> getTopicclass() {
            return topicclass;
        }

        public void setTopicclass(List<String> topicclass) {
            this.topicclass = topicclass;
        }
    }
}
