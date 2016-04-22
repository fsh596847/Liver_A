package com.yuntongxun.kitsdk.beans;

/**
 * Created by Yong on 2016/4/21.
 */
public class CommonUserData {
    private int type;
    private ToEntity to;
    private FromEntity from;

    public static class ToEntity {
        private String id;
        private int type;
        private String name;
        private String uuid;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }
    }

    public static class FromEntity {
        private String id;
        private int type;
        private String name;
        private String uuid;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ToEntity getTo() {
        return to;
    }

    public void setTo(ToEntity to) {
        this.to = to;
    }

    public FromEntity getFrom() {
        return from;
    }

    public void setFrom(FromEntity from) {
        this.from = from;
    }
}
