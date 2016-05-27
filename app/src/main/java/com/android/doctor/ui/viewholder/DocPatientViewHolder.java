package com.android.doctor.ui.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.interf.OnListItemClickListener;
import com.android.doctor.model.PatientList;

import java.util.List;

/**
 * Created by Yong on 2016-02-14.
 */
public class DocPatientViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    /*
    * use layout R.layout.item.patient
    */
    ImageView mImgAvatar;
    TextView mTvName;
    TextView mTvDiagnose;
    TextView mTvInfo;
    TextView mTvIcon;
    ImageView mImgFollowup;
    ImageView mImgArchive;

    private OnListItemClickListener itemClickListener;

    public DocPatientViewHolder(View view){
        super(view);
        initView(view);
    }

    public void initView(View view) {
        view.setOnClickListener(this);
        mImgAvatar = (ImageView) view.findViewById(R.id.avatar_view);
        mTvName = (TextView) view.findViewById(R.id.tv_username);
        mTvDiagnose = (TextView) view.findViewById(R.id.tv_diagnose);
        mTvInfo = (TextView) view.findViewById(R.id.tv_other);
        mTvIcon = (TextView) view.findViewById(R.id.tv_icon);
        mImgFollowup = (ImageView) view.findViewById(R.id.img_followup);
        mImgArchive = (ImageView) view.findViewById(R.id.img_archive);
    }

    public void setViewData(PatientList.DataEntity e) {
        if (e != null) {
            mImgAvatar.setImageResource(e.getSex() == 0 ? R.drawable.patient_photo_f : R.drawable.patient_photo_m);
            mTvName.setText(e.getName());
            List<String> list = e.getGroups();
            if (list != null) {
                String desc = "";
                for (String str: list) {
                    desc = desc + " " + str;
                }
                mTvDiagnose.setText(desc);
            }
            if (mImgFollowup != null) {
                mImgFollowup.setImageResource(e.getIsplan() == 0 ? R.drawable.ic_icon_followup : R.drawable.ic_icon_followup_sel);
            }
            if (mImgArchive!= null) {
                mImgArchive.setImageResource(e.getIshasarchives() == 0 ? R.drawable.ic_icon_archive : R.drawable.ic_icon_archive_sel);
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
