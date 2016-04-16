package com.android.doctor.ui.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.interf.OnListItemClickListener;
import com.yuntongxun.kitsdk.ui.group.model.ECContacts;

/**
 * Created by Yong on 2016-02-14.
 */
public class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    /*
    * use layout R.layout.item.patient
    */
    /**头像*/
    ImageView mAvatar;
    /**名称*/
    TextView name_tv;
    /**账号*/
    TextView account;
    /**选择状态*/
    CheckBox checkBox;

    private OnListItemClickListener itemClickListener;

    public ContactViewHolder(View view){
        super(view);
        initView(view);
        view.setOnClickListener(this);
    }

    public void initView(View view) {
        mAvatar = (ImageView) view.findViewById(R.id.avatar_iv);
        name_tv = (TextView) view.findViewById(R.id.name_tv);
        //account = (TextView) view.findViewById(R.id.account);
        //checkBox = (CheckBox) view.findViewById(R.id.contactitem_select_cb);
    }

    public void fillUI(ECContacts contacts) {
        //name_tv.setText(contacts.getNickname());
        //account.setText(contacts.getContactid());
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
