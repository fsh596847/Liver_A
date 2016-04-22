package com.android.doctor.ui.patient;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.app.AppContext;
import com.android.doctor.helper.DateUtils;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.model.PatientList;
import com.android.doctor.model.PlanList;
import com.android.doctor.model.RespEntity;
import com.android.doctor.model.User;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RestClient;
import com.android.doctor.ui.adapter.TimeLineAdapter;
import com.android.doctor.ui.base.BaseActivity;
import com.android.doctor.ui.widget.TimeLineMarker;

import java.util.HashMap;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Yong on 2016/4/4.
 */
public class PatientInfoActivity extends BaseActivity{
    @InjectView(R.id.img_patient)
    protected ImageView mImgAvatar;
    @InjectView(R.id.tv_address)
    protected TextView mTvAddress;
    @InjectView(R.id.tv_id_num)
    protected TextView mTvIdNum;
    @InjectView(R.id.tv_is_contact)
    protected TextView mTvIsContact;
    @InjectView(R.id.tv_sex)
    protected TextView mTvSex;
    @InjectView(R.id.ll_dis_arc)
    protected LinearLayout mDiseaseArc;
    @InjectView(R.id.ll_plan)
    protected LinearLayout mVisitPlan;

    private PatientList.DataEntity mPatient;

    public static void startAty(Context context, PatientList.DataEntity e) {
        Intent intent = new Intent(context, PatientInfoActivity.class);
        intent.putExtra("data", e);
        context.startActivity(intent);
    }

    @Override
    protected void setContentLayout() {
        setContentView(R.layout.activity_patient_info);
    }

    @Override
    protected void init() {
        Intent intent = getIntent();
        mPatient = intent.getParcelableExtra("data");
    }

    @Override
    protected void initView() {
        super.initView();
        setActionBar(mPatient.getName());
        setBaseViewData();
    }

    @Override
    protected void initData() {
        onLoadPlan();
    }

    private void setBaseViewData() {
        String addr = String.format(getString(R.string.address), mPatient.getAddress());
        String card = String.format(getString(R.string.card_num), mPatient.getCard());
        mTvAddress.setText(addr);
        mTvIdNum.setText(card);
        int sSex = mPatient.getSex() == 0 ? R.drawable.ic_female : R.drawable.ic_male;
        mTvSex.setCompoundDrawablesWithIntrinsicBounds(sSex,0,0,0);
    }

    private void setArchViewData(int cnt) {
        for (int i = 0; i < cnt; ++i) {
            PlanList.DataEntity d = null;
            View v = LayoutInflater.from(this).inflate(R.layout.item_timeline_base, mDiseaseArc, false);
            initItem(v, i == 0 ? TimeLineAdapter.START : TimeLineAdapter.NORMAL);
            TextView t1 = (TextView) v.findViewById(R.id.tv_1);
            TextView t2 = (TextView) v.findViewById(R.id.tv_2);
            TextView t3 = (TextView) v.findViewById(R.id.tv_3);
            t1.setText(DateUtils.getDateString(d.getCreateTime()));
            t2.setText(d.getPlanname());
            int stat = d.getStatus();
            t3.setText(stat == 1 ? "执行中" : stat == 2 ? "已完成" : "未创建");
            mDiseaseArc.addView(v);
        }
        View ve = LayoutInflater.from(this).inflate(R.layout.item_timeline_border, mDiseaseArc, false);
        TimeLineMarker mMarker = (TimeLineMarker) ve.findViewById(R.id.item_time_line_mark);
        mMarker.setEndLine(null);
        ((TextView)ve.findViewById(R.id.tx_action)).setText(R.string.look_more_archive);
        ve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIHelper.showtAty(PatientInfoActivity.this, DisArchiveActivity.class);
            }
        });
        mDiseaseArc.addView(ve);
    }

    private void setPlanViewData(List<PlanList.DataEntity> pData) {
        if (pData == null) return;
        for (int i = 0; i < pData.size(); ++i) {
            PlanList.DataEntity d = pData.get(i);
            View v = LayoutInflater.from(this).inflate(R.layout.item_timeline_base, mVisitPlan, false);
            initItem(v, i == 0 ? TimeLineAdapter.START : TimeLineAdapter.NORMAL);
            TextView t1 = (TextView) v.findViewById(R.id.tv_1);
            TextView t2 = (TextView) v.findViewById(R.id.tv_2);
            TextView t3 = (TextView) v.findViewById(R.id.tv_3);
            t1.setText(DateUtils.getDateString(d.getCreateTime()));
            t2.setText(d.getPlanname());
            int stat = d.getStatus();
            t3.setText(stat == 1 ? "执行中" : stat == 2 ? "已完成" : "未创建");
            mVisitPlan.addView(v);
        }
        View ve = LayoutInflater.from(this).inflate(R.layout.item_timeline_border, mVisitPlan, false);
        TimeLineMarker mMarker = (TimeLineMarker) ve.findViewById(R.id.item_time_line_mark);
        mMarker.setEndLine(null);
        ((TextView)ve.findViewById(R.id.tx_action)).setText(R.string.look_more_plan);
        ve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFastDoubleClick()) return;
                UIHelper.showPatientPlanAty(PatientInfoActivity.this, "" + mPatient.getPuid());
            }
        });
        mVisitPlan.addView(ve);
    }

    private void initItem(View itemView, int type) {
        TimeLineMarker mMarker = (TimeLineMarker) itemView.findViewById(R.id.item_time_line_mark);
        if (type == TimeLineAdapter.ATOM) {
            mMarker.setBeginLine(null);
            mMarker.setEndLine(null);
        } else if (type == TimeLineAdapter.START) {
            mMarker.setBeginLine(null);
        } else if (type == TimeLineAdapter.END) {
            mMarker.setEndLine(null);
        }
    }

    private void onLoadPlan() {
        User u = AppContext.context().getUser();
        if (u == null || u.getUser() == null) {
            return;
        }
        HashMap<String, String> option = new HashMap<>();
        option.put("page_size", "2");
        option.put("duid", "" + u.getUser().getDuid());
        option.put("followtype", "随诊计划");
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<PlanList>> call = service.getDoctorPatientRecord("" + mPatient.getPuid());
        call.enqueue(new Callback<RespEntity<PlanList>>() {
            @Override
            public void onResponse(Call<RespEntity<PlanList>> call, Response<RespEntity<PlanList>> response) {
                RespEntity<PlanList> data = response.body();
                if (response.isSuccessful()) {
                    if (data == null) {
                        //onSuccess(new ArrayList());
                        return;
                    } else if (data.getResponse_params() != null) {
                        setPlanViewData(data.getResponse_params().getData());
                    }
                } else {
                    String errMsg = "";
                    if (data != null) {
                        errMsg = data.getError_msg();
                    }
                    //onFail(errMsg);
                }
            }

            @Override
            public void onFailure(Call<RespEntity<PlanList>> call, Throwable t) {
                //onFail("加载失败");
            }
        });
    }

    @OnClick(R.id.fab)
    protected void onAddContact() {
        String title = "还不是联系人";
        String text = getString(R.string.is_add_contact);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(text);
        builder.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton(R.string.after, null);
        builder.setCancelable(false).setTitle(title);
        Dialog d = builder.create();
        d.show();
    }

    @OnClick(R.id.tv_visit_plan)
    protected void onLookPlan() {
        UIHelper.showPatientPlanAty(this, "xxx");
    }

    @OnClick(R.id.tv_dis_archive)
    protected void onLookArchive() {
        UIHelper.showtAty(PatientInfoActivity.this, DisArchiveActivity.class);
    }
}
