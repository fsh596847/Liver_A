package com.android.doctor.ui.viewholder;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.app.AppContext;
import com.android.doctor.interf.OnListItemClickListener;
import com.android.doctor.model.Constants;
import com.android.doctor.model.SuggClassList;
import com.android.doctor.app.DataCacheManager;
import com.android.doctor.rest.RestClient;

/**
 * Created by Yong on 2016-02-14.
 */
public class KSubjectViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    /*
    * use layout R.layout.item.patient
    */
    TextView textView;
    ImageView imageView;
    ImageButton imgBtn;

    private OnListItemClickListener itemClickListener;

    public KSubjectViewHolder(View view){
        super(view);
        initView(view);
        view.setOnClickListener(this);
    }

    public void initView(View view) {
        textView = (TextView) view.findViewById(R.id.tv_text);
        imageView = (ImageView) view.findViewById(R.id.img_sub);
        imgBtn = (ImageButton) view.findViewById(R.id.img_subs);
        if (imgBtn != null) imgBtn.setOnClickListener(this);
    }

    public void setViewData(SuggClassList.SuggEntity o) {
        if (o == null) return;
        textView.setText(o.getName());
        if (!TextUtils.isEmpty(o.getImgurl())) {
            AppContext.getImageLoader().displayImage(RestClient.getImgURL(o.getImgurl(), 0, 0), imageView);
        }
        if (imgBtn != null) {
            SuggClassList.SuggEntity sg = DataCacheManager.getInstance().findSubjectByCode(o.getCode());
            if (sg != null) {
                imgBtn.setImageResource(R.drawable.ic_btn_nike_blue);
                imgBtn.setTag(Constants.KBASE_SUBJECT_STATE_SUBSCRIBED);
            } else {
                imgBtn.setImageResource(R.drawable.btn_add_gray);
                imgBtn.setTag(Constants.KBASE_SUBJECT_STATE_UNSUBSCRIBED);
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
