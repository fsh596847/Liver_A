package com.android.doctor.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.android.doctor.R;
import com.android.doctor.interf.OnListItemClickListener;
import com.android.doctor.model.TestItemList;
import com.android.doctor.model.CheckOutItemList;
import com.android.doctor.model.DiagList;
import com.android.doctor.model.MedicInfo;
import com.android.doctor.model.TreatPlanList;
import com.android.doctor.ui.base.BaseRecyViewAdapter;
import com.android.doctor.ui.viewholder.SimpleTextViewHolder;

/**
 * Created by Yong on 2016-02-14.
 */
public class SimpleTextListAdapter extends BaseRecyViewAdapter {
    public static final int VIEW_TYPE_MEDIC = 0;
    public static final int VIEW_TYPE_PRODUCT = 1;
    public void setItemOptionClickListener(
            OnListItemClickListener itemOptionClickListener) {
        this.itemOptionClickListener = itemOptionClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = super.getItemViewType(position);
        if (viewType == VIEW_TYPE_ITEM) {
            Object item = data.get(position);
            if (MedicInfo.MedicInfoEntity.class.equals(item.getClass())) {
                viewType = VIEW_TYPE_MEDIC;
            }
        }
        return viewType;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int pos) {
        int viewType = viewHolder.getItemViewType();
        if (SimpleTextViewHolder.class.equals(viewHolder.getClass())) {
            SimpleTextViewHolder tv = (SimpleTextViewHolder) viewHolder;
            Object obj = getItem(pos);
            if (obj != null) {
                if (obj.getClass().equals(MedicInfo.MedicInfoEntity.class)) { //选择药品
                    MedicInfo.MedicInfoEntity e = (MedicInfo.MedicInfoEntity) obj;
                    tv.setViewData(e.getPname());
                } else if (obj.getClass().equals(String.class)) {//选择用法
                    String tx = (String) obj;
                    tv.setViewData(tx);
                } else if (obj.getClass().equals(TreatPlanList.TreatPlanEntity.class)) {//选择诊断
                    String tx = ((TreatPlanList.TreatPlanEntity)obj).getName();
                    tv.setViewData(tx);
                } else if (obj.getClass().equals(DiagList.DiagEntity.class)) {//选择治疗方式
                    String tx = ((DiagList.DiagEntity)obj).getName();
                    tv.setViewData(tx);
                } else if (obj.getClass().equals(TestItemList.ClasslistEntity.class)) {//选择检验项目
                    String tx = ((TestItemList.ClasslistEntity)obj).getName();
                    tv.setViewData(tx);
                } else if (obj.getClass().equals(CheckOutItemList.CKOEntity.class)) {//选择检查项目
                    String tx = ((CheckOutItemList.CKOEntity)obj).getName();
                    tv.setViewData(tx);
                }
            }
        }
    }


    @Override
    protected RecyclerView.ViewHolder getViewHolder(ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(R.layout.item_list_text, viewGroup, false);
        SimpleTextViewHolder viewHolder = new SimpleTextViewHolder(view);
        viewHolder.setItemClickListener(itemOptionClickListener);
        return viewHolder;
    }

}
