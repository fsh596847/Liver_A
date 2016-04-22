package com.android.doctor.ui.plan;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.helper.DateUtils;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.model.NewPlanRecord;
import com.android.doctor.model.PlanRecordList;
import com.android.doctor.model.RespEntity;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RestClient;
import com.android.doctor.ui.base.BaseActivity;
import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;

import java.util.Date;

import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Yong on 2016/4/2.
 */
public class PlanRecordInfoActivity extends BaseActivity {
    public static final String ARG_PARAM = "ARG_PARAM";

    @InjectView(R.id.tv_title)
    protected TextView mTvTitle;
    @InjectView(R.id.tv_patient_name)
    protected TextView mTvPatientName;
    @InjectView(R.id.tv_diagnose_time)
    protected TextView mTvDiagTime;
    @InjectView(R.id.rdg_btn)
    protected RadioGroup mRdg;
    @InjectView(R.id.edt_content)
    protected EditText mEdtContent;

    private SlideDateTimePicker mDateTimePicker;
    private NewPlanRecord mRecord;

    public static void startAty(Context context, NewPlanRecord record) {
        Intent intent = new Intent(context, PlanRecordInfoActivity.class);
        intent.putExtra(ARG_PARAM, record);
        context.startActivity(intent);
    }

    @Override
    protected void setContentLayout() {
        setContentView(R.layout.activity_diagnose_record);
    }

    @Override
    protected void init() {
        mRecord = getIntent().getParcelableExtra(ARG_PARAM);
    }

    @Override
    protected void initView() {
        super.initView();
        setActionBar(R.string.diag_record);

        mDateTimePicker = new SlideDateTimePicker.Builder(getSupportFragmentManager())
                .setListener(new SlideDateTimeListener() {
                    @Override
                    public void onDateTimeSet(Date date) {
                        mTvDiagTime.setText(DateUtils.formatDate(date));
                    }
                }).setInitialDate(new Date()).build();
        setViewData();
    }

    private void setViewData() {
        mTvTitle.setText(mRecord.getPlanname());
        mTvPatientName.setText(mRecord.getPname());
    }

    @OnClick(R.id.rl_diagnose_time)
    protected void onSetTime() {
        mDateTimePicker.show();
    }


    @OnClick(R.id.btn_save)
    protected void onSave() {
        int id = mRdg.getCheckedRadioButtonId();
        String tvDate = mTvDiagTime.getText().toString();
        String tvContent = mEdtContent.getText().toString();
        mRecord.setDate(tvDate);
        mRecord.setFlowMethod(id == R.id.rdbtn_telephone ? 1 : 2);
        mRecord.setFlowUpResult(tvContent);

        onSaveRequest();
    }

    private void onSaveRequest() {
        showProcessDialog();
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity> call = service.addPlanRecord(mRecord);
        call.enqueue(new Callback<RespEntity>() {
            @Override
            public void onResponse(Call<RespEntity> call, Response<RespEntity> response) {
                RespEntity<PlanRecordList> data = response.body();
                onResult(data);
            }

            @Override
            public void onFailure(Call<RespEntity> call, Throwable t) {
                dismissProcessDialog();
                Log.d("onFailure", t.toString());
            }
        });
    }

    private void onResult(RespEntity resp) {
        dismissProcessDialog();
        if (resp != null) {
            String text = resp.getError_msg();
            UIHelper.showToast(this, text);
            if (resp.getError_code() == 0) {
                setResult(RESULT_OK);
                onBackPressed();
            }
        }
    }

}
