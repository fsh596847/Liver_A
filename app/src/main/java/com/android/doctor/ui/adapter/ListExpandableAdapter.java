package com.android.doctor.ui.adapter;

/**
 * Created by Yong on 2016-02-18.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.doctor.R;

public class ListExpandableAdapter extends BaseExpandableListAdapter {
    private LayoutInflater mInflater;
    private Context context;

    int[] group_state_array = new int[] { R.drawable.arrow_down, R.drawable.arrow_up };
    private List<String> mGroupList;
    private List<List<String>> mChildList;

    public ListExpandableAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return mGroupList == null ? 0 : mGroupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (mChildList == null) {
            return 0;
        }
        if (groupPosition >= mChildList.size() || mChildList.get(groupPosition) == null) {
            return 0;
        }

        return mChildList.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroupList == null ? null : mGroupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (mChildList == null) {
            return null;
        }
        if (groupPosition >= mChildList.size() || mChildList.get(groupPosition) == null) {
            return null;
        }

        return mChildList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    public List<String> getGroup() {
        return mGroupList;
    }

    public void setGroup(List<String> group) {
        this.mGroupList = group;
    }

    public List<List<String>> getChild() {
        return mChildList;
    }

    public void setChild(List<List<String>> child) {
        this.mChildList = child;
    }

    public void setData(List<String> group, List<List<String>> child) {
        this.mGroupList = group;
        this.mChildList = child;
        notifyDataSetChanged();
    }

    /**
     * group
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        // 为视图对象指定布局
        convertView = LinearLayout.inflate(context, R.layout.item_list_group, null);
        //RelativeLayout myLayout = (RelativeLayout) convertView.findViewById(R.id.rl_group);
        // 用来显示一级标签上的标题信息
        TextView group_title = (TextView) convertView.findViewById(R.id.tv_group_name);
        // 用来显示一级标签上的大体描述的信息
        TextView group_state = (TextView) convertView.findViewById(R.id.tv_icon);
        // 设置标题上的文本信息
        //group_title.setText(mGroupList.get(groupPosition));
        // 设置整体描述上的文本信息

        if (!isExpanded) {
            group_state.setBackgroundResource(group_state_array[0]);
            //myLayout.setBackgroundResource(R.drawable.text_item_top_bg);
        } else {
            //myLayout.setBackgroundResource(R.drawable.text_item_bg);
            group_state.setBackgroundResource(group_state_array[1]);
        } // 返回一个布局对象
        return convertView;

    }

    /**
     * child
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
                             ViewGroup parent) {
        if (convertView == null) {
            mViewChild = new ViewChild();
            convertView = mInflater.inflate(R.layout.item_list_child_item, null);
            mViewChild.tv1 = (TextView)convertView.findViewById(R.id.tv_name);
            mViewChild.tv2 = (TextView)convertView.findViewById(R.id.text);
            convertView.setTag(mViewChild);
        } else {
            mViewChild = (ViewChild) convertView.getTag();
        }

        return convertView;
    }

    ViewChild mViewChild;

    static class ViewChild {
        TextView tv0;
        TextView tv1;
        TextView tv2;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
    /**
     * 设置gridview点击事件监听
     *
     * @param gridView
     */
    private void setGridViewListener(final GridView gridView) {
        gridView.setOnItemClickListener(new GridView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                if (view instanceof TextView) {
                    // 如果想要获取到哪一行，则自定义gridview的adapter，item设置2个textview一个隐藏设置id，显示哪一行
                    TextView tv = (TextView) view;
                    Toast.makeText(context, "position=" + position + "||" + tv.getText(), Toast.LENGTH_SHORT).show();
                    Log.e("hefeng", "gridView listaner position=" + position + "||text=" + tv.getText());
                }
            }
        });
    }

    /**
     * 设置gridview数据
     *
     * @param data
     * @return
     */
    private ArrayList<HashMap<String, Object>> setGridViewData(String[] data) {
        ArrayList<HashMap<String, Object>> gridItem = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < data.length; i++) {
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put("channel_gridview_item", data[i]);
            gridItem.add(hashMap);
        }
        return gridItem;
    }

}
