package com.android.doctor.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Yong on 2016/5/5.
 */
public class TempPlanList {

    /**
     * name : 乙肝方案勿删1
     * tplid : bede7970-8381-426a-a14c-8e12d82c7c87
     * usecount : 1
     * uid : 122
     * issys : 1
     * isshare : 0
     * createtime : 2016-01-29 16:49:11
     * lastupdatetime : 1454057351374
     * source : clone
     * hosid : 7
     * hosname : 太原市第三人民医院
     * username : 郭瑛
     */

    private List<TplsEntity> tpls;

    public List<TplsEntity> getTpls() {
        return tpls;
    }

    public void setTpls(List<TplsEntity> tpls) {
        this.tpls = tpls;
    }

    public static class TplsEntity implements Parcelable {
        private String name;
        private String tplid;
        private int usecount;
        private int uid;
        private int issys;
        private int isshare;
        private String createtime;
        private long lastupdatetime;
        private String source;
        private int hosid;
        private String hosname;
        private String username;
        private String diag;
        private String treat;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTplid() {
            return tplid;
        }

        public void setTplid(String tplid) {
            this.tplid = tplid;
        }

        public int getUsecount() {
            return usecount;
        }

        public void setUsecount(int usecount) {
            this.usecount = usecount;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getIssys() {
            return issys;
        }

        public void setIssys(int issys) {
            this.issys = issys;
        }

        public int getIsshare() {
            return isshare;
        }

        public void setIsshare(int isshare) {
            this.isshare = isshare;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public long getLastupdatetime() {
            return lastupdatetime;
        }

        public void setLastupdatetime(long lastupdatetime) {
            this.lastupdatetime = lastupdatetime;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public int getHosid() {
            return hosid;
        }

        public void setHosid(int hosid) {
            this.hosid = hosid;
        }

        public String getHosname() {
            return hosname;
        }

        public void setHosname(String hosname) {
            this.hosname = hosname;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getDiag() {
            return diag;
        }

        public String getTreat() {
            return treat;
        }

        public void setDiag(String diag) {
            this.diag = diag;
        }

        public void setTreat(String treat) {
            this.treat = treat;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.name);
            dest.writeString(this.tplid);
            dest.writeInt(this.usecount);
            dest.writeInt(this.uid);
            dest.writeInt(this.issys);
            dest.writeInt(this.isshare);
            dest.writeString(this.createtime);
            dest.writeLong(this.lastupdatetime);
            dest.writeString(this.source);
            dest.writeInt(this.hosid);
            dest.writeString(this.hosname);
            dest.writeString(this.username);
            dest.writeString(this.diag);
            dest.writeString(this.treat);
        }

        public TplsEntity() {
        }

        protected TplsEntity(Parcel in) {
            this.name = in.readString();
            this.tplid = in.readString();
            this.usecount = in.readInt();
            this.uid = in.readInt();
            this.issys = in.readInt();
            this.isshare = in.readInt();
            this.createtime = in.readString();
            this.lastupdatetime = in.readLong();
            this.source = in.readString();
            this.hosid = in.readInt();
            this.hosname = in.readString();
            this.username = in.readString();
            this.diag = in.readString();
            this.treat = in.readString();
        }

        public static final Parcelable.Creator<TplsEntity> CREATOR = new Parcelable.Creator<TplsEntity>() {
            @Override
            public TplsEntity createFromParcel(Parcel source) {
                return new TplsEntity(source);
            }

            @Override
            public TplsEntity[] newArray(int size) {
                return new TplsEntity[size];
            }
        };
    }
}
