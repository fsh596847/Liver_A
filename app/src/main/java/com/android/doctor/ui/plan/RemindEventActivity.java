package com.android.doctor.ui.plan;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.helper.DateUtils;
import com.android.doctor.model.PlanBaseInfo;
import com.android.doctor.model.PlanList;
import com.android.doctor.model.RemindResultList;
import com.android.doctor.model.RespEntity;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RespHandler;
import com.android.doctor.rest.RestClient;
import com.android.doctor.ui.base.BaseActivity;
import com.yuntongxun.kitsdk.utils.LogUtil;

import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * Created by Yong on 2016/3/30.
 */
public class RemindEventActivity extends BaseActivity  {

    @InjectView(R.id.tv_title)
    protected TextView mTvTitle;
    @InjectView(R.id.tv_patient)
    protected TextView mTvPatient;
    @InjectView(R.id.tv_doctor)
    protected TextView mTvDoctor;
    @InjectView(R.id.tv_date)
    protected TextView mTvDate;
    @InjectView(R.id.tv_import_state)
    protected TextView mTvState;
    @InjectView(R.id.tv_remind_date)
    protected TextView mTvRemindDate;
    @InjectView(R.id.tv_remind_content)
    protected TextView mTvRemindContent;

    private RemindResultList.RemindResultEntity mPlRemind;
    private PlanList.PlanBaseEntity mPlItem;

    public static void startAty(Context context, RemindResultList.RemindResultEntity pItem) {
        Intent intent = new Intent(context, RemindEventActivity.class);
        intent.putExtra("data", pItem);
        context.startActivity(intent);
    }

    @Override
    protected void setContentLayout() {
        setContentView(R.layout.activity_plan_remind);
    }

    @Override
    protected void init() {
        Intent intent = getIntent();
        if (intent != null) {
            mPlRemind = intent.getParcelableExtra("data");
        }
    }

    @Override
    protected void initView() {
        super.initView();
        setActionBar(R.string.remind_event);
    }

    @Override
    protected void initData() {
        super.initData();
        onLoadPlanBase();
    }

    private void setViewData() {
        if (mPlItem != null) {
            mTvTitle.setText(mPlItem.getPlanname());
            mTvPatient.setText(mPlItem.getPname());
            mTvDoctor.setText(mPlItem.getDname());
            mTvDate.setText(DateUtils.getDateString(mPlItem.getCreateTime()));
            int stat = mPlItem.getStatus();
            String s = String.format(getString(R.string.plan_status), stat == 1 ? "执行中" : stat == 2 ? "已完成" : "未创建", mPlItem.getSendstatus());
            mTvState.setText(s);
        }
        if (mPlRemind != null) {
            mTvRemindDate.setText(mPlRemind.getTime());
            mTvRemindContent.setText(mPlRemind.getContent());
        }
    }

    private void onLoadPlanBase() {
        if (mPlRemind == null) return;
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<PlanBaseInfo>> call = service.getPlanById("" + mPlRemind.getPid());
        call.enqueue(new RespHandler<PlanBaseInfo>() {
            @Override
            public void onSucceed(RespEntity<PlanBaseInfo> resp) {
                if (resp != null && resp.getResponse_params() != null) {
                    mPlItem = resp.getResponse_params().getData();
                    setViewData();
                }
            }

            @Override
            public void onFailed(RespEntity<PlanBaseInfo> resp) {

            }
        });
    }

    @OnClick(R.id.include_plan_base)
    protected void onClickPlanBase() {
        if (mPlItem == null) return;
        PlanDetaActivity.startAty(this,"" + mPlItem.getPid(),mPlItem.getStatus(), mPlItem);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == RESULT_OK) {
            LogUtil.d("AddPlanActivity: ", data.getDataString());
        }
    }

}
