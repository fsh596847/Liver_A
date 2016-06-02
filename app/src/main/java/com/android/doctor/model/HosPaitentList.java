package com.android.doctor.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yong on 2016/4/19.
 */
public class HosPaitentList {

    private int total;
    /**
     * _id : 571195b2090c5ed995418a47
     * visitid : 2425767
     * hospitalPatientId : 2425767
     * puuid : 9e01d5f9-8eb6-44d0-a7c1-33c3f98f500b
     * name : 郝江
     * py : hj
     * sex : 1
     * phone : 13903540522
     * hosid : 7
     * pid : 0
     * patientid : 66117
     * casecode : 164854
     * birthday : 1975-04-01
     * card : 142401197504010636
     * mrcode :
     * visitdate : 2015-01-19
     * outdate : 2015-03-05 12:43:08
     * province : 山西
     * city : 晋中
     * county : 榆次
     * address : 安宁街顺驰小区2-1-502
     * deptid : 18
     * deptname : 肝病五科
     * diagname : 肝炎肝硬化（活动性、失代偿性）
     * kw : 肝硬化
     * kw1 : ["活动性","失代偿性"]
     * kw2 : []
     * incount : 2
     * fee : 40210.92
     * desc :
     * cisuuid : {"sourcetype":2,"datatype":"出院","cispk":"2425767","hispk":"2425767","lispk":"2425767","visitcard":"164854","mrcode":""}
     * doctors : [{"duid":122,"level":0},{"duid":124,"level":1},{"duid":342,"level":2},{"duid":122,"level":3}]
     * createtime : 1460770226854
     * source : cis
     */

    private List<HosPatientEntity> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<HosPatientEntity> getData() {
        return data;
    }

    public void setData(List<HosPatientEntity> data) {
        this.data = data;
    }

    public static class HosPatientEntity implements Parcelable {
        private String _id;
        private int visitid;
        private int hospitalPatientId;
        private String puuid;
        private String name;
        private String py;
        private int sex;
        private String phone;
        private int hosid;
        private int pid;
        private String puid;
        private int patientid;
        private String casecode;
        private String birthday;
        private String card;
        private String mrcode;
        private String visitdate;
        private String outdate;
        private String province;
        private String city;
        private String county;
        private String address;
        private int deptid;
        private String deptname;
        private String diagname;
        private String kw;
        private int incount;
        private String fee;
        private String desc;
        /**
         * sourcetype : 2
         * datatype : 出院
         * cispk : 2425767
         * hispk : 2425767
         * lispk : 2425767
         * visitcard : 164854
         * mrcode :
         */

        private CisuuidEntity cisuuid;
        private long createtime;
        private String source;
        private int sync;
        private List<String> kw1;
        private List<String> kw2;

        public int getSync() {
            return sync;
        }

        public void setSync(int sync) {
            this.sync = sync;
        }

        /**
         * duid : 122
         * level : 0
         */


        private List<DoctorsEntity> doctors;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public int getVisitid() {
            return visitid;
        }

        public void setVisitid(int visitid) {
            this.visitid = visitid;
        }

        public int getHospitalPatientId() {
            return hospitalPatientId;
        }

        public void setHospitalPatientId(int hospitalPatientId) {
            this.hospitalPatientId = hospitalPatientId;
        }

        public String getPuid() {
            return puid;
        }

        public void setPuid(String puid) {
            this.puid = puid;
        }

        public String getPuuid() {
            return puuid;
        }

