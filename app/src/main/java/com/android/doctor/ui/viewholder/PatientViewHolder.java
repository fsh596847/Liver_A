package com.android.doctor.ui.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.interf.OnListItemClickListener;
import com.android.doctor.model.HosPaitentList;

/**
 * Created by Yong on 2016-02-14.
 */
public class PatientViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    /*
    * use layout R.layout.item.patient
    */
    ImageView mImgAvatar;
    TextView mTvName;
    TextView mTvDiagType;
    TextView mTvSeeDoctor;
    TextView mTvLevHos;
    TextView mTvAddress;
    TextView mState;

    private OnListItemClickListener itemClickListener;

    public PatientViewHolder(View view){
        super(view);
        initView(view);
    }

    public void initView(View view) {
        view.setOnClickListener(this);
        mImgAvatar = (ImageView) view.findViewById(R.id.avatar_view);
        mTvName = (TextView) view.findViewById(R.id.tv_username);
        mTvDiagType = (TextView) view.findViewById(R.id.tv_dis_type);
        mTvSeeDoctor = (TextView) view.findViewById(R.id.tv_see_doctor);
        mTvLevHos = (TextView) view.findViewById(R.id.tv_leave_hospital);
        mTvAddress = (TextView) view.findViewById(R.id.tv_address);
        mState = (TextView) view.findViewById(R.id.tv_import_state);
    }

    public void setViewData(HosPaitentList.HosPatientEntity e) {
        if (e != null) {
            if (mImgAvatar != null)
            mImgAvatar.setImageResource(e.getSex() == 0 ? R.drawable.patient_photo_f : R.drawable.patient_photo_m);
            mTvName.setText(e.getName());
            mTvDiagType.setText(e.getKw());
            mTvSeeDoctor.setText("就诊：" + e.getVisitdate());
            mTvLevHos.setText("出院："+e.getOutdate());
            mTvAddress.setText("地址："+e.getAddress());
            boolean isImported = e.getSync() == 0;
            mState.setText(isImported ? "已导入" : "导入");
            if (!isImported) {
                mState.setOnClickListener(this);
            }
        }
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
