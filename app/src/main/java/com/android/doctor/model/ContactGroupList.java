package com.android.doctor.model;

import java.util.List;

/**
 * Created by Yong on 2016/4/22.
 */
public class ContactGroupList {

    /**
     * _id : 56937345af1cecd603426674
     * name : 郭小青患者群
     * type : 2
     * permission : 1
     * declared : 希望我们坚强，善良，乐观，相信一切都...
     * groupclass : ["乙肝"]
     * voipAccount : 8001053600000082
     * uuid : 723c48c4-9a27-4e7c-8300-48f2d2e1f0e7
     * ownertype : 0
     * owner : 124
     * ownernickname : 郭小青
     * source : ios
     * count : 3
     * dateCreated : 1452503877437
     * status : 0
     * imgurl : images/group/default.jpg
     * groupId : gg8001053686
     * tag : 0
     */

    private List<GroupsEntity> groups;

    public List<GroupsEntity> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupsEntity> groups) {
        this.groups = groups;
    }

    public static class GroupsEntity {
        private String _id;
        private String name;
        private int type;
        private int permission;
        private String declared;
        private String voipAccount;
        private String uuid;
        private int ownertype;
        private int owner;
        private String ownernickname;
        private String source;
        private int count;
        private long dateCreated;
        private int status;
        private String imgurl;
        private String groupId;
        private int tag;
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

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getPermission() {
            return permission;
        }

        public void setPermission(int permission) {
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

        public int getOwnertype() {
            return ownertype;
        }

        public void setOwnertype(int ownertype) {
            this.ownertype = ownertype;
        }

        public int getOwner() {
            return owner;
        }

        public void setOwner(int owner) {
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

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public long getDateCreated() {
            return dateCreated;
        }

        public void setDateCreated(long dateCreated) {
            this.dateCreated = dateCreated;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
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

        public int getTag() {
            return tag;
        }

        public void setTag(int tag) {
            this.tag = tag;
        }

        public List<String> getGroupclass() {
            return groupclass;
        }

        public void setGroupclass(List<String> groupclass) {
            this.groupclass = groupclass;
        }
    }
}
