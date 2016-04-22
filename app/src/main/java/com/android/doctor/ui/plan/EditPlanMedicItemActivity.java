package com.android.doctor.ui.plan;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.helper.DialogHelper;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.model.MedicInfo;
import com.android.doctor.model.PlanDeta;
import com.android.doctor.ui.base.BaseActivity;
import com.android.doctor.ui.widget.NumberStepper;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;

import java.util.Arrays;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Yong on 2016/4/2.
 */
public class EditPlanMedicItemActivity extends BaseActivity{

    public static final int REQUEST_CODE_SELECT_MEDICINE = 300;
    public static final int REQUEST_CODE_SELECT_USAGE = 301;
    public static final int REQUEST_CODE_SELECT_USELEVEL = 302;

    @InjectView(R.id.edt_title)
    protected EditText mEdtMeName;
    @InjectView(R.id.tv_usage)
    protected TextView mTvUsage;
    @InjectView(R.id.tv_use_level)
    protected TextView mTvUseLevel;
    @InjectView(R.id.tv_duration_time)
    protected TextView mTvDuration;
    @InjectView(R.id.num_duration)
    protected NumberStepper mNumDuration;
    @InjectView(R.id.tv_dtime_unit)
    protected TextView mTvDUnit;
    @InjectView(R.id.edt_content)
    protected EditText mTvNotice;

    private PlanDeta.DataEntity.ItemsEntity mItemEntity;
    private int mReqCode;
    private int index;

    @Override
    protected void setContentLayout() {
        setContentView(R.layout.activity_medicine_item);
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        mItemEntity = intent.getParcelableExtra("data");
        mReqCode = intent.getIntExtra("requestCode", 0);
        index = intent.getIntExtra("index", -1);
        if (mItemEntity == null) {
            setActionBar(R.string.new_project);
            mItemEntity = new PlanDeta.DataEntity.ItemsEntity();
        } else {
            setActionBar(mItemEntity.getName());
        }

        setViewData();
    }

    private void setViewData() {
        if (mItemEntity == null) return;
        mEdtMeName.setText(mItemEntity.getName());
        mEdtMeName.setSelection(mEdtMeName.getText().length());
        mTvUsage.setText(mItemEntity.getYf());
        mTvUseLevel.setText(mItemEntity.getYl());
        mTvNotice.setText(mItemEntity.getContent());

        int end = mItemEntity.getEnd();
        String endUnit = mItemEntity.getEndunit();
        boolean nevEnd = (end == 0 && TextUtils.isEmpty(endUnit));

        mNumDuration.setVisibility(nevEnd ? View.INVISIBLE : View.VISIBLE);
        mTvDUnit.setVisibility(nevEnd ? View.INVISIBLE : View.VISIBLE);
        mTvDuration.setText(nevEnd ? R.string.lifetime : R.string.custom);
        mNumDuration.setValue(end);
        mTvDUnit.setText(endUnit);
    }

    private void updateDuaView(int vis, String text) {
        mNumDuration.setVisibility(vis);
        mTvDUnit.setVisibility(vis);
        mTvDuration.setText(text);
    }

    private boolean onSetBackResult() {
        String title = mEdtMeName.getText().toString();
        String content = mTvNotice.getText().toString();
        String yf = mTvUsage.getText().toString();
        String yl = mTvUseLevel.getText().toString();
        if (TextUtils.isEmpty(title)) {
            UIHelper.showToast(this, "请填写标题");
            return false;
        }
        mItemEntity.setName(title);
        mItemEntity.setContent(content);
        mItemEntity.setYf(yf);
        mItemEntity.setYl(yl);
        int time = mNumDuration.getValue();
        String unit = mTvDUnit.getText().toString();
        mItemEntity.setEnd(time);
        mItemEntity.setEndunit(unit);
        String hint = String.format(getString(R.string.medicine_hint), yf, yl, time, unit);
        mItemEntity.setHint(hint);
        return true;
    }

    @OnClick(R.id.tv_select_medicine)
    protected void onSelectMedicine() {
        Intent intent = new Intent();
        intent.setClass(this, SelectMedicineListActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SELECT_MEDICINE);
    }

    @OnClick(R.id.tv_usage)
    protected void onSelectUsage() {
        SelectUseItemActivity.startAty(this, REQUEST_CODE_SELECT_USAGE);
    }

    @OnClick(R.id.tv_use_level)
    protected void onSelectUseLevel() {
        SelectUseItemActivity.startAty(this, REQUEST_CODE_SELECT_USELEVEL);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_SELECT_MEDICINE:
                    MedicInfo.MedicInfoEntity extra = data.getParcelableExtra("data");
                    mEdtMeName.setText(extra.getPname());
                    break;
                case REQUEST_CODE_SELECT_USAGE:
                    mTvUsage.setText(data.getStringExtra("data"));
                    break;
                case REQUEST_CODE_SELECT_USELEVEL:
                    mTvUseLevel.setText(data.getStringExtra("data"));
                    break;
            }
        }
    }

    @OnClick(R.id.img_complete)
    protected void onReturn() {
        if (!onSetBackResult()) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra("data", mItemEntity);
        intent.putExtra("index", index);
        setResult(RESULT_OK, intent);
        finish();
    }

    /**持续时间类型切换**/
    @OnClick(R.id.tv_duration_time)
    protected void onDurationTypeClick() {
        final List<String> list = Arrays.asList(getResources().getStringArray(R.array.duration_time));
        DialogHelper.showBottomListDialog(this, list,
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        dialog.dismiss();
                        if (position == 4) {
                            updateDuaView(View.VISIBLE, list.get(position));
                        } else if (position != 5) {
                            updateDuaView(View.INVISIBLE, list.get(position));
                        }
                    }
                }, null);
    }

    @OnClick(R.id.tv_dtime_unit)
    protected void showUnitListView() {
        final List<String> list = Arrays.asList(getResources().getStringArray(R.array.unit_date_exclude_week));
        DialogHelper.showBottomListDialog(this, list,
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        dialog.dismiss();
                        if (position != 3) {
                            mTvDUnit.setText(list.get(position));
                        }
                    }
                }, null);
    }
}
