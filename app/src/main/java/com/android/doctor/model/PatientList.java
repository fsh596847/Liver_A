package com.android.doctor.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yong on 2016/4/13.
 */
public class PatientList {

    /**
     * data :
     * total : 2
     */

    private int total;
    /**
     * _id : 570ddd0ed533ddfc3a39a66e
     * duid : 19
     * dname : null
     * puid : 1
     * name : 牛伟强
     * province : null
     * city : null
     * region : null
     * provincename : null
     * cityname : null
     * regionname : null
     * firstuid : 1
     * relationship : 1
     * createTime : 1460526371660
     * focus : 0
     * followupcount : 0
     * groups : ["出院"]
     * followup : 0
     * stat :
     * address : 北京市 北京(市辖区) 东城区
     * phone : 13333514298
     * card : 140524198602094012
     * birthday : 1986-02-09
     * addressextend : 测试地址
     * sex : 1
     * uuid : c88a88d2-46b7-4fe5-bfd9-bdc2f940509a
     * createtime : 1460526350265
     * py : NWQ
     * isplan : 0
     * ishasarchives : 0
     */

    private List<DataEntity> data;

    public void setTotal(int total) {
        this.total = total;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity implements Parcelable {
        private String _id;
        private int duid;
        private String dname;
        private int puid;
        private String name;
        private String province;
        private String city;
        private String region;
        private String provincename;
        private String cityname;
        private String regionname;
        private int firstuid;
        private int relationship;
        private long createTime;
        private int focus;
        private int followupcount;
        private int followup;
        /**
         * isOutpatientDepartment : 0
         * isLiveHospital : 1
         * isFollowUp : 0
         * isPrivate : 0
         * isInHospital : 0
         * isOther : 0
         */

        private StatEntity stat;
        private String address;
        private String phone;
        private String card;
        private String birthday;
        private String addressextend;
        private int sex;
        private String uuid;
        private long createtime;
        private String py;
        private int isplan;
        private int ishasarchives;
        private List<String> groups;

        public void set_id(String _id) {
            this._id = _id;
        }

        public void setDuid(int duid) {
            this.duid = duid;
        }

        public void setDname(String dname) {
            this.dname = dname;
        }

        public void setPuid(int puid) {
            this.puid = puid;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public void setProvincename(String provincename) {
            this.provincename = provincename;
        }

        public void setCityname(String cityname) {
            this.cityname = cityname;
        }

        public void setRegionname(String regionname) {
            this.regionname = regionname;
        }

        public void setFirstuid(int firstuid) {
            this.firstuid = firstuid;
        }

        public void setRelationship(int relationship) {
            this.relationship = relationship;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public void setFocus(int focus) {
            this.focus = focus;
        }

        public void setFollowupcount(int followupcount) {
            this.followupcount = followupcount;
        }

        public void setFollowup(int followup) {
            this.followup = followup;
        }

        public void setStat(StatEntity stat) {
            this.stat = stat;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void setCard(String card) {
            this.card = card;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public void setAddressextend(String addressextend) {
            this.addressextend = addressextend;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public void setCreatetime(long createtime) {
            this.createtime = createtime;
        }

        public void setPy(String py) {
            this.py = py;
        }

        public void setIsplan(int isplan) {
            this.isplan = isplan;
        }

        public void setIshasarchives(int ishasarchives) {
            this.ishasarchives = ishasarchives;
        }

        public void setGroups(List<String> groups) {
            this.groups = groups;
        }

        public String get_id() {
            return _id;
        }

        public int getDuid() {
            return duid;
        }

        public Object getDname() {
            return dname;
        }

        public int getPuid() {
            return puid;
        }

        public String getName() {
            return name;
        }

        public Object getProvince() {
            return province;
        }

        public Object getCity() {
            return city;
        }

        public Object getRegion() {
            return region;
        }

        public Object getProvincename() {
            return provincename;
        }

        public Object getCityname() {
            return cityname;
        }

        public Object getRegionname() {
            return regionname;
        }

        public int getFirstuid() {
            return firstuid;
        }

        public int getRelationship() {
            return relationship;
        }

        public long getCreateTime() {
            return createTime;
        }

        public int getFocus() {
            return focus;
        }

        public int getFollowupcount() {
            return followupcount;
        }

        public int getFollowup() {
            return followup;
        }

        public StatEntity getStat() {
            return stat;
        }

        public String getAddress() {
            return address;
        }

        public String getPhone() {
            return phone;
        }

        public String getCard() {
            return card;
        }

        public String getBirthday() {
            return birthday;
        }

        public String getAddressextend() {
            return addressextend;
        }

        public int getSex() {
            return sex;
        }

        public String getUuid() {
            return uuid;
        }

        public long getCreatetime() {
            return createtime;
        }

        public String getPy() {
            return py;
        }

        public int getIsplan() {
            return isplan;
        }

        public int getIshasarchives() {
            return ishasarchives;
        }

        public List<String> getGroups() {
            return groups;
        }

        public static class StatEntity implements Parcelable {
            private int isOutpatientDepartment;
            private int isLiveHospital;
            private int isFollowUp;
            private int isPrivate;
            private int isInHospital;
            private int isOther;

            public void setIsOutpatientDepartment(int isOutpatientDepartment) {
                this.isOutpatientDepartment = isOutpatientDepartment;
            }

            public void setIsLiveHospital(int isLiveHospital) {
                this.isLiveHospital = isLiveHospital;
            }

            public void setIsFollowUp(int isFollowUp) {
                this.isFollowUp = isFollowUp;
            }

            public void setIsPrivate(int isPrivate) {
                this.isPrivate = isPrivate;
            }

            public void setIsInHospital(int isInHospital) {
                this.isInHospital = isInHospital;
            }

            public void setIsOther(int isOther) {
                this.isOther = isOther;
            }

            public int getIsOutpatientDepartment() {
                return isOutpatientDepartment;
            }

            public int getIsLiveHospital() {
                return isLiveHospital;
            }

            public int getIsFollowUp() {
                return isFollowUp;
            }

            public int getIsPrivate() {
                return isPrivate;
            }

            public int getIsInHospital() {
                return isInHospital;
            }

            public int getIsOther() {
                return isOther;
            }


            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.isOutpatientDepartment);
                dest.writeInt(this.isLiveHospital);
                dest.writeInt(this.isFollowUp);
                dest.writeInt(this.isPrivate);
                dest.writeInt(this.isInHospital);
                dest.writeInt(this.isOther);
            }

            public StatEntity() {
            }

            private StatEntity(Parcel in) {
                this.isOutpatientDepartment = in.readInt();
                this.isLiveHospital = in.readInt();
                this.isFollowUp = in.readInt();
                this.isPrivate = in.readInt();
                this.isInHospital = in.readInt();
                this.isOther = in.readInt();
            }

            public static final Creator<StatEntity> CREATOR = new Creator<StatEntity>() {
                public StatEntity createFromParcel(Parcel source) {
                    return new StatEntity(source);
                }

                public StatEntity[] newArray(int size) {
                    return new StatEntity[size];
                }
            };
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this._id);
            dest.writeInt(this.duid);
            dest.writeString(this.dname);
            dest.writeInt(this.puid);
            dest.writeString(this.name);
            dest.writeString(this.province);
            dest.writeString(this.city);
            dest.writeString(this.region);
            dest.writeString(this.provincename);
            dest.writeString(this.cityname);
            dest.writeString(this.regionname);
            dest.writeInt(this.firstuid);
            dest.writeInt(this.relationship);
            dest.writeLong(this.createTime);
            dest.writeInt(this.focus);
            dest.writeInt(this.followupcount);
            dest.writeInt(this.followup);
            dest.writeParcelable(this.stat, flags);
            dest.writeString(this.address);
            dest.writeString(this.phone);
            dest.writeString(this.card);
            dest.writeString(this.birthday);
            dest.writeString(this.addressextend);
            dest.writeInt(this.sex);
            dest.writeString(this.uuid);
            dest.writeLong(this.createtime);
            dest.writeString(this.py);
            dest.writeInt(this.isplan);
            dest.writeInt(this.ishasarchives);
            dest.writeList(this.groups);
        }

        public DataEntity() {
        }

        private DataEntity(Parcel in) {
            this._id = in.readString();
            this.duid = in.readInt();
            this.dname = in.readString();
            this.puid = in.readInt();
            this.name = in.readString();
            this.province = in.readString();
            this.city = in.readString();
            this.region = in.readString();
            this.provincename = in.readString();
            this.cityname = in.readString();
            this.regionname = in.readString();
            this.firstuid = in.readInt();
            this.relationship = in.readInt();
            this.createTime = in.readLong();
            this.focus = in.readInt();
            this.followupcount = in.readInt();
            this.followup = in.readInt();
            this.stat = in.readParcelable(StatEntity.class.getClassLoader());
            this.address = in.readString();
            this.phone = in.readString();
            this.card = in.readString();
            this.birthday = in.readString();
            this.addressextend = in.readString();
            this.sex = in.readInt();
            this.uuid = in.readString();
            this.createtime = in.readLong();
            this.py = in.readString();
            this.isplan = in.readInt();
            this.ishasarchives = in.readInt();
            this.groups = new ArrayList<String>();
            in.readList(this.groups, ArrayList.class.getClassLoader());
        }

        public static final Parcelable.Creator<DataEntity> CREATOR = new Parcelable.Creator<DataEntity>() {
            public DataEntity createFromParcel(Parcel source) {
                return new DataEntity(source);
            }

            public DataEntity[] newArray(int size) {
                return new DataEntity[size];
            }
        };
    }
}
