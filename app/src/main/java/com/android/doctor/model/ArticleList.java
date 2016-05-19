package com.android.doctor.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Yong on 2016/5/13.
 */
public class ArticleList {


    private List<SuggestsEntity> suggests;

    public List<SuggestsEntity> getSuggests() {
        return suggests;
    }

    public void setSuggests(List<SuggestsEntity> suggests) {
        this.suggests = suggests;
    }

    public static class SuggestsEntity implements Parcelable {
        private String _id;
        private String title;
        private String pubname;
        private String pubtime;
        private String uniquecode;
        private int suggid;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPubname() {
            return pubname;
        }

        public void setPubname(String pubname) {
            this.pubname = pubname;
        }

        public String getPubtime() {
            return pubtime;
        }

        public void setPubtime(String pubtime) {
            this.pubtime = pubtime;
        }

        public String getUniquecode() {
            return uniquecode;
        }

        public void setUniquecode(String uniquecode) {
            this.uniquecode = uniquecode;
        }

        public int getSuggid() {
            return suggid;
        }

        public void setSuggid(int suggid) {
            this.suggid = suggid;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this._id);
            dest.writeString(this.title);
            dest.writeString(this.pubname);
            dest.writeString(this.pubtime);
            dest.writeString(this.uniquecode);
            dest.writeInt(this.suggid);
        }

        public SuggestsEntity() {
        }

        protected SuggestsEntity(Parcel in) {
            this._id = in.readString();
            this.title = in.readString();
            this.pubname = in.readString();
            this.pubtime = in.readString();
            this.uniquecode = in.readString();
            this.suggid = in.readInt();
        }

        public static final Parcelable.Creator<SuggestsEntity> CREATOR = new Parcelable.Creator<SuggestsEntity>() {
            @Override
            public SuggestsEntity createFromParcel(Parcel source) {
                return new SuggestsEntity(source);
            }

            @Override
            public SuggestsEntity[] newArray(int size) {
                return new SuggestsEntity[size];
            }
        };
    }
}
