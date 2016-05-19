package com.android.doctor.model;

import java.util.List;

/**
 * Created by Yong on 2016/5/6.
 */
public class TPlanDeta {
    private List<PlanDeta.PlanDetaEntity> tpls;

    public List<PlanDeta.PlanDetaEntity> getTpls() {
        return tpls;
    }

    public void setTpls(List<PlanDeta.PlanDetaEntity> tpls) {
        this.tpls = tpls;
    }
}
