package com.android.doctor.ui.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.interf.OnListItemClickListener;
import com.android.doctor.model.TempPlanList;
import com.android.doctor.ui.base.SingleTapConfirm;

/**
 * Created by Yong on 2016-02-14.
 */
public class TPlanViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    /*
    * use layout R.layout.item.patient
    */
    TextView mTvTitle;
    TextView mTvNameDate;
    TextView mTvState;
    private GestureDetector gestureDetector;
    private OnListItemClickListener itemClickListener;

    public TPlanViewHolder(View view, Context c){
        super(view);
        gestureDetector = new GestureDetector(c, new SingleTapConfirm());
        initView(view);
    }

    public void initView(View view) {
        mTvTitle = (TextView) view.findViewById(R.id.tv_title);
        mTvNameDate = (TextView) view.findViewById(R.id.tv_name_date);
        mTvState = (TextView) view.findViewById(R.id.tv_import_state);
        view.findViewById(R.id.tv_delete).setOnClickListener(this);
        view.setOnClickListener(this);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (gestureDetector.onTouchEvent(event)) {
                    onClick(v);
                    return true;
                }
                return false;
            }
        });
    }

    public void setViewData(TempPlanList.TplsEntity obj) {
        if (obj == null) return;
        mTvTitle.setText(obj.getName());
        String text = obj.getDiag() + "  " + obj.getTreat();
        mTvNameDate.setText(text);
        String tx = "使用次数\n    " + obj.getUsecount();
        mTvState.setText(tx);
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
