package com.android.doctor.ui.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.interf.OnListItemClickListener;
import com.android.doctor.model.DoctorList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Yong on 2016-02-14.
 */
public class VisitPatientViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    /*
    * use layout R.layout.item.patient
    */
    @InjectView(R.id.avatar_view)
    ImageView mImgAvatar;
    @InjectView(R.id.tv_username)
    TextView mTvName;
    TextView mTvDiagnose;
    @InjectView(R.id.tv_other)
    TextView mTvInfo;
    TextView mTvIcon;

    private OnListItemClickListener itemClickListener;

    public VisitPatientViewHolder(View view){
        super(view);
        ButterKnife.inject(this, view);
        view.setOnClickListener(this);
    }

    public void setViewData(DoctorList.DoctorEntity entity) {
        mTvName.setText(entity.getUsername());
        int sexRes = "0".equals(entity.getGender()) ? R.drawable.ic_female : R.drawable.ic_male;
        mTvName.setCompoundDrawablesWithIntrinsicBounds(0,0,sexRes,0);
        String str = entity.getDeptname() + "   " + entity.getHosname();
        mTvInfo.setText(str);
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
