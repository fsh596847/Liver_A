package com.android.doctor.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Yong on 2016/3/24.
 */
public class MsgUserData implements Parcelable {


    /**
     * type : 0
     * to : {"id":"gg8001053686","type":0,"name":"郭小青患者群","uuid":"723c48c4-9a27-4e7c-8300-48f2d2e1f0e7"}
     * from : {"id":"124","name":"郭小青","type":"0","uuid":"c680dddf-0a25-4167-be59-0ea2ed673bfe"}
     */

    private String type;
    /**
     * id : gg8001053686
     * type : 0
     * name : 郭小青患者群
     * uuid : 723c48c4-9a27-4e7c-8300-48f2d2e1f0e7
     */

    private ToEntity to;
    /**
     * id : 124
     * name : 郭小青
     * type : 0
     * uuid : c680dddf-0a25-4167-be59-0ea2ed673bfe
     */

    private FromEntity from;

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

    public static class ToEntity implements Parcelable {
        private String id;
        private String type;
        private String name;
        private String uuid;

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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeString(this.type);
            dest.writeString(this.name);
            dest.writeString(this.uuid);
        }

        public ToEntity() {
        }

        protected ToEntity(Parcel in) {
            this.id = in.readString();
            this.type = in.readString();
            this.name = in.readString();
            this.uuid = in.readString();
        }

        public static final Creator<ToEntity> CREATOR = new Creator<ToEntity>() {
            @Override
            public ToEntity createFromParcel(Parcel source) {
                return new ToEntity(source);
            }

            @Override
            public ToEntity[] newArray(int size) {
                return new ToEntity[size];
            }
        };
    }

    public static class FromEntity implements Parcelable {
        private String id;
        private String name;
        private String type;
        private String uuid;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeString(this.name);
            dest.writeString(this.type);
            dest.writeString(this.uuid);
        }

        public FromEntity() {
        }

        protected FromEntity(Parcel in) {
            this.id = in.readString();
            this.name = in.readString();
            this.type = in.readString();
            this.uuid = in.readString();
        }

        public static final Creator<FromEntity> CREATOR = new Creator<FromEntity>() {
            @Override
            public FromEntity createFromParcel(Parcel source) {
                return new FromEntity(source);
            }

            @Override
            public FromEntity[] newArray(int size) {
                return new FromEntity[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
        dest.writeParcelable(this.to, flags);
        dest.writeParcelable(this.from, flags);
    }

    public MsgUserData() {
    }

    protected MsgUserData(Parcel in) {
        this.type = in.readString();
        this.to = in.readParcelable(ToEntity.class.getClassLoader());
        this.from = in.readParcelable(FromEntity.class.getClassLoader());
    }

    public static final Parcelable.Creator<MsgUserData> CREATOR = new Parcelable.Creator<MsgUserData>() {
        @Override
        public MsgUserData createFromParcel(Parcel source) {
            return new MsgUserData(source);
        }

        @Override
        public MsgUserData[] newArray(int size) {
            return new MsgUserData[size];
        }
    };
}
