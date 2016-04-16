package com.android.doctor.ui.adapter;

/**
 * Created by Yong on 2016-02-23.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.android.doctor.R;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class PopDownMenuAdapter extends BaseAdapter {

    private Context context;
    private List<String> list;
    private int checkItemPosition = 0;
    private int mLayout;
    private boolean mIsGrid;
    private int mType;

    public void setCheckItem(int position) {
        checkItemPosition = position;
        notifyDataSetChanged();
    }

    public PopDownMenuAdapter(Context context, List<String> list, int layout, int type, boolean isGrid) {
        this.context = context;
        this.list = list;
        this.mLayout = layout;
        this.mIsGrid = isGrid;
        this.mType = type;
    }

    public int getmType() {
        return mType;
    }

    public int getCheckItemPosition() {
        return checkItemPosition;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(context).inflate(mLayout, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        fillValue(position, viewHolder);
        return convertView;
    }

    private void fillValue(int position, ViewHolder viewHolder) {
        viewHolder.mText.setText(list.get(position));
        if (checkItemPosition != -1) {
            if (checkItemPosition == position) {
                viewHolder.mText.setTextColor(context.getResources().getColor(R.color.appThemePrimary));
                if (mIsGrid) {
                    viewHolder.mText.setBackgroundResource(R.drawable.item_checked_bg);
                } else {
                    //viewHolder.mText.setCompoundDrawablesWithIntrinsicBounds(null, null, context.getResources().getDrawable(R.drawable.checked), null);
                }
            } else {
                viewHolder.mText.setTextColor(context.getResources().getColor(R.color.app_theme_primary_textcolor));
                /*if (mIsGrid) {

                } else {
                    viewHolder.mText.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                }*/
            }
        }
    }

    static class ViewHolder {
        @InjectView(R.id.tv_text)
        TextView mText;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}

