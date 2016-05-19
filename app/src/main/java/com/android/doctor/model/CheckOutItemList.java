package com.android.doctor.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Yong on 2016/5/7.
 */
public class CheckOutItemList {

    /**
     * _id : 55e6c391970e2ba0f5a53ca4
     * code : 6001
     * name : B超
     */

    private List<CKOEntity> list;

    public List<CKOEntity> getList() {
        return list;
    }

    public void setList(List<CKOEntity> list) {
        this.list = list;
    }

    public static class CKOEntity implements Parcelable {
        private String _id;
        private int code;
        private String name;

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


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this._id);
            dest.writeInt(this.code);
            dest.writeString(this.name);
        }

        public CKOEntity() {
        }

        protected CKOEntity(Parcel in) {
            this._id = in.readString();
            this.code = in.readInt();
            this.name = in.readString();
        }

        public static final Parcelable.Creator<CKOEntity> CREATOR = new Parcelable.Creator<CKOEntity>() {
            @Override
            public CKOEntity createFromParcel(Parcel source) {
                return new CKOEntity(source);
            }

            @Override
            public CKOEntity[] newArray(int size) {
                return new CKOEntity[size];
            }
        };
    }
}
