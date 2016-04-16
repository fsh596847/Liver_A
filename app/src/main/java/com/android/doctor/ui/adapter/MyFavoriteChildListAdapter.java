package com.android.doctor.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.doctor.R;

import java.util.List;

public class MyFavoriteChildListAdapter extends BaseAdapter {

  private LayoutInflater layoutInflater;
  private List mData;
    private int mLayout;

  public MyFavoriteChildListAdapter(Context context, List data, int layout) {
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
        viewHolder.imageView = (ImageView) view.findViewById(R.id.img);
        viewHolder.textView1 = (TextView) view.findViewById(R.id.tv_name);
        viewHolder.textView2 = (TextView) view.findViewById(R.id.text);
        view.setTag(viewHolder);
    } else {
        viewHolder = (ViewHolder) view.getTag();
    }

    Context context = parent.getContext();
    //viewHolder.textView1.setText((String)mData.get(position));
    return view;
  }

  static class ViewHolder {
      ImageView imageView;
      TextView textView1;
      TextView textView2;
  }
}
