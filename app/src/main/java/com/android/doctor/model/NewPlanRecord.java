package com.android.doctor.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Yong on 2016/4/17.
 */
public class NewPlanRecord implements Parcelable {
    private int pid;
    private int duid;
    private int puid;
    private String pname;
    private String planname;
    private String date;
    private int flowMethod;
    private String flowUpResult;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getDuid() {
        return duid;
    }

    public void setDuid(int duid) {
        this.duid = duid;
    }

    public int getPuid() {
        return puid;
    }

    public void setPuid(int puid) {
        this.puid = puid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPlanname() {
        return planname;
    }

    public void setPlanname(String planname) {
        this.planname = planname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getFlowMethod() {
        return flowMethod;
    }

    public void setFlowMethod(int flowMethod) {
        this.flowMethod = flowMethod;
    }

    public String getFlowUpResult() {
        return flowUpResult;
    }

    public void setFlowUpResult(String flowUpResult) {
        this.flowUpResult = flowUpResult;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.pid);
        dest.writeInt(this.duid);
        dest.writeInt(this.puid);
        dest.writeString(this.pname);
        dest.writeString(this.planname);
        dest.writeString(this.date);
        dest.writeInt(this.flowMethod);
        dest.writeString(this.flowUpResult);
    }

    public NewPlanRecord() {
    }

    public void setNewPlanRecord(PlanRecordList.RecordEntity other) {
        this.setPid(other.getPid());
        this.setPlanname(other.getPlanname());
        this.setPname(other.getPname());
        this.setDuid(other.getDuid());
        this.setPuid(other.getPuid());
    }

    public void setNewPlanRecord(PlanList.PlanBaseEntity other) {
        this.setPid(other.getPid());
        this.setPlanname(other.getPlanname());
        this.setPname(other.getPname());
        this.setDuid(other.getDuid());
        this.setPuid(other.getPuid());
    }

    private NewPlanRecord(Parcel in) {
        this.pid = in.readInt();
        this.duid = in.readInt();
        this.puid = in.readInt();
        this.pname = in.readString();
        this.planname = in.readString();
        this.date = in.readString();
        this.flowMethod = in.readInt();
        this.flowUpResult = in.readString();
    }

    public static final Parcelable.Creator<NewPlanRecord> CREATOR = new Parcelable.Creator<NewPlanRecord>() {
        public NewPlanRecord createFromParcel(Parcel source) {
            return new NewPlanRecord(source);
        }

        public NewPlanRecord[] newArray(int size) {
            return new NewPlanRecord[size];
        }
    };
}
