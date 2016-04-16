package com.android.doctor.ui.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.helper.DateUtils;
import com.android.doctor.interf.OnListItemClickListener;
import com.android.doctor.model.PlanList;

/**
 * Created by Yong on 2016-02-14.
 */
public class VisitPlanViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    /*
    * use layout R.layout.item.patient
    */
    TextView mTvTitle;
    TextView mTvNameDate;
    TextView mTvState;

    private OnListItemClickListener itemClickListener;

    public VisitPlanViewHolder(View view){
        super(view);
        initView(view);
    }

    public void initView(View view) {
        mTvTitle = (TextView) view.findViewById(R.id.tv_title);
        mTvNameDate = (TextView) view.findViewById(R.id.tv_name_date);
        mTvState = (TextView) view.findViewById(R.id.tv_state);
        view.setOnClickListener(this);
    }

    public void setViewData(PlanList.DataEntity obj) {
        if (obj == null) return;
        mTvTitle.setText(obj.getPlanname());
        String text = obj.getPname() + " | " + DateUtils.getDateString(obj.getCreateTime());
        mTvNameDate.setText(text);
        int stat = obj.getStatus();
        mTvState.setText(stat == 1 ? "执行中" : stat == 2 ? "已完成" : "未创建");
    }

    public void setItemClickListener(OnListItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    @Override
    public void onClick(View v) {
        if (itemClickListener != null) {
            itemClickListener.onItemClick(getAdapterPosition(), v);
        }
    }
}
