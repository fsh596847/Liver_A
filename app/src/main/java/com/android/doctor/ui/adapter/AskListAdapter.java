package com.android.doctor.ui.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.app.AppConfig;
import com.android.doctor.helper.DateUtils;
import com.android.doctor.model.AskList;
import com.android.doctor.ui.base.BaseListAdapter;
import com.yuntongxun.kitsdk.utils.DateUtil;

import java.util.Date;

/**
 * Created by Yong on 2016/5/20.
 */
public class AskListAdapter extends BaseListAdapter {

    @Override
    protected View getRealView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        View view = convertView;
        if (view == null) {
            view = mInflater.inflate(R.layout.item_ask, parent, false);
            vh = new ViewHolder();
            vh.tvTitle = (TextView) view.findViewById(R.id.tv_title);
            vh.content1 = (TextView) view.findViewById(R.id.tv_name);
            vh.timeDate = (TextView) view.findViewById(R.id.tv_date);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }

        Log.d(AppConfig.TAG, "AskListAdapter-> getRealView " + position + ", " + String.valueOf(vh == null));
        setViewData(vh, getItem(position));

        return view;
    }

    private void setViewData(ViewHolder vh, Object item) {
        if (item == null || vh == null) return;
        if (item.getClass().equals(AskList.AsksEntity.class)) {
            AskList.AsksEntity e = (AskList.AsksEntity) item;
            vh.tvTitle .setText(e.getTitle());
            vh.content1.setText("患者: " + e.getNickname());
            Date date = DateUtils.toDate(e.getPubtime());
            vh.timeDate.setText(String.format("%tm", date) + "-" + String.format("%td", date));
        }
    }

    static class ViewHolder {
        TextView tvTitle;
        TextView timeDate;
        TextView content1;
        TextView content2;
        View line;
    }
}
