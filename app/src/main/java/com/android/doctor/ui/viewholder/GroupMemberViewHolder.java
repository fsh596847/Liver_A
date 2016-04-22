package com.android.doctor.ui.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.interf.OnListItemClickListener;
import com.android.doctor.interf.OnListItemSlideListener;
import com.android.doctor.ui.chat.GroupMemberActivity;
import com.android.doctor.ui.widget.BothSlideLayout;
import com.yuntongxun.kitsdk.ui.group.model.ECContacts;
import com.yuntongxun.kitsdk.utils.LogUtil;

/**
 * Created by Yong on 2016-02-14.
 */
public class GroupMemberViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener
        ,OnListItemSlideListener, BothSlideLayout.OnSlideListener {
    /***
    ** 布局文件 R.layout.group_item
    **/

    /**头像*/
    ImageView mLabel;
    /**头像*/
    ImageView mAvatar;
    /**名称*/
    TextView name_tv;
    /**账号*/
    TextView desc;
    /**选择状态*/
    CheckBox checkBox;

    TextView tvDelete;

    BothSlideLayout slideLayout;

    GroupMemberActivity aty;

    private OnListItemClickListener itemClickListener;

    public GroupMemberViewHolder(View view, GroupMemberActivity aty){
        super(view);
        initView(view);
        this.aty = aty;
    }

    public void initView(View view) {
        mLabel = (ImageView)view.findViewById(R.id.img_delete);
        mAvatar = (ImageView) view.findViewById(R.id.img_avatar);
        name_tv = (TextView) view.findViewById(R.id.tv_member_name);
        desc = (TextView) view.findViewById(R.id.tv_member_desc);
        tvDelete = (TextView)view.findViewById(R.id.tv_delete);
        slideLayout = (BothSlideLayout) view.findViewById(R.id.slide_layout);
        slideLayout.setOnSlideListener(this);
        mLabel.setOnClickListener(this);
        tvDelete.setOnClickListener(this);
        view.setOnClickListener(this);
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
        int id = v.getId();
        if (itemClickListener != null) {
            itemClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    /**
     * none = 0
     * left -1
     * right 1
     **/
    @Override
    public void onItemSlide(int state) {
        if (state == 0) {
            slideLayout.closeSlide();
        } else if (state == 1) {
            slideLayout.slideOpenLeftView();
        } else if (state == -1) {
            slideLayout.slideOpenRightView();
        }
    }

    @Override
    public void onSlide(int state) {
        LogUtil.d(LogUtil.getLogUtilsTag(GroupMemberViewHolder.class), "" + state);
        aty.getmTvEdit().setText(state == 0 ? "编辑" : "完成");
    }
}
