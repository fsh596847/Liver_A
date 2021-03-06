package com.android.doctor.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.helper.DateUtils;
import com.android.doctor.interf.OnListItemClickListener;
import com.android.doctor.model.PlanDeta;
import com.android.doctor.model.PlanList;
import com.android.doctor.model.TPlanList;
import com.android.doctor.model.TempPlanList;

import java.util.List;

public class PlanDetaAdapter<T extends Object> extends BaseAdapter implements View.OnClickListener{
    public final static int HEADER = 0;
    public final static int NORMAL = 1;

    private LayoutInflater layoutInflater;
    private List<T> mData;
    private OnListItemClickListener itemClickListener;
    private Context context;

    public PlanDetaAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public void setmData(List<T> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    public void setItemClickListener(OnListItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData == null ? null : mData.get(position);
    }

    public List<T> getmData() {
        return mData;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public int getItemById(String id) {
        for (int i = 0; i < mData.size() ;++i) {
            Object o = mData.get(i);
            if (PlanDeta.PlanDetaEntity.class.equals(o.getClass())) {
                PlanDeta.PlanDetaEntity d = (PlanDeta.PlanDetaEntity) mData.get(i);
                if (d.get_id().equals(id)) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public int getItemViewType(int position) {
        Object object = mData.get(position);
        if (object instanceof PlanList.PlanBaseEntity )
        //if (position == 0)
            return HEADER;
        else return NORMAL;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       int type = getItemViewType(position);
        if (convertView == null) {
            if (type == HEADER) {
                PlanBaseViewHolder viewHolder;
                viewHolder = new PlanBaseViewHolder();
                convertView = layoutInflater.inflate(R.layout.item_plan_base, parent, false);
                viewHolder.mTvTitle = (TextView) convertView.findViewById(R.id.tv_title);
                viewHolder.mTvPatient = (TextView) convertView.findViewById(R.id.tv_patient);
                viewHolder.mTvDoctor = (TextView) convertView.findViewById(R.id.tv_doctor);
                viewHolder.mTvDate = (TextView) convertView.findViewById(R.id.tv_date);
                viewHolder.mTvState = (TextView) convertView.findViewById(R.id.tv_import_state);
                convertView.setTag(viewHolder);
                setBaseViewData(viewHolder, (PlanList.PlanBaseEntity) getItem(position));
            } else {
                PlanItemViewHolder viewHolder = new PlanItemViewHolder();
                convertView = layoutInflater.inflate(R.layout.item_plan_deta, parent, false);
                viewHolder.rlTitle = (RelativeLayout) convertView.findViewById(R.id.rl_label);
                viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_label);
                viewHolder.vgItems = (ViewGroup) convertView.findViewById(R.id.ll_items);
                viewHolder.line = convertView.findViewById(R.id.v_line);
                viewHolder.rlTitle.setOnClickListener(this);
                viewHolder.rlTitle.setTag(position);
                convertView.setTag(viewHolder);
                setPlanItemViewData(viewHolder, getItem(position));
            }
        } else {
            if (type == HEADER) {
                PlanBaseViewHolder viewHolder = (PlanBaseViewHolder) convertView.getTag();
                setBaseViewData(viewHolder, (PlanList.PlanBaseEntity) getItem(position));
            } else {
                PlanItemViewHolder viewHolder = (PlanItemViewHolder) convertView.getTag();
                viewHolder.rlTitle.setOnClickListener(this);
                viewHolder.rlTitle.setTag(position);
                setPlanItemViewData(viewHolder, getItem(position));
            }
        }
        return convertView;
    }


    private void setBaseViewData(PlanBaseViewHolder vh, PlanList.PlanBaseEntity mPlItem) {
        vh.mTvTitle.setText(mPlItem.getPlanname());
        vh.mTvPatient.setText(mPlItem.getPname());
        vh.mTvDoctor.setText(mPlItem.getDname());
        vh.mTvDate.setText(DateUtils.getDateString(mPlItem.getCreateTime()));
        int stat = mPlItem.getStatus();
        String s = String.format(context.getString(R.string.plan_status), stat == 1 ? "执行中" : stat == 2 ? "已完成" : "未创建", mPlItem.getSendstatus());
        vh.mTvState.setText(s);
    }

    private void setPlanItemViewData(PlanItemViewHolder vh, Object obj) {
        if (obj == null) return;
        if (obj.getClass().equals(PlanDeta.PlanDetaEntity.class)) {
            PlanDeta.PlanDetaEntity d = (PlanDeta.PlanDetaEntity) obj;
            vh.tvTitle.setText(d.getName());
            vh.vgItems.removeAllViews();
            vh.vgItems.setVisibility("false".equals(d.getShow()) ? View.GONE : View.VISIBLE);
            List<PlanDeta.PlanDetaEntity.ItemsEntity> items = d.getItems();
            for (int i = 0; items != null && i < items.size(); ++i) {
                PlanDeta.PlanDetaEntity.ItemsEntity itemsEntity = items.get(i);
                vh.vgItems.addView(crtCaptionView(itemsEntity));
            }
        }
    }

    private View crtCaptionView(PlanDeta.PlanDetaEntity.ItemsEntity o) {
        View view = layoutInflater.inflate(R.layout.item_caption, null);
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        TextView tvContent = (TextView) view.findViewById(R.id.tv_content);
        TextView tvContentEx = (TextView) view.findViewById(R.id.tv_content_ex);
        tvTitle.setText(o.getName());
        tvContent.setText(o.getContent());
        tvContentEx.setText(o.getHint());
        return view;
    }

    public void updateData(T d) {
        if (d == null) return;
        int position = 0;
        if (d.getClass().equals(PlanDeta.PlanDetaEntity.class)) {
            position = getItemById(((PlanDeta.PlanDetaEntity)d).get_id());
        }
        Log.d("updateData", "" + position);
        if (position == -1) {
            mData.add(d);
            notifyDataSetChanged();
        } else if (0 <= position && position < mData.size()) {
            mData.set(position, d);
            notifyDataSetChanged();
        }
    }

    static class PlanBaseViewHolder {
        protected TextView mTvTitle;
        protected TextView mTvPatient;
        protected TextView mTvDoctor;
        protected TextView mTvDate;
        protected TextView mTvState;
    }

    static class PlanItemViewHolder {
        RelativeLayout rlTitle;
        TextView tvTitle;
        ViewGroup vgItems;
        View line;
    }


    @Override
    public void onClick(View v) {
        if (itemClickListener != null) {
            if (v.getId() == R.id.rl_label) {
                Log.d("1PlanDetaListAdapter", "" + (int) v.getTag());
                itemClickListener.onItemClick((Integer) v.getTag(), v);
            }
        }
    }
}
