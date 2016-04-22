package com.android.doctor.model;

import java.util.List;

/**
 * Created by Yong on 2016/4/18.
 */
public class AdjustPlanParam {

    private int pid;
    private String ref_tplid;
    private List<PlanDeta.DataEntity> tpldetails;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public List<PlanDeta.DataEntity> getTpldetails() {
        return tpldetails;
    }

    public void setTpldetails(List<PlanDeta.DataEntity> tpldetails) {
        this.tpldetails = tpldetails;
    }

    public String getRef_tplid() {
        return ref_tplid;
    }

    public void setRef_tplid(String ref_tplid) {
        this.ref_tplid = ref_tplid;
    }
}
