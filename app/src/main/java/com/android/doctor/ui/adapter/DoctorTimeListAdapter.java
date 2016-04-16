package com.android.doctor.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.doctor.R;

import java.util.List;

public class DoctorTimeListAdapter extends BaseAdapter {

  private LayoutInflater layoutInflater;
  private List<String> mData;
    private int mLayout;
  private  int[] colors = { Color.WHITE, Color.rgb(219, 238, 244) };//RGB颜色

  public DoctorTimeListAdapter(Context context, int layout) {
    layoutInflater = LayoutInflater.from(context);
      this.mLayout = layout;
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
        view = layoutInflater.inflate(mLayout, parent, false);

        viewHolder = new ViewHolder();
        viewHolder.textView = (TextView) view.findViewById(R.id.tv1);
        view.setTag(viewHolder);
    } else {
        viewHolder = (ViewHolder) view.getTag();
    }

      view.setBackgroundColor(colors[position % 2]);// 每隔item之间颜色不同
    Context context = parent.getContext();
    viewHolder.textView.setText((String)mData.get(position));
    return view;
  }

  static class ViewHolder {
    TextView textView;
  }
}
