package com.android.doctor.model;

/**
 * Created by Yong on 2016/4/16.
 */
public class UpdatePlanDetaParam {
    private int uid;
    private String username;
    private int hisid;
    private String hisname;
    private String name;
    private String diag;
    private String treat;
    private PlanDeta plan;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getHisid() {
        return hisid;
    }

    public void setHisid(int hisid) {
        this.hisid = hisid;
    }

    public String getHisname() {
        return hisname;
    }

    public void setHisname(String hisname) {
        this.hisname = hisname;
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

    public PlanDeta getPlan() {
        return plan;
    }

    public void setPlan(PlanDeta plan) {
        this.plan = plan;
    }
}
