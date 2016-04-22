package com.android.doctor.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.app.AppContext;
import com.android.doctor.helper.DateUtils;
import com.android.doctor.interf.OnListItemClickListener;
import com.android.doctor.model.PlanDeta;
import com.android.doctor.model.PlanList;
import com.android.doctor.ui.viewholder.TimeLineViewHolder;

import java.util.List;

/**
 *
 */
public class PlanDetaAdapter1<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public final static int HEADER = 0;
    public final static int NORMAL = 1;

    private List<T> mDataSet;
    private OnListItemClickListener itemClickListener;
    private LayoutInflater layoutInflater;

    public void setItemClickListener(OnListItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public PlanDetaAdapter1(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return HEADER;
        else return NORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_timeline_base, viewGroup, false);
        TimeLineViewHolder vh = new TimeLineViewHolder(v, viewType);
        vh.setItemClickListener(this.itemClickListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        int viewType = viewHolder.getItemViewType();
        if (viewType == HEADER) {

        } else if (viewType == NORMAL) {

        }
    }

    @Override
    public int getItemCount() {
        return mDataSet == null ? 0 : mDataSet.size();
    }


    static class PlanBaseViewHolder extends RecyclerView.ViewHolder{
        protected TextView mTvTitle;
        protected TextView mTvPatient;
        protected TextView mTvDoctor;
        protected TextView mTvDate;
        protected TextView mTvState;

        public PlanBaseViewHolder(View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View view) {
            mTvTitle = (TextView) view.findViewById(R.id.tv_title);
            mTvPatient = (TextView) view.findViewById(R.id.tv_patient);
            mTvDoctor = (TextView) view.findViewById(R.id.tv_doctor);
            mTvDate = (TextView) view.findViewById(R.id.tv_date);
            mTvState = (TextView) view.findViewById(R.id.tv_state);
        }

        private void setBaseViewData(PlanList.DataEntity mPlItem) {
            mTvTitle.setText(mPlItem.getPlanname());
            mTvPatient.setText(mPlItem.getPname());
            mTvDoctor.setText(mPlItem.getDname());
            mTvDate.setText(DateUtils.getDateString(mPlItem.getCreateTime()));
            int stat = mPlItem.getStatus();
            mTvState.setText(stat == 1 ? "执行中" : stat == 2 ? "已完成" : "未创建");
        }
    }

    static class PlanItemViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout rlTitle;
        TextView tvTitle;
        ViewGroup vgItems;
        View line;

        public PlanItemViewHolder(View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View view) {
            rlTitle = (RelativeLayout) view.findViewById(R.id.rl_label);
            tvTitle = (TextView) view.findViewById(R.id.tv_label);
            vgItems = (ViewGroup) view.findViewById(R.id.ll_items);
            line = view.findViewById(R.id.v_line);
        }

        private void setPlanItemViewData(PlanDeta.DataEntity d) {
            tvTitle.setText(d.getName());
            vgItems.removeAllViews();
            vgItems.setVisibility("false".equals(d.getShow()) ? View.GONE : View.VISIBLE);
            List<PlanDeta.DataEntity.ItemsEntity> items = d.getItems();
            for (int i = 0; items != null && i < items.size(); ++i) {
                PlanDeta.DataEntity.ItemsEntity itemsEntity = items.get(i);
                vgItems.addView(crtCaptionView(itemsEntity));
            }
        }

        private View crtCaptionView(PlanDeta.DataEntity.ItemsEntity o) {
            LayoutInflater layoutInflater = LayoutInflater.from(AppContext.context());
            View view = layoutInflater.inflate(R.layout.item_caption, null);
            TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
            TextView tvContent = (TextView) view.findViewById(R.id.tv_content);
            TextView tvContentEx = (TextView) view.findViewById(R.id.tv_content_ex);
            tvTitle.setText(o.getName());
            tvContent.setText(o.getContent());
            tvContentEx.setText(o.getHint());
            return view;
        }
    }

}
