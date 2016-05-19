package com.android.doctor.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.model.DoctorTimeSheet;

import java.util.List;

public class DoctorTimeListAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<DoctorTimeSheet.WeekDayEntity> mData;
    private int mLayout;
    private int[] colors = { Color.WHITE, Color.rgb(219, 238, 244) };//RGB颜色
    private static String week[] = {"", "周一","周二","周三","周四","周五","周六","周日"};

    public DoctorTimeListAdapter(Context context, int layout) {
        layoutInflater = LayoutInflater.from(context);
        this.mLayout = layout;
    }

    public void setmData(List<DoctorTimeSheet.WeekDayEntity> mData) {
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
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.setViewData((DoctorTimeSheet.WeekDayEntity) getItem(position));
        view.setBackgroundColor(colors[position % 2]);// 每隔item之间颜色不同
        viewHolder.tx0.setText(week[position]);
        return view;
    }

    static class ViewHolder {
        TextView tx0;
        TextView tx1;
        TextView tx2;
        TextView tx3;
        TextView tx4;

        public ViewHolder(View view) {
            tx0 = (TextView) view.findViewById(R.id.tv0);
            tx1 = (TextView) view.findViewById(R.id.tv1);
            tx2 = (TextView) view.findViewById(R.id.tv2);
            tx3 = (TextView) view.findViewById(R.id.tv3);
            tx4= (TextView) view.findViewById(R.id.tv4);
        }

        public void setViewData(DoctorTimeSheet.WeekDayEntity dayEntity) {
            if (dayEntity == null) return;
            tx1.setText(dayEntity.getD1().getStyle());
            tx2.setText(dayEntity.getD2().getStyle());
            tx3.setText(dayEntity.getD3().getStyle());
            tx4.setText(dayEntity.getD4().getStyle());
        }
    }
}
