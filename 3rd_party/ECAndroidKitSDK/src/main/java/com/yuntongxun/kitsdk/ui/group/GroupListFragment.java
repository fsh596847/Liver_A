/*
 *  Copyright (c) 2013 The CCP project authors. All Rights Reserved.
 *
 *  Use of this source code is governed by a Beijing Speedtong Information Technology Co.,Ltd license
 *  that can be found in the LICENSE file in the root of the web site.
 *
 *   http://www.yuntongxun.com
 *
 *  An additional intellectual property rights grant can be found
 *  in the file PATENTS.  All contributing project authors may
 *  be found in the AUTHORS file in the root of the source tree.
 */
package com.yuntongxun.kitsdk.ui.group;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


















import com.yuntongxun.eckitsdk.R;
import com.yuntongxun.ecsdk.ECError;
import com.yuntongxun.kitsdk.adapter.CCPListAdapter;
import com.yuntongxun.kitsdk.core.CCPAppManager;
import com.yuntongxun.kitsdk.db.GroupSqlManager;
import com.yuntongxun.kitsdk.db.IMessageSqlManager;
import com.yuntongxun.kitsdk.ui.chatting.view.EmojiconTextView;
import com.yuntongxun.kitsdk.ui.chatting.view.OverflowAdapter;
import com.yuntongxun.kitsdk.ui.chatting.view.OverflowHelper;
import com.yuntongxun.kitsdk.ui.group.model.DemoGroup;
import com.yuntongxun.kitsdk.ui.group.model.GroupService;
import com.yuntongxun.kitsdk.utils.DemoUtils;
import com.yuntongxun.kitsdk.view.CCPFragment;


/**
 * 群组界面
 * @author 容联•云通讯
 * @date 2014-12-4
 * @version 4.0
 */
public class GroupListFragment extends CCPFragment implements GroupService.Callback{

    /**群组列表*/
    private ListView mListView;
    /**群组列表信息适配器*/
    private GroupAdapter mGroupAdapter;
    
    

    /**
     * 群组列表点击事件
     */
    private final AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            if(mGroupAdapter != null) {
                DemoGroup dGroup = mGroupAdapter.getItem(position);
                if(dGroup.isJoin()) {
                    CCPAppManager.startChattingAction(getActivity() , dGroup.getGroupId() , dGroup.getName());
                    return ;
                }
            }
        }

    };

   

    @Override
    protected int getLayoutId() {

        return R.layout.groups_activity;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(mListView != null) {
            mListView.setAdapter(null);
        }
        mListView = (ListView) findViewById(R.id.group_list);
        View emptyView = findViewById(R.id.empty_tip_tv);
        mListView.setEmptyView(emptyView);
        mListView.setOnItemClickListener(onItemClickListener);
        mGroupAdapter = new GroupAdapter(getActivity());
        mListView.setAdapter(mGroupAdapter);

        findViewById(R.id.loading_tips_area).setVisibility(View.GONE);

        registerReceiver(new String[]{getActivity().getPackageName() + ".inited" ,IMessageSqlManager.ACTION_GROUP_DEL});
    
    
        
    
    
    
    }
    
    
    
    @Override
    public void onResume() {
        super.onResume();
        GroupSqlManager.registerGroupObserver(mGroupAdapter);
        mGroupAdapter.notifyChange();

        GroupService.syncGroup(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        GroupSqlManager.unregisterGroupObserver(mGroupAdapter);

    }

    @Override
    protected void handleReceiver(Context context, Intent intent) {
        super.handleReceiver(context, intent);
        if(intent.getAction().equals(new String[]{getActivity().getPackageName()+".inited"})){
            GroupService.syncGroup(this);
        } else if (IMessageSqlManager.ACTION_GROUP_DEL.equals(intent.getAction())) {
            onSyncGroup();
        }
    }

    public void onGroupFragmentVisible(boolean visible) {

    }

    public class GroupAdapter extends CCPListAdapter<DemoGroup> {
        int padding;
        /**
         * @param ctx
         */
        public GroupAdapter(Context ctx) {
            super(ctx, new DemoGroup());
            padding = ctx.getResources().getDimensionPixelSize(R.dimen.OneDPPadding);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            ViewHolder mViewHolder = null;
            if(convertView == null || convertView.getTag() == null) {
                view = View.inflate(mContext , R.layout.item_mine_group, null);

                mViewHolder = new ViewHolder();
                mViewHolder.groupitem_avatar_iv = (ImageView) view.findViewById(R.id.groupitem_avatar_iv);
                mViewHolder.group_name = (EmojiconTextView) view.findViewById(R.id.group_name);
                mViewHolder.group_id = (TextView) view.findViewById(R.id.group_id);
                mViewHolder.join_state = (TextView) view.findViewById(R.id.join_state);
                view.setTag(mViewHolder);
            } else {
                view = convertView;
                mViewHolder = (ViewHolder) view.getTag();
            }

            DemoGroup group = getItem(position);
            if(group != null) {
               
                    mViewHolder.groupitem_avatar_iv.setImageResource(R.drawable.group_head);
                    mViewHolder.groupitem_avatar_iv.setPadding(0, 0, 0, 0);
                
                mViewHolder.group_name.setText(TextUtils.isEmpty(group.getName()) ? group.getGroupId() : group.getName());
                mViewHolder.group_id.setText(getString(R.string.str_group_id_fmt, DemoUtils.getGroupShortId(group.getGroupId())));
                mViewHolder.join_state.setText(group.isJoin() ?"已加入" :"");
                mViewHolder.join_state.setVisibility(group.isJoin()? View.VISIBLE:View.GONE);
            }

            return view;
        }

        @Override
        public void notifyChange() {
            Cursor cursor = GroupSqlManager.getGroupCursor();
            setCursor(cursor);
            super.notifyDataSetChanged();
        }

        @Override
        public void initCursor() {
            notifyChange();
        }

        @Override
        public DemoGroup getItem(DemoGroup t, Cursor cursor) {
            DemoGroup group = new DemoGroup();
            group.setCursor(cursor);
            return group;
        }


        class ViewHolder {
            ImageView groupitem_avatar_iv;
            EmojiconTextView group_name;
            TextView group_id;
            TextView join_state;
        }

    }


    @Override
    public void onSyncGroup() {
        mGroupAdapter.notifyChange();
    }

    @Override
    public void onSyncGroupInfo(String groupId) {

    }

    @Override
    public void onGroupDel(String groupId) {
        onSyncGroup();
    }

    @Override
    public void onError(ECError error) {
    }
}
