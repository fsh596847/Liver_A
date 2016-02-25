package com.fidots.restaurant.ui.adapter;

/**
 * Created by Yong on 2016-02-18.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fidots.restaurant.R;

public class MyFavoriteExpandableAdapter extends BaseExpandableListAdapter {
    Context context;
    private String[] group = new String[]{"美食","美食", "美食"};
    private String[][] child;
    int[] group_state_array = new int[] { R.drawable.arrow_down, R.drawable.arrow_up };
    private LayoutInflater mInflater;

    public MyFavoriteExpandableAdapter(Context context, String[] group, String[][] child) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
        //this.group = group;
        //this.child = child;
    }

    @Override
    public int getGroupCount() {
        return group.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 3;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return group[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return "1";
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

    /**
     * group
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        // 为视图对象指定布局
        convertView = LinearLayout.inflate(context, R.layout.item_my_favorite_group, null);
        //RelativeLayout myLayout = (RelativeLayout) convertView.findViewById(R.id.rl_group);
        // 用来显示一级标签上的标题信息
        TextView group_title = (TextView) convertView.findViewById(R.id.tv_group_name);
        // 用来显示一级标签上的大体描述的信息
        TextView group_state = (TextView) convertView.findViewById(R.id.tv_icon);
        // 设置标题上的文本信息
        group_title.setText(group[groupPosition]);
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
        Log.d("MyFavoriteExpand:", "" + groupPosition);
        if (convertView == null) {
            mViewChild = new ViewChild();
            convertView = mInflater.inflate(R.layout.item_my_favorite_child_item, null);
            //mViewChild.gridView = (GridView) convertView.findViewById(R.id.gv_child);
            convertView.setTag(mViewChild);
        } else {
            mViewChild = (ViewChild) convertView.getTag();
        }
        //List list = new ArrayList();
        //list.add("1");
        //list.add("1");
        //list.add("1");
        //MyFavoriteChildListAdapter adapter = new MyFavoriteChildListAdapter(context, list, R.layout.item_my_favorite_child_item);
        //mViewChild.gridView.setAdapter(adapter);
        //setGridViewListener(mViewChild.gridView);
        //mViewChild.gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        return convertView;
    }

    ViewChild mViewChild;

    static class ViewChild {
        TextView textView;
        GridView gridView;
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
