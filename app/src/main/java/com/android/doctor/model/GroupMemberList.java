package com.android.doctor.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Yong on 2016/4/28.
 */
public class GroupMemberList {

    /**
     * _id : 5695ab04090a2d1424e9b14f
     * display : 郭瑛
     * groupId : gg8001053689
     * mail :
     * tel :
     * remark :
     * rule : 0
     * ownertype : 0
     * owneruid : 122
     * jointime : 1452649220865
     * joinstatus : 0
     * status : 0
     * voipAccount : 8001053600000081
     * source : web
     */

    private List<GroupMemberEntity> members;

    public List<GroupMemberEntity> getMembers() {
        return members;
    }

    public void setMembers(List<GroupMemberEntity> members) {
        this.members = members;
    }

    public static class GroupMemberEntity implements Parcelable {
        private String _id;
        private String display;
        private String groupId;
        private String mail;
        private String tel;
        private String remark;
        private String rule;
        private String ownertype;
        private String owneruid;
        private String jointime;
        private String joinstatus;
        private String status;
        private String voipAccount;
        private String source;
        private boolean isManager;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getDisplay() {
            return display;
        }

        public void setDisplay(String display) {
            this.display = display;
        }

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public String getMail() {
            return mail;
        }

        public void setMail(String mail) {
            this.mail = mail;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getRule() {
            return rule;
        }

        public void setRule(String rule) {
            this.rule = rule;
        }

        public String getOwnertype() {
            return ownertype;
        }

        public void setOwnertype(String ownertype) {
            this.ownertype = ownertype;
        }

        public String getOwneruid() {
            return owneruid;
        }

        public void setOwneruid(String owneruid) {
            this.owneruid = owneruid;
        }

        public String getJointime() {
            return jointime;
        }

        public void setJointime(String jointime) {
            this.jointime = jointime;
        }

        public String getJoinstatus() {
            return joinstatus;
        }

        public void setJoinstatus(String joinstatus) {
            this.joinstatus = joinstatus;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getVoipAccount() {
            return voipAccount;
        }

        public void setVoipAccount(String voipAccount) {
            this.voipAccount = voipAccount;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public boolean isManager() {
            return isManager;
        }

        public void setIsManager(boolean manager) {
            isManager = manager;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this._id);
            dest.writeString(this.display);
            dest.writeString(this.groupId);
            dest.writeString(this.mail);
            dest.writeString(this.tel);
            dest.writeString(this.remark);
            dest.writeString(this.rule);
            dest.writeString(this.ownertype);
            dest.writeString(this.owneruid);
            dest.writeString(this.jointime);
            dest.writeString(this.joinstatus);
            dest.writeString(this.status);
            dest.writeString(this.voipAccount);
            dest.writeString(this.source);
        }

        public GroupMemberEntity() {
        }

        private GroupMemberEntity(Parcel in) {
            this._id = in.readString();
            this.display = in.readString();
            this.groupId = in.readString();
            this.mail = in.readString();
            this.tel = in.readString();
            this.remark = in.readString();
            this.rule = in.readString();
            this.ownertype = in.readString();
            this.owneruid = in.readString();
            this.jointime = in.readString();
            this.joinstatus = in.readString();
            this.status = in.readString();
            this.voipAccount = in.readString();
            this.source = in.readString();
        }

        public static final Parcelable.Creator<GroupMemberEntity> CREATOR = new Parcelable.Creator<GroupMemberEntity>() {
            public GroupMemberEntity createFromParcel(Parcel source) {
                return new GroupMemberEntity(source);
            }

            public GroupMemberEntity[] newArray(int size) {
                return new GroupMemberEntity[size];
            }
        };
    }
}
