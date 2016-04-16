package com.android.doctor.ui.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.doctor.interf.OnListItemClickListener;

/**
 * Created by Yong on 2016-02-14.
 */
public class DoctorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    /*
    * use layout R.layout.item.patient
    */
    ImageView mImgAvatar;
    TextView mTvName;
    TextView mTvDiagnose;
    TextView mTvInfo;
    TextView mTvIcon;

    private OnListItemClickListener itemClickListener;

    public DoctorViewHolder(View view){
        super(view);
        initView(view);
    }

    public void initView(View view) {
        view.setOnClickListener(this);
    }

    public void fillUI(Object pro) {

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
