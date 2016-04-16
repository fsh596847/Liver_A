package com.android.doctor.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.doctor.R;

import java.util.List;

public class TimeLineListAdapter extends BaseAdapter {

  private LayoutInflater layoutInflater;
  private List<String> mData;
  private  int[] colors = { Color.WHITE, Color.rgb(219, 238, 244) };//RGB颜色

  public TimeLineListAdapter(Context context) {
    layoutInflater = LayoutInflater.from(context);
  }

    public void setmData(List<String> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    @Override
  public int getCount() {
      return mData == null ? 0 : mData.size();
  }

  @Override
  public Object getItem(int position) {
      return mData == null ? null : mData.get(position);
  }

  @Override
  public long getItemId(int position) {
      return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder viewHolder;
    View view = convertView;
    if (view == null) {
        view = layoutInflater.inflate(R.layout.item_list_time_line, parent, false);

        viewHolder = new ViewHolder();
        viewHolder.rlTitle = (RelativeLayout) view.findViewById(R.id.rl);
        viewHolder.timeDate = (TextView) view.findViewById(R.id.tv_date_time);
        viewHolder.content1 = (TextView) view.findViewById(R.id.tv_text1);
        viewHolder.content2 = (TextView) view.findViewById(R.id.tv_text2);
        viewHolder.line = view.findViewById(R.id.v_line);
        view.setTag(viewHolder);
    } else {
        viewHolder = (ViewHolder) view.getTag();
    }
      ViewGroup.LayoutParams params = viewHolder.line.getLayoutParams();
      if (position == 0) {
          viewHolder.rlTitle.setVisibility(View.VISIBLE);
      } else {
          if (mData.get(position).equals(mData.get(position - 1))) {
              viewHolder.rlTitle.setVisibility(View.GONE);
          }
          if (position + 1 == mData.size()) {
              viewHolder.line.setVisibility(View.GONE);
          }
      }
      //view.setBackgroundColor(colors[position % 2]);// 每隔item之间颜色不同

    return view;
  }

  static class ViewHolder {
      RelativeLayout rlTitle;
      TextView timeDate;
      TextView content1;
      TextView content2;
      View line;
  }
}
