package com.android.doctor.model;

/**
 * Created by Yong on 2016/4/19.
 */
public class DSendInviteParam {
    private String dname;
    private String duid;
    private String hosid;
    private String pname;
    private String phone;
    private String pcard;
    private String puuid;
    private String type;

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getPuuid() {
        return puuid;
    }

    public void setPuuid(String puuid) {
        this.puuid = puuid;
    }

    public String getPcard() {
        return pcard;
    }

    public void setPcard(String pcard) {
        this.pcard = pcard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getHosid() {
        return hosid;
    }

    public void setHosid(String hosid) {
        this.hosid = hosid;
    }

    public String getDuid() {
        return duid;
    }

    public void setDuid(String duid) {
        this.duid = duid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
