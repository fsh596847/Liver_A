package com.android.doctor.ui.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.interf.OnListItemClickListener;
import com.android.doctor.model.ArticleList;

/**
 * Created by Yong on 2016-02-14.
 */
public class ArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    /*
    * use layout R.layout.item.patient
    */
    TextView textView;

    private OnListItemClickListener itemClickListener;

    public ArticleViewHolder(View view){
        super(view);
        initView(view);
        view.setOnClickListener(this);
    }

    public void initView(View view) {
        textView = (TextView) view.findViewById(R.id.tv_text);
    }

    public void setViewData(String str) {
        textView.setText(str);
    }

    public void setViewData(ArticleList.SuggestsEntity o) {
        if (o == null) return;
        textView.setText(o.getTitle());
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
