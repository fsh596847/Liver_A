package com.yuntongxun.kitsdk.custom;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Yong on 2016/4/21.
 */
public class CommonUserData {
    private String type;
    private ToEntity to;
    private FromEntity from;

    public CommonUserData() {
    }

    public CommonUserData(String type, ToEntity to, FromEntity from) {
        this.type = type;
        this.to = to;
        this.from = from;
    }

    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("type", type);
            object.put("to", to.toJson());
            object.put("from", from.toJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public static class ToEntity {
        private String id;
        private String type;
        private String name;
        private String uuid;

        public ToEntity() {
        }

        public ToEntity(String id, String type, String name, String uuid) {
            this.id = id;
            this.type = type;
            this.name = name;
            this.uuid = uuid;
        }

        public JSONObject toJson() {
            JSONObject object = new JSONObject();
            try {
                object.put("id", id);
                object.put("type", type);
                object.put("name", name);
                object.put("uuid", uuid);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return object;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
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

        public boolean equals(ToEntity to) {
            if (to == null) return false;
            return this.getId().equals(to.getId())
                    && getType().equals(to.getType())
                    && getName().equals(to.getName())
                    && getUuid().equals(to.getUuid());
        }
    }

    public static class FromEntity {
        private String id;
        private String type;
        private String name;
        private String uuid;

        public FromEntity() {
        }

        public FromEntity(String id, String type, String name, String uuid) {
            this.id = id;
            this.type = type;
            this.name = name;
            this.uuid = uuid;
        }

        public JSONObject toJson() {
            JSONObject object = new JSONObject();
            try {
                object.put("id", id);
                object.put("type", type);
                object.put("name", name);
                object.put("uuid", uuid);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return object;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
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


        public boolean equals(ToEntity to) {
            if (to == null) return false;
            return this.getId().equals(to.getId())
                    && getType().equals(to.getType())
                    && getName().equals(to.getName())
                    && getUuid().equals(to.getUuid());
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
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
