package com.android.doctor.ui.plan;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.helper.DialogHelper;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.interf.OnListItemClickListener;
import com.android.doctor.model.DataText;
import com.android.doctor.model.PlanDeta;
import com.android.doctor.model.PlanList;
import com.android.doctor.model.RespEntity;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RestClient;
import com.android.doctor.ui.adapter.PlanDetaAdapter;
import com.android.doctor.ui.base.BaseActivity;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;

import java.util.Arrays;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Yong on 2016/3/31.
 */
public class PlanSchemeActivity extends BaseActivity implements OnListItemClickListener{
    public static final int REQUEST_DOCTOR_ADVICE_CODE = 1;
    public static final int REQUEST_DOCTOR_REVIEW_CODE = 2;
    public static final int REQUEST_DOCTOR_CHECK_CODE = 3;
    public static final int REQUEST_DOCTOR_CHECKOUT_CODE = 4;
    public static final int REQUEST_DOCTOR_MEDICINE_CODE = 7;
    public static final int REQUEST_DOCTOR_REMIND_CODE = 9;
    public static final int REQUEST_DOCTOR_FOLLOW_CODE = 10;



    @InjectView(R.id.listView)
    protected ListView mListView;

    /*@InjectView(R.id.ll_doctor_advice)
    protected LinearLayout mDoctorAdviceLayout;
    @InjectView(R.id.ll_doctor_review)
    protected LinearLayout mDoctorReviewLayout;
    @InjectView(R.id.ll_doctor_check)
    protected LinearLayout mDoctorCheckLayout;
    @InjectView(R.id.ll_doctor_checkout)
    protected LinearLayout mDoctorCheckoutLayout;
    @InjectView(R.id.ll_doctor_medicine)
    protected LinearLayout mDoctorMedicineLayout;
    @InjectView(R.id.ll_doctor_remind)
    protected LinearLayout mDoctorRemindLayout;
    @InjectView(R.id.ll_doctor_follow)
    protected LinearLayout mDoctorFollowLayout;

    private ArrayList<DataText> mListDoctorAdvice;
    private ArrayList<DataText> mListDoctorReview;
    private ArrayList<DataText> mListDoctorCheck;
    private ArrayList<DataText> mListDoctorCheckout;
    private ArrayList<DataText> mListDoctorMedicine;
    private ArrayList<DataText> mListDoctorRemind;
    private ArrayList<DataText> mListDoctorFollow;*/
    private PlanDetaAdapter mAdapter;
    private PlanList.DataEntity mPlItem;

    public static void startAty(Context context, PlanList.DataEntity pItem) {
        Intent intent = new Intent(context, PlanSchemeActivity.class);
        intent.putExtra("data", pItem);
        context.startActivity(intent);
    }

    @Override
    protected void setContentLayout() {
        setContentView(R.layout.activity_plan_deta);
    }

    @Override
    protected void init() {
        Intent intent = getIntent();
        if (intent != null) {
            mPlItem = intent.getParcelableExtra("data");
        }
    }

    @Override
    protected void initView() {
        super.initView();
        setActionBar(R.string.visit_plan);
        mAdapter = new PlanDetaAdapter(this);
        mAdapter.setItemClickListener(this);
        mListView.setAdapter(mAdapter);
        //setBaseViewData();
    }

    @Override
    protected void initData() {
        super.initData();
        onLoad();
    }

    @OnClick(R.id.img_complete)
    protected void onComplete() {

    }

    private void setBaseViewData() {
        /*mTvTitle.setText(mPlItem.getPlanname());
        mTvPatient.setText(mPlItem.getPname());
        mTvDoctor.setText(mPlItem.getDname());
        mTvDate.setText(DateUtils.getDateString(mPlItem.getCreateTime()));
        int stat = mPlItem.getStatus();
        mTvState.setText(stat == 1 ? "执行中" : stat == 2 ? "已完成" : "未创建");*/
    }


    @OnClick(R.id.img_more)
    protected void onPlanSchemeOper() {
         DialogHelper.showBottomListDialog(this,
                Arrays.asList(getResources().getStringArray(R.array.plan_scheme_operation)),
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        dialog.dismiss();
                        switch (position) {
                            case 0 ://终止当前计划
                                break;
                            case 1://添加随访记录
                                UIHelper.showtAty(PlanSchemeActivity.this, DiagRecordActivity.class);
                                break;
                            case 2://查看随访记录
                                UIHelper.showtAty(PlanSchemeActivity.this, DiagRecordListActivity.class);
                                break;
                            case 3://查看提醒事件
                                break;
                            default:break;
                        }
                    }
                }, null);
    }


    private void onStartEditAtyForResult(int code, PlanDeta.DataEntity data) {
        Intent intent = new Intent();
        intent.setClass(this, EditSchemeItemActivity.class);
        intent.putExtra("requestCode", code);
        intent.putExtra("data", data);
        startActivityForResult(intent, code);
    }

    private View createCaptionView(DataText o) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.item_caption, null);
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        TextView tvContent = (TextView) view.findViewById(R.id.tv_content);
        TextView tvContentEx = (TextView) view.findViewById(R.id.tv_content_ex);
        tvTitle.setText(o.getTitle());
        tvContent.setText(o.getContent());
        tvContentEx.setText(o.getContent_ex());
        return view;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            PlanDeta.DataEntity d = data.getParcelableExtra("data");
            mAdapter.updateData(d);
        }
    }

    private boolean compareList(List<DataText> newList, List<DataText> oldList) {
        if (newList.size() != oldList.size()) return false;
        for (int i = 0; i < oldList.size(); ++i) {
            DataText o1 = newList.get(i);
            DataText o2 = oldList.get(i);
            if (!o1.equals(o2)) return false;
        }
        return true;
    }

    private void updateView(LinearLayout container, List<DataText> data) {
        container.removeAllViews();
        for (DataText o : data) {
            View v = createCaptionView(o);
            container.addView(v);
        }
    }

    private void closeView(LinearLayout container, List<DataText> data) {
        container.removeAllViews();
    }

    @Override
    public void onItemClick(int position, View view) {
        PlanDeta.DataEntity entity = (PlanDeta.DataEntity) mAdapter.getItem(position);
        if (entity != null) {
            onStartEditAtyForResult(entity.getCode(), entity);
        }
    }

    private void onLoad() {
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<PlanDeta>> call = service.getPlanDeta("" + mPlItem.getPid());
        call.enqueue(new Callback<RespEntity<PlanDeta>>() {
            @Override
            public void onResponse(Call<RespEntity<PlanDeta>> call, Response<RespEntity<PlanDeta>> response) {
                RespEntity<PlanDeta> data = response.body();
                if (response.isSuccessful()) {
                    if (data == null) {
                        return;
                    } else if (data.getResponse_params() != null) {
                        setPlanViewData(data.getResponse_params().getData());
                    }
                } else {
                    String errMsg = "";
                    if (data != null) {
                        errMsg = data.getError_msg();
                    }
                }
            }

            @Override
            public void onFailure(Call<RespEntity<PlanDeta>> call, Throwable t) {
                Log.d("PlanSchemeActivity",t.toString());
            }
        });
    }

    private void setPlanViewData(List data) {
        Log.d("PlanSchemeActivity", "setPlanViewData");
        if (data == null) return;
        data.add(0, mPlItem);
        mAdapter.setmData(data);
    }
}
