package com.android.doctor.ui.patient;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.app.AppConfig;
import com.android.doctor.app.AppContext;
import com.android.doctor.helper.ChatUtils;
import com.android.doctor.helper.DateUtils;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.model.ContactList;
import com.android.doctor.model.PatientBaseInfo;
import com.android.doctor.model.PlanList;
import com.android.doctor.model.RespEntity;
import com.android.doctor.model.User;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RespHandler;
import com.android.doctor.rest.RestClient;
import com.android.doctor.ui.adapter.TimeLineAdapter;
import com.android.doctor.ui.base.BaseActivity;
import com.android.doctor.app.DataCacheManager;
import com.android.doctor.ui.widget.EmptyLayout;
import com.android.doctor.ui.widget.TimeLineMarker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Yong on 2016/4/4.
 */
public class PatientProfileActivity extends BaseActivity{
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

    private PatientBaseInfo.PatientEntity mPatInfo;
    private ContactList.ContactEntity contactEntity;
    private String puid;
    private boolean isFriend;

    public static void startAty(Context context, String puid) {
        Intent intent = new Intent(context, PatientProfileActivity.class);
        intent.putExtra("puid", puid);
        context.startActivity(intent);
    }

    /***
     * 视图布局
     */
    @Override
    protected void setContentLayout() {
        setContentView(R.layout.activity_patient_info);
    }

    @Override
    protected void init() {
        Intent intent = getIntent();
        puid = intent.getStringExtra("puid");
    }

    /***
     * 初始数据
     */
    @Override
    protected void initData() {
        onLoadBaseInfo();
        onLoadPlan();
    }


    /****
     * 填充视图
     */
    private void setBaseViewData() {
        if (mPatInfo == null) return;
        setActionBar(mPatInfo.getName());
        String addr = String.format(getString(R.string.address), mPatInfo.getAddress());
        String card = String.format(getString(R.string.card_num), mPatInfo.getCard());
        mTvAddress.setText(addr);
        mTvIdNum.setText(card);
        int sSex = mPatInfo.getSex().equals(String.valueOf(0)) ? R.drawable.ic_female : R.drawable.ic_male;
        mTvSex.setCompoundDrawablesWithIntrinsicBounds(sSex,0,0,0);

        checkIsFriend();

        showActionView();
    }

    private void showActionView() {
        findViewById(R.id.tv_visit_plan).setVisibility(View.VISIBLE);
        findViewById(R.id.tv_dis_archive).setVisibility(View.VISIBLE);
        findViewById(R.id.fab).setVisibility(View.VISIBLE);
    }

