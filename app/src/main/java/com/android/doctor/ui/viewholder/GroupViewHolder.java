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
public class GroupViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    /***
    ** 我创建的群 R.layout.group_item
    ** 搜索群 R.layout.group_search_item
    **/

    /**头像*/
    ImageView mAvatar;
    /**名称*/
    TextView name_tv;
    /**账号*/
    TextView account;
    /**选择状态*/
    CheckBox checkBox;

    private OnListItemClickListener itemClickListener;

    public GroupViewHolder(View view){
        super(view);
        initView(view);
        view.setOnClickListener(this);
    }

    public void initView(View view) {
        mAvatar = (ImageView) view.findViewById(R.id.img_avatar);
        name_tv = (TextView) view.findViewById(R.id.tv_group_name);
        //account = (TextView) view.findViewById(R.id.account);
        //checkBox = (CheckBox) view.findViewById(R.id.contactitem_select_cb);
    }

    public void fillUI(ECContacts contacts) {
        name_tv.setText(contacts.getNickname());
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
