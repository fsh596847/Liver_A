package com.android.doctor.ui.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.doctor.R;

import java.util.List;

public class SimpleListAdapter extends BaseAdapter {

  private LayoutInflater layoutInflater;
  private List<String> mData;
    private int mLayout;

  public SimpleListAdapter(Context context, List<String> data, int layout) {
    layoutInflater = LayoutInflater.from(context);
    this.mData = data;
      this.mLayout = layout;
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
        viewHolder.textView = (TextView) view.findViewById(R.id.tv_text);
        view.setTag(viewHolder);
    } else {
        viewHolder = (ViewHolder) view.getTag();
    }

    Context context = parent.getContext();
    viewHolder.textView.setText((String)mData.get(position));
    return view;
  }

  static class ViewHolder {
    TextView textView;
  }
}
