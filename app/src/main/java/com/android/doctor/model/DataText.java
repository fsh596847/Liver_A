package com.android.doctor.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Yong on 2016/3/31.
 */
public class DataText implements Parcelable {
    private String title;
    private String content;
    private String content_ex;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent_ex() {
        return content_ex;
    }

    public void setContent_ex(String content_ex) {
        this.content_ex = content_ex;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeString(this.content_ex);
    }

    public DataText() {
    }

    protected DataText(Parcel in) {
        this.title = in.readString();
        this.content = in.readString();
        this.content_ex = in.readString();
    }

    public static final Parcelable.Creator<DataText> CREATOR = new Parcelable.Creator<DataText>() {
        @Override
        public DataText createFromParcel(Parcel source) {
            return new DataText(source);
        }

        @Override
        public DataText[] newArray(int size) {
            return new DataText[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if(o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        if (getClass() != o.getClass()) {
            return false;
        }
        DataText dt = (DataText)o;
        return this.title.equals(dt.getTitle())
                && this.content.equals(dt.getContent())
                && this.content_ex.equals(dt.getContent_ex());
    }
}
