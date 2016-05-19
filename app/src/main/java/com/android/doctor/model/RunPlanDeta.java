package com.android.doctor.model;

import java.util.List;

/**
 * Created by Yong on 2016/5/5.
 */
public class RunPlanDeta {
    private PlanList.PlanBaseEntity plan;
    private int plancount;
    private List<PlanDeta.PlanDetaEntity> data;

    public PlanList.PlanBaseEntity getPlan() {
        return plan;
    }

    public void setPlan(PlanList.PlanBaseEntity plan) {
        this.plan = plan;
    }

    public int getPlancount() {
        return plancount;
    }

    public void setPlancount(int plancount) {
        this.plancount = plancount;
    }

    public List<PlanDeta.PlanDetaEntity> getData() {
        return data;
    }

    public void setData(List<PlanDeta.PlanDetaEntity> data) {
        this.data = data;
    }
}