    private void setArchViewData(int cnt) {
        mDiseaseArc.setVisibility(View.VISIBLE);
        for (int i = 0; i < cnt; ++i) {
            PlanList.PlanBaseEntity d = null;
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
                UIHelper.showtAty(PatientProfileActivity.this, DiseaArchActivity.class);
            }
        });
        mDiseaseArc.addView(ve);
    }

    /***
     * 填充随访档案视图
     * @param pData
     */
    private void setPlanViewData(List<PlanList.PlanBaseEntity> pData) {
        if (pData == null || pData.isEmpty()) return;
        mVisitPlan.setVisibility(View.VISIBLE);
        for (int i = 0; i < pData.size(); ++i) {
            PlanList.PlanBaseEntity d = pData.get(i);
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
                if(isFastDoubleClick() || mPatInfo == null) return;
                UIHelper.showPatientPlanAty(PatientProfileActivity.this, "" + mPatInfo.getPuid());
            }
        });
        mVisitPlan.addView(ve);
    }

    /***
     * 时间轴
     * @param itemView
     * @param type
     */
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

    /***
     * 获取基本数据
     */
    private void onLoadBaseInfo() {
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<PatientBaseInfo>> call = service.getPatientBaseInfo(puid);
        call.enqueue(new RespHandler<PatientBaseInfo>() {
            @Override
            public void onSucceed(RespEntity<PatientBaseInfo> resp) {
                mPatInfo = resp.getResponse_params().getPatient();
                setBaseViewData();
                setMaskLayout(View.GONE, EmptyLayout.HIDE_LAYOUT, "");
            }

            @Override
            public void onFailed(RespEntity<PatientBaseInfo> resp) {
                setMaskLayout(View.VISIBLE, EmptyLayout.NETWORK_ERROR, resp.getError_msg());
            }
        });
    }

    /***
     * 获取随访计划数据
     */
    private void onLoadPlan() {
        User.UserEntity u = AppContext.context().getUser();
        if (u == null) return;
        HashMap<String, String> option = new HashMap<>();
        option.put("page_size", "2");
        option.put("duid", "" + u.getDuid());
        option.put("followtype", "随诊计划");
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<PlanList>> call = service.getDoctorPatientRecord(puid);
        call.enqueue(new RespHandler<PlanList>() {
            @Override
            public void onSucceed(RespEntity<PlanList> resp) {
                if (resp != null && resp.getResponse_params() != null) {
                    setPlanViewData(resp.getResponse_params().getData());
                }
            }

            @Override
            public void onFailed(RespEntity<PlanList> resp) {
                setMaskLayout(View.VISIBLE, EmptyLayout.NETWORK_ERROR, resp.getError_msg());
            }
        });
    }


    /***
     * 检查是否已经是好友
     */
    private void checkIsFriend() {
        contactEntity = DataCacheManager.getInstance().findContact(AppConfig.APP_CONTACT_PATIENT, puid);
        if (contactEntity != null) {
            isFriend = true;
            mTvIsContact.setText(R.string.contact);
        } else {
            Log.d(AppConfig.TAG, "[PatientProfileActivity-> checkIsFriend] contact is null " + puid);
        }
    }

    public Map<String,String> getAddFriendParam() {
        Map<String, String> map = new HashMap<>();
        User.UserEntity userEntity = AppContext.context().getUser();
        if (userEntity == null) return null;
        map.put("uid", userEntity.getDuid());
        map.put("utype", "1");
        map.put("linktype", "患者");
        map.put("friendid", puid);
        map.put("ishasrelationship", "0");
        return map;
    }

    private void onAddMember() {
        showProcessDialog();
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity> call = service.addFriend(getAddFriendParam());
        call.enqueue(new Callback<RespEntity>() {
            @Override
            public void onResponse(Call<RespEntity> call, Response<RespEntity> response) {
                dismissProcessDialog();
                RespEntity r = response.body();
                if (r != null) {
                    UIHelper.showToast(r.getError_msg());
                }
            }

            @Override
            public void onFailure(Call<RespEntity> call, Throwable t) {
                dismissProcessDialog();
                Log.d(AppConfig.TAG,"[PatientProfileActivity-> onAddMember-> onFailure]" + t.toString());
            }
        });
    }

    private void showChoiceDialog() {
        String title = "还不是联系人";
        String text = getString(R.string.is_add_contact);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(text);
        builder.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onAddMember();
            }
        });
        builder.setNegativeButton(R.string.after, null);
        builder.setCancelable(false).setTitle(title);
        Dialog d = builder.create();
        d.show();
    }

    /***
     * 添加联系人
     */
    @OnClick(R.id.fab)
    protected void onFabClick() {
        if (isFriend) {
            ChatUtils.chat2(this, contactEntity.get_id(), contactEntity.getUid(),
                    contactEntity.getName(), contactEntity.getLinkuuid(), String.valueOf(1));
        } else {
            showChoiceDialog();
        }
    }

    /***
     * 查看随访计划
     */
    @OnClick(R.id.tv_visit_plan)
    protected void onLookPlan() {
        UIHelper.showPatientPlanAty(this, "xxx");
    }

    /***
     * 查看档案
     */
    @OnClick(R.id.tv_dis_archive)
    protected void onLookArchive() {
        if (mPatInfo == null) return;
        DiseaArchActivity.startAty(this, mPatInfo);
    }

}
