package com.android.doctor.ui.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.doctor.interf.OnListItemClickListener;

/**
 * Created by Yong on 2016-02-14.
 */
public class PageItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private OnListItemClickListener itemClickListener;

    public PageItemViewHolder(View view){
        super(view);
    }

    public void initView(View view) {

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
