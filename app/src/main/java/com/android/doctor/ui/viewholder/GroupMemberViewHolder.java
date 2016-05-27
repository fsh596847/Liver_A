package com.android.doctor.ui.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.interf.OnListItemClickListener;
import com.android.doctor.interf.OnListItemSlideListener;
import com.android.doctor.model.GroupMemberList;
import com.android.doctor.ui.base.SingleTapConfirm;
import com.android.doctor.ui.chat.GroupCardActivity;
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

    private GestureDetector gestureDetector;

    boolean bIsOwner;

    private OnListItemClickListener itemClickListener;

    public GroupMemberViewHolder(View view, GroupMemberActivity aty){
        super(view);
        initView(view);
        this.aty = aty;
        gestureDetector = new GestureDetector(aty, new SingleTapConfirm());
    }

    public void initView(View view) {
        mLabel = (ImageView)view.findViewById(R.id.img_delete);
        mAvatar = (ImageView) view.findViewById(R.id.img_avatar);
        name_tv = (TextView) view.findViewById(R.id.tv_member_name);
        desc = (TextView) view.findViewById(R.id.tv_member_desc);
        tvDelete = (TextView)view.findViewById(R.id.tv_delete);
        slideLayout = (BothSlideLayout) view.findViewById(R.id.slide_layout);

        setListener(view);
    }

    private void setListener(View view) {
        slideLayout.setOnSlideListener(this);
        mLabel.setOnClickListener(this);
        tvDelete.setOnClickListener(this);

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (gestureDetector.onTouchEvent(event)) {
                    onClick(v);
                    return true;
                }
                return false;
            }
        });
    }

    public void setViewData(GroupMemberList.GroupMemberEntity mem) {
        name_tv.setText(mem.getDisplay());
        String ownerType = "0".equals(mem.getOwnertype()) ? "医生 " : "患者 ";
        bIsOwner = mem.isManager();
        String tx = ownerType + (bIsOwner ? "管理员" : "");
        desc.setText(tx);
        if (bIsOwner) {
            slideLayout.setCanSlide(false);
        } else {
            slideLayout.setCanSlide(true);
        }
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
        if (bIsOwner) return;
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
