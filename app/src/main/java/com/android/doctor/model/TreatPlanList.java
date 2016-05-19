package com.android.doctor.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Yong on 2016/4/18.
 */
public class TreatPlanList {

    /**
     * _id : 56f4de317dd8ce3480e75d49
     * code : 1
     * name : 药物治疗
     */

    private List<TreatPlanEntity> list;

    public List<TreatPlanEntity> getList() {
        return list;
    }

    public void setList(List<TreatPlanEntity> list) {
        this.list = list;
    }

    public static class TreatPlanEntity implements Parcelable {
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

        public TreatPlanEntity() {
        }

        protected TreatPlanEntity(Parcel in) {
            this._id = in.readString();
            this.code = in.readInt();
            this.name = in.readString();
        }

        public static final Parcelable.Creator<TreatPlanEntity> CREATOR = new Parcelable.Creator<TreatPlanEntity>() {
            @Override
            public TreatPlanEntity createFromParcel(Parcel source) {
                return new TreatPlanEntity(source);
            }

            @Override
            public TreatPlanEntity[] newArray(int size) {
                return new TreatPlanEntity[size];
            }
        };
    }
}