        public void setPuuid(String puuid) {
            this.puuid = puuid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPy() {
            return py;
        }

        public void setPy(String py) {
            this.py = py;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getHosid() {
            return hosid;
        }

        public void setHosid(int hosid) {
            this.hosid = hosid;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public int getPatientid() {
            return patientid;
        }

        public void setPatientid(int patientid) {
            this.patientid = patientid;
        }

        public String getCasecode() {
            return casecode;
        }

        public void setCasecode(String casecode) {
            this.casecode = casecode;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getCard() {
            return card;
        }

        public void setCard(String card) {
            this.card = card;
        }

        public String getMrcode() {
            return mrcode;
        }

        public void setMrcode(String mrcode) {
            this.mrcode = mrcode;
        }

        public String getVisitdate() {
            return visitdate;
        }

        public void setVisitdate(String visitdate) {
            this.visitdate = visitdate;
        }

        public String getOutdate() {
            return outdate;
        }

        public void setOutdate(String outdate) {
            this.outdate = outdate;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCounty() {
            return county;
        }

        public void setCounty(String county) {
            this.county = county;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getDeptid() {
            return deptid;
        }

        public void setDeptid(int deptid) {
            this.deptid = deptid;
        }

        public String getDeptname() {
            return deptname;
        }

        public void setDeptname(String deptname) {
            this.deptname = deptname;
        }

        public String getDiagname() {
            return diagname;
        }

        public void setDiagname(String diagname) {
            this.diagname = diagname;
        }

        public String getKw() {
            return kw;
        }

        public void setKw(String kw) {
            this.kw = kw;
        }

        public int getIncount() {
            return incount;
        }

        public void setIncount(int incount) {
            this.incount = incount;
        }

        public String getFee() {
            return fee;
        }

        public void setFee(String fee) {
            this.fee = fee;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public CisuuidEntity getCisuuid() {
            return cisuuid;
        }

        public void setCisuuid(CisuuidEntity cisuuid) {
            this.cisuuid = cisuuid;
        }

        public long getCreatetime() {
            return createtime;
        }

        public void setCreatetime(long createtime) {
            this.createtime = createtime;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public List<String> getKw1() {
            return kw1;
        }

        public void setKw1(List<String> kw1) {
            this.kw1 = kw1;
        }

        public List<String> getKw2() {
            return kw2;
        }

        public void setKw2(List<String> kw2) {
            this.kw2 = kw2;
        }

        public List<DoctorsEntity> getDoctors() {
            return doctors;
        }

        public void setDoctors(List<DoctorsEntity> doctors) {
            this.doctors = doctors;
        }

        public static class CisuuidEntity implements Parcelable {
            private int sourcetype;
            private String datatype;
            private String cispk;
            private String hispk;
            private String lispk;
            private String visitcard;
            private String mrcode;

            public int getSourcetype() {
                return sourcetype;
            }

            public void setSourcetype(int sourcetype) {
                this.sourcetype = sourcetype;
            }

            public String getDatatype() {
                return datatype;
            }

            public void setDatatype(String datatype) {
                this.datatype = datatype;
            }

            public String getCispk() {
                return cispk;
            }

            public void setCispk(String cispk) {
                this.cispk = cispk;
            }

            public String getHispk() {
                return hispk;
            }

            public void setHispk(String hispk) {
                this.hispk = hispk;
            }

            public String getLispk() {
                return lispk;
            }

            public void setLispk(String lispk) {
                this.lispk = lispk;
            }

            public String getVisitcard() {
                return visitcard;
            }

            public void setVisitcard(String visitcard) {
                this.visitcard = visitcard;
            }

            public String getMrcode() {
                return mrcode;
            }

            public void setMrcode(String mrcode) {
                this.mrcode = mrcode;
            }


            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.sourcetype);
                dest.writeString(this.datatype);
                dest.writeString(this.cispk);
                dest.writeString(this.hispk);
                dest.writeString(this.lispk);
                dest.writeString(this.visitcard);
                dest.writeString(this.mrcode);
            }

            public CisuuidEntity() {
            }

            private CisuuidEntity(Parcel in) {
                this.sourcetype = in.readInt();
                this.datatype = in.readString();
                this.cispk = in.readString();
                this.hispk = in.readString();
                this.lispk = in.readString();
                this.visitcard = in.readString();
                this.mrcode = in.readString();
            }

            public static final Parcelable.Creator<CisuuidEntity> CREATOR = new Parcelable.Creator<CisuuidEntity>() {
                public CisuuidEntity createFromParcel(Parcel source) {
                    return new CisuuidEntity(source);
                }

                public CisuuidEntity[] newArray(int size) {
                    return new CisuuidEntity[size];
                }
            };
        }

        public static class DoctorsEntity implements Parcelable {
            private String duid;
            private String level;

            public String getDuid() {
                return duid;
            }

            public void setDuid(String duid) {
                this.duid = duid;
            }

            public String getLevel() {
                return level;
            }

            public void setLevel(String level) {
                this.level = level;
            }


            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.duid);
                dest.writeString(this.level);
            }

            public DoctorsEntity() {
            }

            private DoctorsEntity(Parcel in) {
                this.duid = in.readString();
                this.level = in.readString();
            }

            public static final Parcelable.Creator<DoctorsEntity> CREATOR = new Parcelable.Creator<DoctorsEntity>() {
                public DoctorsEntity createFromParcel(Parcel source) {
                    return new DoctorsEntity(source);
                }

                public DoctorsEntity[] newArray(int size) {
                    return new DoctorsEntity[size];
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
            dest.writeInt(this.visitid);
            dest.writeInt(this.hospitalPatientId);
            dest.writeString(this.puuid);
            dest.writeString(this.name);
            dest.writeString(this.py);
            dest.writeInt(this.sex);
            dest.writeString(this.phone);
            dest.writeInt(this.hosid);
            dest.writeInt(this.pid);
            dest.writeString(this.puid);
            dest.writeInt(this.patientid);
            dest.writeString(this.casecode);
            dest.writeString(this.birthday);
            dest.writeString(this.card);
            dest.writeString(this.mrcode);
            dest.writeString(this.visitdate);
            dest.writeString(this.outdate);
            dest.writeString(this.province);
            dest.writeString(this.city);
            dest.writeString(this.county);
            dest.writeString(this.address);
            dest.writeInt(this.deptid);
            dest.writeString(this.deptname);
            dest.writeString(this.diagname);
            dest.writeString(this.kw);
            dest.writeInt(this.incount);
            dest.writeString(this.fee);
            dest.writeString(this.desc);
            dest.writeParcelable(this.cisuuid, 0);
            dest.writeLong(this.createtime);
            dest.writeString(this.source);
            dest.writeList(this.kw1);
            dest.writeList(this.kw2);
            dest.writeTypedList(doctors);
        }

        public HosPatientEntity() {
        }

        private HosPatientEntity(Parcel in) {
            this._id = in.readString();
            this.visitid = in.readInt();
            this.hospitalPatientId = in.readInt();
            this.puuid = in.readString();
            this.name = in.readString();
            this.py = in.readString();
            this.sex = in.readInt();
            this.phone = in.readString();
            this.hosid = in.readInt();
            this.pid = in.readInt();
            this.puid = in.readString();
            this.patientid = in.readInt();
            this.casecode = in.readString();
            this.birthday = in.readString();
            this.card = in.readString();
            this.mrcode = in.readString();
            this.visitdate = in.readString();
            this.outdate = in.readString();
            this.province = in.readString();
            this.city = in.readString();
            this.county = in.readString();
            this.address = in.readString();
            this.deptid = in.readInt();
            this.deptname = in.readString();
            this.diagname = in.readString();
            this.kw = in.readString();
            this.incount = in.readInt();
            this.fee = in.readString();
            this.desc = in.readString();
            this.cisuuid = in.readParcelable(CisuuidEntity.class.getClassLoader());
            this.createtime = in.readLong();
            this.source = in.readString();
            this.kw1 = new ArrayList<String>();
            in.readList(this.kw1, List.class.getClassLoader());
            this.kw2 = new ArrayList<String>();
            in.readList(this.kw2, List.class.getClassLoader());
            this.doctors = new ArrayList<>();
            in.readTypedList(doctors, DoctorsEntity.CREATOR);
        }

        public static final Parcelable.Creator<HosPatientEntity> CREATOR = new Parcelable.Creator<HosPatientEntity>() {
            public HosPatientEntity createFromParcel(Parcel source) {
                return new HosPatientEntity(source);
            }

            public HosPatientEntity[] newArray(int size) {
                return new HosPatientEntity[size];
            }
        };
    }
}
