package com.android.doctor.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Yong on 2016/4/25.
 */
public class PatientBaseInfo {

    /**
     * _id : 56fbcedd81d9c7606e3c6bfe
     * address : 山西省 太原市 市辖区
     * phone : 13935109451
     * card : 140303198605290454
     * birthday : 1986-05-29
     * sex : 1
     * name : 王文举
     * puid : 106
     * uuid : 6b44444f-99c2-42a8-9907-6aa04781cd17
     * createtime : 1459343069665
     * py : WWJ
     * isplan : 0
     * ishasarchives : 0
     */

    private PatientEntity patient;

    public PatientEntity getPatient() {
        return patient;
    }

    public void setPatient(PatientEntity patient) {
        this.patient = patient;
    }

    public static class PatientEntity implements Parcelable {
        private String _id;
        private String address;
        private String phone;
        private String card;
        private String birthday;
        private String sex;
        private String name;
        private String puid;
        private String uuid;
        private String createtime;
        private String py;
        private String isplan;
        private String ishasarchives;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getCard() {
            return card;
        }

        public void setCard(String card) {
            this.card = card;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPuid() {
            return puid;
        }

        public void setPuid(String puid) {
            this.puid = puid;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getPy() {
            return py;
        }

        public void setPy(String py) {
            this.py = py;
        }

        public String getIsplan() {
            return isplan;
        }

        public void setIsplan(String isplan) {
            this.isplan = isplan;
        }

        public String getIshasarchives() {
            return ishasarchives;
        }

        public void setIshasarchives(String ishasarchives) {
            this.ishasarchives = ishasarchives;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this._id);
            dest.writeString(this.address);
            dest.writeString(this.phone);
            dest.writeString(this.card);
            dest.writeString(this.birthday);
            dest.writeString(this.sex);
            dest.writeString(this.name);
            dest.writeString(this.puid);
            dest.writeString(this.uuid);
            dest.writeString(this.createtime);
            dest.writeString(this.py);
            dest.writeString(this.isplan);
            dest.writeString(this.ishasarchives);
        }

        public PatientEntity() {
        }

        protected PatientEntity(Parcel in) {
            this._id = in.readString();
            this.address = in.readString();
            this.phone = in.readString();
            this.card = in.readString();
            this.birthday = in.readString();
            this.sex = in.readString();
            this.name = in.readString();
            this.puid = in.readString();
            this.uuid = in.readString();
            this.createtime = in.readString();
            this.py = in.readString();
            this.isplan = in.readString();
            this.ishasarchives = in.readString();
        }

        public static final Parcelable.Creator<PatientEntity> CREATOR = new Parcelable.Creator<PatientEntity>() {
            @Override
            public PatientEntity createFromParcel(Parcel source) {
                return new PatientEntity(source);
            }

            @Override
            public PatientEntity[] newArray(int size) {
                return new PatientEntity[size];
            }
        };
    }
}
