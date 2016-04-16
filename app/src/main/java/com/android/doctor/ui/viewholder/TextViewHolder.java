package com.android.doctor.ui.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.interf.OnListItemClickListener;

/**
 * Created by Yong on 2016-02-14.
 */
public class TextViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    /*
    * use layout R.layout.item.patient
    */
    TextView textView;

    private OnListItemClickListener itemClickListener;

    public TextViewHolder(View view){
        super(view);
        initView(view);
        view.setOnClickListener(this);
    }

    public void initView(View view) {
        textView = (TextView) view.findViewById(R.id.tv_text);
    }

    public void fillUI(String str) {
        textView.setText(str);
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
