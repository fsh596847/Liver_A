package com.android.doctor.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yong on 2016/4/28.
 */
public class GroupList {

    /**
     * _id : 5695abe6090a2d1424e9b151
     * name : 替比夫定片
     * type : 2
     * permission : 1
     * declared : 吃着替比得战友们、进来交流交流经验
     * groupclass : ["病毒携带者"]
     * voipAccount : 8001053600000081
     * uuid : 60a5d9c4-04fe-4dfa-adcc-2f557feca295
     * ownertype : 0
     * owner : 122
     * ownernickname : 郭瑛
     * source : web
     * count : 1
     * dateCreated : 2016-01-13 09:44:06
     * status : 0
     * imgurl : images/group/default.jpg
     * groupId : gg8001053691
     * tag : 0
     */

    private List<GroupsEntity> groups;

    public List<GroupsEntity> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupsEntity> groups) {
        this.groups = groups;
    }

    public static class GroupsEntity implements Parcelable {
        private String _id;
        private String name;
        private String type;
        private String permission;
        private String declared;
        private String voipAccount;
        private String uuid;
        private String ownertype;
        private String owner;
        private String ownernickname;
        private String source;
        private String count;
        private String dateCreated;
        private String status;
        private String imgurl;
        private String groupId;
        private String tag;
        private List<String> groupclass;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
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

        public String getPermission() {
            return permission;
        }

        public void setPermission(String permission) {
            this.permission = permission;
        }

        public String getDeclared() {
            return declared;
        }

        public void setDeclared(String declared) {
            this.declared = declared;
        }

        public String getVoipAccount() {
            return voipAccount;
        }

        public void setVoipAccount(String voipAccount) {
            this.voipAccount = voipAccount;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getOwnertype() {
            return ownertype;
        }

        public void setOwnertype(String ownertype) {
            this.ownertype = ownertype;
        }

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public String getOwnernickname() {
            return ownernickname;
        }

        public void setOwnernickname(String ownernickname) {
            this.ownernickname = ownernickname;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getDateCreated() {
            return dateCreated;
        }

        public void setDateCreated(String dateCreated) {
            this.dateCreated = dateCreated;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public List<String> getGroupclass() {
            return groupclass;
        }

        public void setGroupclass(List<String> groupclass) {
            this.groupclass = groupclass;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this._id);
            dest.writeString(this.name);
            dest.writeString(this.type);
            dest.writeString(this.permission);
            dest.writeString(this.declared);
            dest.writeString(this.voipAccount);
            dest.writeString(this.uuid);
            dest.writeString(this.ownertype);
            dest.writeString(this.owner);
            dest.writeString(this.ownernickname);
            dest.writeString(this.source);
            dest.writeString(this.count);
            dest.writeString(this.dateCreated);
            dest.writeString(this.status);
            dest.writeString(this.imgurl);
            dest.writeString(this.groupId);
            dest.writeString(this.tag);
            dest.writeList(this.groupclass);
        }

        public GroupsEntity() {
        }

        private GroupsEntity(Parcel in) {
            this._id = in.readString();
            this.name = in.readString();
            this.type = in.readString();
            this.permission = in.readString();
            this.declared = in.readString();
            this.voipAccount = in.readString();
            this.uuid = in.readString();
            this.ownertype = in.readString();
            this.owner = in.readString();
            this.ownernickname = in.readString();
            this.source = in.readString();
            this.count = in.readString();
            this.dateCreated = in.readString();
            this.status = in.readString();
            this.imgurl = in.readString();
            this.groupId = in.readString();
            this.tag = in.readString();
            this.groupclass = new ArrayList<String>();
            in.readList(this.groupclass, List.class.getClassLoader());
        }

        public static final Parcelable.Creator<GroupsEntity> CREATOR = new Parcelable.Creator<GroupsEntity>() {
            public GroupsEntity createFromParcel(Parcel source) {
                return new GroupsEntity(source);
            }

            public GroupsEntity[] newArray(int size) {
                return new GroupsEntity[size];
            }
        };
    }

    private String total;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
