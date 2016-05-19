package com.android.doctor.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Yong on 2016/4/18.
 */
public class DiagList {

    private List<DiagEntity> list;

    public List<DiagEntity> getList() {
        return list;
    }

    public void setList(List<DiagEntity> list) {
        this.list = list;
    }

    public static class DiagEntity implements Parcelable {
        private String _id;
        private String name;
        private String spell;
        private String icd10;
        private int hosid;
        private String dept;
        private String keyword;
        private int orderno;

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

        public String getSpell() {
            return spell;
        }

        public void setSpell(String spell) {
            this.spell = spell;
        }

        public String getIcd10() {
            return icd10;
        }

        public void setIcd10(String icd10) {
            this.icd10 = icd10;
        }

        public int getHosid() {
            return hosid;
        }

        public void setHosid(int hosid) {
            this.hosid = hosid;
        }

        public String getDept() {
            return dept;
        }

        public void setDept(String dept) {
            this.dept = dept;
        }

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public int getOrderno() {
            return orderno;
        }

        public void setOrderno(int orderno) {
            this.orderno = orderno;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this._id);
            dest.writeString(this.name);
            dest.writeString(this.spell);
            dest.writeString(this.icd10);
            dest.writeInt(this.hosid);
            dest.writeString(this.dept);
            dest.writeString(this.keyword);
            dest.writeInt(this.orderno);
        }

        public DiagEntity() {
        }

        protected DiagEntity(Parcel in) {
            this._id = in.readString();
            this.name = in.readString();
            this.spell = in.readString();
            this.icd10 = in.readString();
            this.hosid = in.readInt();
            this.dept = in.readString();
            this.keyword = in.readString();
            this.orderno = in.readInt();
        }

        public static final Parcelable.Creator<DiagEntity> CREATOR = new Parcelable.Creator<DiagEntity>() {
            @Override
            public DiagEntity createFromParcel(Parcel source) {
                return new DiagEntity(source);
            }

            @Override
            public DiagEntity[] newArray(int size) {
                return new DiagEntity[size];
            }
        };
    }
}
