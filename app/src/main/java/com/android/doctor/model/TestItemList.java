package com.android.doctor.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Yong on 2016/5/7.
 */
public class TestItemList {

    /**
     * _id : 566637efd2494d882c96ab8a
     * code : 10279
     * name : ABO血型鉴定
     * spell : aboxxjd
     * sample : 血
     * hosid : 7
     * hiscode : 933
     * count : 1
     */

    private List<ClasslistEntity> classlist;

    public List<ClasslistEntity> getClasslist() {
        return classlist;
    }

    public void setClasslist(List<ClasslistEntity> classlist) {
        this.classlist = classlist;
    }

    public static class ClasslistEntity implements Parcelable {
        private String _id;
        private int code;
        private String name;
        private String spell;
        private String sample;
        private int hosid;
        private int hiscode;
        private int count;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
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

        public String getSample() {
            return sample;
        }

        public void setSample(String sample) {
            this.sample = sample;
        }

        public int getHosid() {
            return hosid;
        }

        public void setHosid(int hosid) {
            this.hosid = hosid;
        }

        public int getHiscode() {
            return hiscode;
        }

        public void setHiscode(int hiscode) {
            this.hiscode = hiscode;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this._id);
            dest.writeInt(this.code);
            dest.writeString(this.name);
            dest.writeString(this.spell);
            dest.writeString(this.sample);
            dest.writeInt(this.hosid);
            dest.writeInt(this.hiscode);
            dest.writeInt(this.count);
        }

        public ClasslistEntity() {
        }

        protected ClasslistEntity(Parcel in) {
            this._id = in.readString();
            this.code = in.readInt();
            this.name = in.readString();
            this.spell = in.readString();
            this.sample = in.readString();
            this.hosid = in.readInt();
            this.hiscode = in.readInt();
            this.count = in.readInt();
        }

        public static final Parcelable.Creator<ClasslistEntity> CREATOR = new Parcelable.Creator<ClasslistEntity>() {
            @Override
            public ClasslistEntity createFromParcel(Parcel source) {
                return new ClasslistEntity(source);
            }

            @Override
            public ClasslistEntity[] newArray(int size) {
                return new ClasslistEntity[size];
            }
        };
    }
}
