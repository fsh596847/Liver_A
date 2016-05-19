package com.android.doctor.ui.plan;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.android.doctor.R;
import com.android.doctor.app.AppConfig;
import com.android.doctor.app.AppManager;
import com.android.doctor.helper.DialogUtils;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.model.CheckOutItemList;
import com.android.doctor.model.PlanDeta;
import com.android.doctor.model.TestItemList;
import com.android.doctor.ui.base.BaseActivity;
import com.android.doctor.ui.widget.NumberStepper;
import com.android.doctor.ui.widget.NumberStepperValueChangeListener;
import com.google.gson.Gson;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;

import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Created by Yong on 2016/3/30.
 */
public class EditPlanItemDetaActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.rl_title)
    protected RelativeLayout mRlTitle;
    @InjectView(R.id.tv_arrow)
    protected TextView mTvArrow;
    @InjectView(R.id.edt_title)
    protected EditText mEdtTitle;
    @InjectView(R.id.edt_content)
    protected EditText mEdtContent;
    @InjectView(R.id.toggle_btn)
    protected ToggleButton mToggleBtn;
    @InjectView(R.id.tv_hint)
    protected TextView mTvscHint;
    @InjectView(R.id.ll_custom_scheme)
    protected ViewGroup mLlScContainer;
    @InjectView(R.id.ll_container)
    protected ViewGroup mExecPlanContainer;

    @InjectView(R.id.ll_scheme)
    protected LinearLayout mLlScheme;

    private int mReqCode;
    private int index;
    private PlanDeta.PlanDetaEntity.ItemsEntity mItemEntity;

    @Override
    protected void setContentLayout() {
        setContentView(R.layout.activity_edit_subsitem_scheme);
    }

    @Override
    protected void initView() {
        super.initView();
        Intent intent = getIntent();
        mItemEntity = intent.getParcelableExtra("data");
        index = intent.getIntExtra("index", -1);
        mReqCode = intent.getIntExtra("requestCode", 0);

        setViewData();
    }

    private void setViewData() {
        if (mItemEntity == null) {
            setActionBar(R.string.new_project);
            mItemEntity = new PlanDeta.PlanDetaEntity.ItemsEntity();
        } else {
            setActionBar(mItemEntity.getName());
        }

        mEdtTitle.setText(mItemEntity.getName());
        mEdtTitle.setSelection(mEdtTitle.getText().length());
        mEdtContent.setText(mItemEntity.getContent());
        mEdtContent.setSelection(mEdtContent.getText().length());
        mTvscHint.setText(mItemEntity.getHint());

        if (mReqCode == PlanDetaActivity.REQUEST_DOCTOR_ADVICE_CODE ||
                mReqCode == PlanDetaActivity.REQUEST_DOCTOR_REMIND_CODE) {
            mLlScheme.setVisibility(View.GONE);
        }
        if (mReqCode == PlanDetaActivity.REQUEST_DOCTOR_TEST_CODE ||
                mReqCode == PlanDetaActivity.REQUEST_DOCTOR_CHECKOUT_CODE) {
            mTvArrow.setVisibility(View.VISIBLE);
            mRlTitle.setOnClickListener(this);
        }
        setExecPlanView();
    }

    @OnClick(R.id.img_complete)
    protected void onComplete() {
        if (!onSetBackResult()) {
            return;
        }

        Intent intent = new Intent();
        intent.putExtra("data", mItemEntity);
        intent.putExtra("index", index);
        setResult(RESULT_OK, intent);
        AppManager.getAppManager().finishActivity(EditPlanItemDetaActivity.class);
    }

    @OnClick(R.id.tv_arrow)
    protected void selectClass() {
        if (mReqCode == PlanDetaActivity.REQUEST_DOCTOR_TEST_CODE ) {
            SelectTxItemActivity.startAty(this, FragmentTxItemList.REQUEST_LOAD_TYPE_TEST_ITEM);
        } else if (mReqCode == PlanDetaActivity.REQUEST_DOCTOR_CHECKOUT_CODE) {
            SelectTxItemActivity.startAty(this, FragmentTxItemList.REQUEST_LOAD_TYPE_CHECKOUT_ITEM);
        }
    }

    @OnCheckedChanged(R.id.toggle_btn)
    protected void onToggleCustome(CompoundButton btn, boolean check) {
        mLlScContainer.setVisibility(check ? View.INVISIBLE : View.VISIBLE);
        if (check) {
            setDefaultPlan();
        } else {
            setExecPlan();
        }
    }

    private void setExecPlanView() {
        boolean check = mItemEntity.getPlan() == 0;
        mToggleBtn.setChecked(check);
        mLlScContainer.setVisibility(check ? View.INVISIBLE : View.VISIBLE);
        //if (!check) {
            List<PlanDeta.PlanDetaEntity.ItemsEntity.ExecplanEntity> list = mItemEntity.getExecplan();
            if (list != null && 0 < list.size()) {
                LayoutInflater inflater = LayoutInflater.from(this);
                for (PlanDeta.PlanDetaEntity.ItemsEntity.ExecplanEntity e : list) {
                    View view = inflater.inflate(R.layout.item_execplan, null);
                    ExecPlanViewHolder vh = new ExecPlanViewHolder(this, view);
                    view.setTag(vh);
                    vh.setViewData(e);
                    mExecPlanContainer.addView(view);
                }
            }
        //}
    }

    private void setDefaultPlan() {
        List<PlanDeta.PlanDetaEntity.ItemsEntity.DefaultplanEntity> list = mItemEntity.getDefaultplan();
        if (list != null && 0 < list.size()) {
            PlanDeta.PlanDetaEntity.ItemsEntity.DefaultplanEntity e = list.get(0);
            String hint = String.format(getString(R.string.execplan_hint), e.getBeginprefix(), e.getBegin(), e.getBeginunit(),
                    e.getFreq(), e.getFrequnit(), e.getEnd(), e.getEndunit());
            mTvscHint.setText(hint);
            mItemEntity.setHint(hint);
        }
    }

    private boolean onSetBackResult() {
        String title = mEdtTitle.getText().toString();
        String content = mEdtContent.getText().toString();
        if (TextUtils.isEmpty(title)) {
            UIHelper.showToast("请填写标题");
            return false;
        }
        mItemEntity.setName(title);
        mItemEntity.setContent(content);
        Gson g = new Gson();
        Log.d(AppConfig.TAG + " before", g.toJson(mItemEntity.getExecplan()));
        if (mLlScContainer.getVisibility() == View.INVISIBLE) return true;
        setExecPlan();
        Log.d(AppConfig.TAG + ", end", g.toJson(mItemEntity.getExecplan()));
        return true;
    }

    private void setExecPlan() {
        List<PlanDeta.PlanDetaEntity.ItemsEntity.ExecplanEntity> list = mItemEntity.getExecplan();
        if (list != null && 0 < list.size()) {
            PlanDeta.PlanDetaEntity.ItemsEntity.ExecplanEntity e = list.get(0);
            String hint = String.format(getString(R.string.execplan_hint), e.getBeginprefix(), e.getBegin(), e.getBeginunit(),
                    e.getFreq(), e.getFrequnit(), e.getEnd(), e.getEndunit());
            mTvscHint.setText(hint);
            mItemEntity.setHint(hint);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;
        if (requestCode == PlanDetaActivity.REQUEST_DOCTOR_TEST_CODE) {
            TestItemList.ClasslistEntity clsEntity = data.getParcelableExtra("data");
            mEdtTitle.setText(clsEntity.getName());
        } else if (requestCode == PlanDetaActivity.REQUEST_DOCTOR_CHECKOUT_CODE) {
            CheckOutItemList.CKOEntity clsEntity = data.getParcelableExtra("data");
            mEdtTitle.setText(clsEntity.getName());
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.rl_title) {
            Intent intent = new Intent();
            intent.putExtra("requestCode", mReqCode);
            intent.setClass(this, SelectUseItemActivity.class);
            startActivityForResult(intent, mReqCode);
        }
    }

    static class ExecPlanViewHolder implements NumberStepperValueChangeListener {
        /**重复执行**/
        @InjectView(R.id.tv_repeat_times)
        protected TextView mTvRepeatTimes;
        @InjectView(R.id.num_repeat_times)
        protected NumberStepper mNumRepeatTimes;
        @InjectView(R.id.tv_repeat_times_unit)
        protected TextView mTvRepeatUnit;

        /**开始时间**/
        @InjectView(R.id.tv_start_time)
        protected TextView mTvStartTime;
        @InjectView(R.id.num_start_time)
        protected NumberStepper mNumStartTime;
        @InjectView(R.id.tv_start_time_unit)
        protected TextView mTvSTimeUnit;

        /**持续时间**/
        @InjectView(R.id.tv_duration_time)
        protected TextView mTvDurationTime;
        @InjectView(R.id.num_duration)
        protected NumberStepper mNumDuration;
        @InjectView(R.id.tv_dtime_unit)
        protected TextView mTvDtimeUnit;

        private Context context;
        private PlanDeta.PlanDetaEntity.ItemsEntity.ExecplanEntity viewData;

        public ExecPlanViewHolder(Context context, View view) {
            ButterKnife.inject(this, view);
            this.context = context;
            setListener();
        }

        public void setViewData(PlanDeta.PlanDetaEntity.ItemsEntity.ExecplanEntity viewData) {
            this.viewData = viewData;
            int freq = viewData.getFreq();
            String freqUnit = viewData.getFrequnit();
            boolean nevFreq = (freq == 0 && TextUtils.isEmpty(freqUnit));

            mNumRepeatTimes.setVisibility(nevFreq ? View.INVISIBLE : View.VISIBLE);
            mTvRepeatUnit.setVisibility(nevFreq ? View.INVISIBLE : View.VISIBLE);
            mTvRepeatTimes.setText(nevFreq ? R.string.never : R.string.custom);
            mNumRepeatTimes.setValue(freq);
            mTvRepeatUnit.setText(freqUnit);

            int begin = viewData.getBegin();
            String beginUnit = viewData.getBeginunit();
            boolean nevBegin = (begin == 0 && TextUtils.isEmpty(beginUnit));

            mNumStartTime.setVisibility(nevBegin ? View.INVISIBLE : View.VISIBLE);
            mTvSTimeUnit.setVisibility(nevBegin ? View.INVISIBLE : View.VISIBLE);
            mTvStartTime.setText(nevBegin ? "从不" : viewData.getBeginprefix());
            mNumStartTime.setValue(begin);
            mTvSTimeUnit.setText(beginUnit);

            int end = viewData.getEnd();
            String endUnit = viewData.getEndunit();
            boolean nevEnd = (end == 0 && TextUtils.isEmpty(endUnit));

            mNumDuration.setVisibility(nevEnd ? View.INVISIBLE : View.VISIBLE);
            mTvDtimeUnit.setVisibility(nevEnd ? View.INVISIBLE : View.VISIBLE);
            mTvDurationTime.setText(nevEnd ? R.string.lifetime : R.string.custom);
            mNumDuration.setValue(end);
            mTvDtimeUnit.setText(endUnit);
        }

        private void setListener() {
            mNumRepeatTimes.setOnValueChangeListener(this);
            mNumDuration.setOnValueChangeListener(this);
            mNumStartTime.setOnValueChangeListener(this);
        }

        public void updateRepeatView(int visibility, String value) {
            mNumRepeatTimes.setVisibility(visibility);
            mTvRepeatUnit.setVisibility(visibility);
            mTvRepeatTimes.setText(value);
        }

        public void updateDurationView(int visibility, String value) {
            mNumDuration.setVisibility(visibility);
            mTvDtimeUnit.setVisibility(visibility);
            mTvDurationTime.setText(value);
        }

        public void updateBeginView(int visibility, String value) {
           // mNumStartTime.setVisibility(visibility);
           // mTvStartTime.setVisibility(visibility);
            mTvSTimeUnit.setText(value);
        }


        @Override
        public void onValueChange(View view, int value) {
            int id = view.getId();
            switch (id) {
                case R.id.num_repeat_times:
                    viewData.setFreq(value);
                    break;
                case R.id.num_duration:
                    viewData.setEnd(value);
                    break;
                case R.id.num_start_time:
                    viewData.setBegin(value);
                    break;
                default:
                    break;
            }
        }

        /**重复执行类型切换**/
        @OnClick(R.id.tv_repeat_times)
        protected void onRepeatTypeClick() {
            final List<String> list = Arrays.asList(context.getResources().getStringArray(R.array.repeat_execute));
            DialogUtils.showBottomListDialog(context, list,
                    new OnItemClickListener() {
                        @Override
                        public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                            dialog.dismiss();
                            if (position == 5) {//切换到自定义类型
                                updateRepeatView(View.VISIBLE, list.get(position));
                            } else if (position != 6) {
                                String text = list.get(position);
                                updateRepeatView(View.INVISIBLE, text);
                                if (position == 0) {
                                    viewData.setFreq(0);
                                    viewData.setFrequnit("");
                                } else {
                                    viewData.setFreq(Integer.parseInt(text.substring(0,1)));
                                    viewData.setFrequnit(text.substring(1));
                                }
                            }
                        }
                    }, null);
        }

        /**重复执行单位切换**/
        @OnClick(R.id.tv_repeat_times_unit)
        protected void onRepeatUnitClick() {
            showUnitListView(mTvRepeatUnit);
        }

        /***开始时间单位切换*/
        @OnClick(R.id.tv_start_time_unit)
        protected void onStartTimeClick(){
            showUnitListView(mTvSTimeUnit);
        }

        /**持续时间类型切换**/
        @OnClick(R.id.tv_duration_time)
        protected void onDurationTypeClick() {
            final List<String> list = Arrays.asList(context.getResources().getStringArray(R.array.duration_time));
            DialogUtils.showBottomListDialog(context, list,
                    new OnItemClickListener() {
                        @Override
                        public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                            dialog.dismiss();
                            String text = list.get(position);
                            if (position == 4) {
                                updateDurationView(View.VISIBLE, text);
                            } else if (position != 5) {
                                updateDurationView(View.INVISIBLE, text);
                                if (position == 0) {
                                    viewData.setEnd(0);
                                    viewData.setEndunit("");
                                } else {
                                    viewData.setEnd(Integer.parseInt(text.substring(0,1)));
                                    viewData.setEndunit(text.substring(1));
                                }
                            }
                        }
                    }, null);
        }

        private void showUnitListView(final TextView tv) {
            final List<String> list = Arrays.asList(context.getResources().getStringArray(R.array.unit_date));
            DialogUtils.showBottomListDialog(context, list,
                    new OnItemClickListener() {
                        @Override
                        public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                            dialog.dismiss();
                            if (position != 4) {
                                int id = tv.getId();
                                String text = list.get(position);
                                if (id == R.id.tv_repeat_times_unit) {
                                    mTvRepeatUnit.setText(text);
                                    viewData.setFrequnit(text);
                                } else if (id == R.id.tv_start_time_unit) {
                                    mTvSTimeUnit.setText(text);
                                    viewData.setBeginunit(text);
                                }
                            }
                        }
                    }, null);
        }

        public PlanDeta.PlanDetaEntity.ItemsEntity.ExecplanEntity getViewData() {
            return viewData;
        }
    }
}
