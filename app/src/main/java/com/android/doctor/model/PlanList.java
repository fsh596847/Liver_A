package com.android.doctor.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yong on 2016/4/15.
 */
public class PlanList implements Parcelable {

    private List<DataEntity> data;

    public List<DataEntity> getData() {
        return data;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public static class DataEntity implements Parcelable {
        private String _id;
        private String planname;
        private int puid;
        private String puuid;
        private String pname;
        private int duid;
        private String dname;
        private String disease;
        private String maindiagnose;
        private String otherdiagnose;
        private String treatmentmodality;
        private int status;
        private int sendstatus;
        private String followtype;
        private String tplid;
        private String ref_tplid;
        private int isaccept;
        private long time;
        private int lastmonth;
        private String lastpubtime;
        private long lastruntime;
        private String updlasttime;
        private String type;
        private String hosid;
        private long createTime;
        private int pid;
        private List<String> months;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getPlanname() {
            return planname;
        }

        public void setPlanname(String planname) {
            this.planname = planname;
        }

        public int getPuid() {
            return puid;
        }

        public void setPuid(int puid) {
            this.puid = puid;
        }

        public Object getPuuid() {
            return puuid;
        }

        public void setPuuid(String puuid) {
            this.puuid = puuid;
        }

        public String getPname() {
            return pname;
        }

        public void setPname(String pname) {
            this.pname = pname;
        }

        public int getDuid() {
            return duid;
        }

        public void setDuid(int duid) {
            this.duid = duid;
        }

        public String getDname() {
            return dname;
        }

        public void setDname(String dname) {
            this.dname = dname;
        }

        public String getDisease() {
            return disease;
        }

        public void setDisease(String disease) {
            this.disease = disease;
        }

        public String getMaindiagnose() {
            return maindiagnose;
        }

        public void setMaindiagnose(String maindiagnose) {
            this.maindiagnose = maindiagnose;
        }

        public String getOtherdiagnose() {
            return otherdiagnose;
        }

        public void setOtherdiagnose(String otherdiagnose) {
            this.otherdiagnose = otherdiagnose;
        }

        public String getTreatmentmodality() {
            return treatmentmodality;
        }

        public void setTreatmentmodality(String treatmentmodality) {
            this.treatmentmodality = treatmentmodality;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getSendstatus() {
            return sendstatus;
        }

        public void setSendstatus(int sendstatus) {
            this.sendstatus = sendstatus;
        }

        public String getFollowtype() {
            return followtype;
        }

        public void setFollowtype(String followtype) {
            this.followtype = followtype;
        }

        public String getTplid() {
            return tplid;
        }

        public void setTplid(String tplid) {
            this.tplid = tplid;
        }

        public String getRef_tplid() {
            return ref_tplid;
        }

        public void setRef_tplid(String ref_tplid) {
            this.ref_tplid = ref_tplid;
        }

        public int getIsaccept() {
            return isaccept;
        }

        public void setIsaccept(int isaccept) {
            this.isaccept = isaccept;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public int getLastmonth() {
            return lastmonth;
        }

        public void setLastmonth(int lastmonth) {
            this.lastmonth = lastmonth;
        }

        public Object getLastpubtime() {
            return lastpubtime;
        }

        public void setLastpubtime(String lastpubtime) {
            this.lastpubtime = lastpubtime;
        }

        public long getLastruntime() {
            return lastruntime;
        }

        public void setLastruntime(long lastruntime) {
            this.lastruntime = lastruntime;
        }

        public Object getUpdlasttime() {
            return updlasttime;
        }

        public void setUpdlasttime(String updlasttime) {
            this.updlasttime = updlasttime;
        }

        public Object getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Object getHosid() {
            return hosid;
        }

        public void setHosid(String hosid) {
            this.hosid = hosid;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public List<?> getMonths() {
            return months;
        }

        public void setMonths(List<String> months) {
            this.months = months;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this._id);
            dest.writeString(this.planname);
            dest.writeInt(this.puid);
            dest.writeString(this.puuid);
            dest.writeString(this.pname);
            dest.writeInt(this.duid);
            dest.writeString(this.dname);
            dest.writeString(this.disease);
            dest.writeString(this.maindiagnose);
            dest.writeString(this.otherdiagnose);
            dest.writeString(this.treatmentmodality);
            dest.writeInt(this.status);
            dest.writeInt(this.sendstatus);
            dest.writeString(this.followtype);
            dest.writeString(this.tplid);
            dest.writeString(this.ref_tplid);
            dest.writeInt(this.isaccept);
            dest.writeLong(this.time);
            dest.writeInt(this.lastmonth);
            dest.writeString(this.lastpubtime);
            dest.writeLong(this.lastruntime);
            dest.writeString(this.updlasttime);
            dest.writeString(this.type);
            dest.writeString(this.hosid);
            dest.writeLong(this.createTime);
            dest.writeInt(this.pid);
            dest.writeList(this.months);
        }

        public DataEntity() {
        }

        private DataEntity(Parcel in) {
            this._id = in.readString();
            this.planname = in.readString();
            this.puid = in.readInt();
            this.puuid = in.readString();
            this.pname = in.readString();
            this.duid = in.readInt();
            this.dname = in.readString();
            this.disease = in.readString();
            this.maindiagnose = in.readString();
            this.otherdiagnose = in.readString();
            this.treatmentmodality = in.readString();
            this.status = in.readInt();
            this.sendstatus = in.readInt();
            this.followtype = in.readString();
            this.tplid = in.readString();
            this.ref_tplid = in.readString();
            this.isaccept = in.readInt();
            this.time = in.readLong();
            this.lastmonth = in.readInt();
            this.lastpubtime = in.readString();
            this.lastruntime = in.readLong();
            this.updlasttime = in.readString();
            this.type = in.readString();
            this.hosid = in.readString();
            this.createTime = in.readLong();
            this.pid = in.readInt();
            this.months = new ArrayList<String>();
            in.readList(this.months, List.class.getClassLoader());
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(data);
    }

    public PlanList() {
    }

    private PlanList(Parcel in) {
        in.readTypedList(data, DataEntity.CREATOR);
    }

    public static final Parcelable.Creator<PlanList> CREATOR = new Parcelable.Creator<PlanList>() {
        public PlanList createFromParcel(Parcel source) {
            return new PlanList(source);
        }

        public PlanList[] newArray(int size) {
            return new PlanList[size];
        }
    };
}
