package com.android.doctor.model;

import java.util.List;

/**
 * Created by Yong on 2016/4/18.
 */
public class CrtPlanParam {

    private String uid;
    private String username;
    private String hosid;
    private String hosname;
    private String name;
    private String diag;
    private String treat;
    private List<PlanDeta.PlanDetaEntity> plan;

    public List<PlanDeta.PlanDetaEntity> getPlan() {
        return plan;
    }

    public void setPlan(List<PlanDeta.PlanDetaEntity> plan) {
        this.plan = plan;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHosid() {
        return hosid;
    }

    public void setHosid(String hosid) {
        this.hosid = hosid;
    }

    public String getHosname() {
        return hosname;
    }

    public void setHosname(String hosname) {
        this.hosname = hosname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiag() {
        return diag;
    }

    public void setDiag(String diag) {
        this.diag = diag;
    }

    public String getTreat() {
        return treat;
    }

    public void setTreat(String treat) {
        this.treat = treat;
    }
}
