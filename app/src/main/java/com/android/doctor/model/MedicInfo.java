package com.android.doctor.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yong on 2016/4/17.
 */
public class MedicInfo implements Parcelable {

    private List<MedicInfoEntity> list;

    public List<MedicInfoEntity> getList() {
        return list;
    }

    public void setList(List<MedicInfoEntity> list) {
        this.list = list;
    }

    public static class MedicInfoEntity implements Parcelable {
        private String _id;
        private String code;
        private String zm;
        private int pcode;
        private String pname;
        private String py;
        private String gg;
        private String bz;
        private String cd;

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

        public String getZm() {
            return zm;
        }

        public void setZm(String zm) {
            this.zm = zm;
        }

        public int getPcode() {
            return pcode;
        }

        public void setPcode(int pcode) {
            this.pcode = pcode;
        }

        public String getPname() {
            return pname;
        }

        public void setPname(String pname) {
            this.pname = pname;
        }

        public String getPy() {
            return py;
        }

        public void setPy(String py) {
            this.py = py;
        }

        public String getGg() {
            return gg;
        }

        public void setGg(String gg) {
            this.gg = gg;
        }

        public String getBz() {
            return bz;
        }

        public void setBz(String bz) {
            this.bz = bz;
        }

        public String getCd() {
            return cd;
        }

        public void setCd(String cd) {
            this.cd = cd;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this._id);
            dest.writeString(this.code);
            dest.writeString(this.zm);
            dest.writeInt(this.pcode);
            dest.writeString(this.pname);
            dest.writeString(this.py);
            dest.writeString(this.gg);
            dest.writeString(this.bz);
            dest.writeString(this.cd);
        }

        public MedicInfoEntity() {
        }

        private MedicInfoEntity(Parcel in) {
            this._id = in.readString();
            this.code = in.readString();
            this.zm = in.readString();
            this.pcode = in.readInt();
            this.pname = in.readString();
            this.py = in.readString();
            this.gg = in.readString();
            this.bz = in.readString();
            this.cd = in.readString();
        }

        public static final Parcelable.Creator<MedicInfoEntity> CREATOR = new Parcelable.Creator<MedicInfoEntity>() {
            public MedicInfoEntity createFromParcel(Parcel source) {
                return new MedicInfoEntity(source);
            }

            public MedicInfoEntity[] newArray(int size) {
                return new MedicInfoEntity[size];
            }
        };
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(list);
    }

    public MedicInfo() {
    }

    private MedicInfo(Parcel in) {
        this.list = new ArrayList<>();
        in.readTypedList(list, MedicInfoEntity.CREATOR);
    }

    public static final Parcelable.Creator<MedicInfo> CREATOR = new Parcelable.Creator<MedicInfo>() {
        public MedicInfo createFromParcel(Parcel source) {
            return new MedicInfo(source);
        }

        public MedicInfo[] newArray(int size) {
            return new MedicInfo[size];
        }
    };
}
