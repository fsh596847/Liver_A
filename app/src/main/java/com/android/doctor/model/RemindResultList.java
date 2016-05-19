package com.android.doctor.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Yong on 2016/5/4.
 */
public class RemindResultList {

    private int total;
    /**
     * _id : 57296ace94bc305e6c82fc0f
     * rsid : 53
     * puid : 106
     * pname : 王文举
     * duid : 124
     * dname : 郭小青
     * content : 如有以下情况,请及时就医!发热、呕血、黑便、昏睡、持续腹痛
     * hint :
     * remindertype :
     * operate :
     * status : 0
     * pid : 1
     * time : 2016/05/13 00:00:00
     * timestr : 2016/05/13
     * yearMonth : 201605
     * day : 13
     * wholook : 1
     */

    private List<RemindResultEntity> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<RemindResultEntity> getData() {
        return data;
    }

    public void setData(List<RemindResultEntity> data) {
        this.data = data;
    }

    public static class RemindResultEntity implements Parcelable {
        private String _id;
        private int rsid;
        private int puid;
        private String pname;
        private int duid;
        private String dname;
        private String content;
        private String hint;
        private String remindertype;
        private String operate;
        private int status;
        private int pid;
        private String time;
        private String timestr;
        private int yearMonth;
        private int day;
        private int wholook;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public int getRsid() {
            return rsid;
        }

        public void setRsid(int rsid) {
            this.rsid = rsid;
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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getHint() {
            return hint;
        }

        public void setHint(String hint) {
            this.hint = hint;
        }

        public String getRemindertype() {
            return remindertype;
        }

        public void setRemindertype(String remindertype) {
            this.remindertype = remindertype;
        }

        public String getOperate() {
            return operate;
        }

        public void setOperate(String operate) {
            this.operate = operate;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getTimestr() {
            return timestr;
        }

        public void setTimestr(String timestr) {
            this.timestr = timestr;
        }

        public int getYearMonth() {
            return yearMonth;
        }

        public void setYearMonth(int yearMonth) {
            this.yearMonth = yearMonth;
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public int getWholook() {
            return wholook;
        }

        public void setWholook(int wholook) {
            this.wholook = wholook;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this._id);
            dest.writeInt(this.rsid);
            dest.writeInt(this.puid);
            dest.writeString(this.pname);
            dest.writeInt(this.duid);
            dest.writeString(this.dname);
            dest.writeString(this.content);
            dest.writeString(this.hint);
            dest.writeString(this.remindertype);
            dest.writeString(this.operate);
            dest.writeInt(this.status);
            dest.writeInt(this.pid);
            dest.writeString(this.time);
            dest.writeString(this.timestr);
            dest.writeInt(this.yearMonth);
            dest.writeInt(this.day);
            dest.writeInt(this.wholook);
        }

        public RemindResultEntity() {
        }

        protected RemindResultEntity(Parcel in) {
            this._id = in.readString();
            this.rsid = in.readInt();
            this.puid = in.readInt();
            this.pname = in.readString();
            this.duid = in.readInt();
            this.dname = in.readString();
            this.content = in.readString();
            this.hint = in.readString();
            this.remindertype = in.readString();
            this.operate = in.readString();
            this.status = in.readInt();
            this.pid = in.readInt();
            this.time = in.readString();
            this.timestr = in.readString();
            this.yearMonth = in.readInt();
            this.day = in.readInt();
            this.wholook = in.readInt();
        }

        public static final Parcelable.Creator<RemindResultEntity> CREATOR = new Parcelable.Creator<RemindResultEntity>() {
            @Override
            public RemindResultEntity createFromParcel(Parcel source) {
                return new RemindResultEntity(source);
            }

            @Override
            public RemindResultEntity[] newArray(int size) {
                return new RemindResultEntity[size];
            }
        };
    }
}
