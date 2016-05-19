package com.android.doctor.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Yong on 2016/5/14.
 */
public class SuggClassList {

    /**
     * _id :
     * code :
     * name :
     * imgurl :
     */

    private List<SuggEntity> data;

    public List<SuggEntity> getData() {
        return data;
    }

    public void setData(List<SuggEntity> data) {
        this.data = data;
    }

    public static class SuggEntity implements Parcelable {
        private String _id;
        private String code;
        private String name;
        private String imgurl;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this._id);
            dest.writeString(this.code);
            dest.writeString(this.name);
            dest.writeString(this.imgurl);
        }

        public SuggEntity() {
        }

        protected SuggEntity(Parcel in) {
            this._id = in.readString();
            this.code = in.readString();
            this.name = in.readString();
            this.imgurl = in.readString();
        }

        public static final Parcelable.Creator<SuggEntity> CREATOR = new Parcelable.Creator<SuggEntity>() {
            @Override
            public SuggEntity createFromParcel(Parcel source) {
                return new SuggEntity(source);
            }

            @Override
            public SuggEntity[] newArray(int size) {
                return new SuggEntity[size];
            }
        };
    }
}
