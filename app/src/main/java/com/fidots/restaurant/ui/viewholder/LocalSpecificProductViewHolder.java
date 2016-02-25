package com.fidots.restaurant.ui.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fidots.restaurant.interf.OnAdapterItemClickListener;
import com.fidots.restaurant.models.SpecificProduct;

/**
 * Created by Yong on 2016-02-14.
 */
public class LocalSpecificProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private OnAdapterItemClickListener itemClickListener;

    public LocalSpecificProductViewHolder(View view){
        super(view);
        initView(view);
    }

    public void initView(View view) {
        view.setOnClickListener(this);
    }

    public void fillUI(SpecificProduct pro) {

    }

    public void setItemClickListener(OnAdapterItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    @Override
    public void onClick(View v) {
        if (itemClickListener != null) {
            itemClickListener.OnItemClick(getAdapterPosition(), v);
        }
    }
}
