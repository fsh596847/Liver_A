package com.android.doctor.ui.plan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.doctor.R;
import com.android.doctor.app.AppConfig;
import com.android.doctor.app.AppContext;
import com.android.doctor.app.AppManager;
import com.android.doctor.model.TestItemList;
import com.android.doctor.model.CheckOutItemList;
import com.android.doctor.model.DiagList;
import com.android.doctor.model.RespEntity;
import com.android.doctor.model.TreatPlanList;
import com.android.doctor.model.User;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RespHandler;
import com.android.doctor.rest.RestClient;
import com.android.doctor.ui.adapter.SimpleTextListAdapter;
import com.android.doctor.ui.base.BaseRecyViewFragment;

import retrofit2.Call;

/**
 * Created by Yong on 2016/3/8.
 */
public class FragmentTxItemList extends BaseRecyViewFragment {

    public static final String ARGS_PARAM = "ARGS_TYPE";
    public static final int REQUEST_LOAD_TYPE_MAIN_DIAG = 102;
    public static final int REQUEST_LOAD_TYPE_OTHER_DIAG = 103;
    public static final int REQUEST_LOAD_TYPE_TREAT = 104;
    public static final int REQUEST_LOAD_TYPE_TEST_ITEM = 105;
    public static final int REQUEST_LOAD_TYPE_CHECKOUT_ITEM = 106;

    private int type;

    public static FragmentTxItemList newInstance(int arg) {
        Log.d(AppConfig.TAG, "FragmentTxItemList-> newInstance: " + arg);
        Bundle args = new Bundle();
        args.putInt(ARGS_PARAM, arg);
        FragmentTxItemList fragment = new FragmentTxItemList();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recycler_view;
    }

    @Override
    protected void init() {
        super.init();
        Bundle b = getArguments();
        if (b != null) {
            type = b.getInt(ARGS_PARAM);
        }
    }

    @Override
    protected void setAdapter() {
        mAdapter = new SimpleTextListAdapter();
        mAdapter.setItemOptionClickListener(this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        onRefresh();
    }

    protected void onLoad(int pageNum, int limit) {
        Log.d(AppConfig.TAG, "FragmentTxItemList-> onLoad: " + type);
        if (REQUEST_LOAD_TYPE_MAIN_DIAG == type || REQUEST_LOAD_TYPE_OTHER_DIAG == type) {
            onLoadDiagList();
        } else if (type == REQUEST_LOAD_TYPE_TREAT){
            onLoadTreatment();
        } else if (type == REQUEST_LOAD_TYPE_TEST_ITEM) {
            onLoadTestItemList();
        } else if (type == REQUEST_LOAD_TYPE_CHECKOUT_ITEM) {
            onLoadCheckItemList();
        }
    }

    private void onLoadTreatment() {
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<TreatPlanList>> call = service.getTreatPlanList();
        call.enqueue(new RespHandler<TreatPlanList>() {
            @Override
            public void onSucceed(RespEntity<TreatPlanList> resp) {
                if (resp == null || resp.getResponse_params() == null) {
                    onSuccess(null);
                } else {
                    onSuccess(resp.getResponse_params().getList());
                }
            }

            @Override
            public void onFailed(RespEntity<TreatPlanList> resp) {
                onFail(resp.getError_msg());
            }
        });
    }

    private void onLoadDiagList() {
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<DiagList>> call = service.getDiagList();
        call.enqueue(new RespHandler<DiagList>() {
            @Override
            public void onSucceed(RespEntity<DiagList> resp) {
                if (resp == null || resp.getResponse_params() == null) {
                    onSuccess(null);
                } else {
                    onSuccess(resp.getResponse_params().getList());
                }
            }

            @Override
            public void onFailed(RespEntity<DiagList> resp) {
                onFail(resp.getError_msg());
            }
        });
    }

    private void onLoadTestItemList() {
        User.UserEntity u = AppContext.context().getUser();
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<TestItemList>> call = service.getCheckItemList(u.getHosid());
        call.enqueue(new RespHandler<TestItemList>() {
            @Override
            public void onSucceed(RespEntity<TestItemList> resp) {
                if (resp == null || resp.getResponse_params() == null) {
                    onSuccess(null);
                } else {
                    onSuccess(resp.getResponse_params().getClasslist());
                }
            }

            @Override
            public void onFailed(RespEntity<TestItemList> resp) {
                onFail(resp.getError_msg());
            }
        });
    }

    private void onLoadCheckItemList() {
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<CheckOutItemList>> call = service.getCheckOutItemList();
        call.enqueue(new RespHandler<CheckOutItemList>() {
            @Override
            public void onSucceed(RespEntity<CheckOutItemList> resp) {
                if (resp == null || resp.getResponse_params() == null) {
                    onSuccess(null);
                } else {
                    onSuccess(resp.getResponse_params().getList());
                }
            }

            @Override
            public void onFailed(RespEntity<CheckOutItemList> resp) {
                onFail(resp.getError_msg());
            }
        });
    }

    @Override
    public void onItemClick(int position, View view) {
        Activity aty = getActivity();
        if (aty != null) {
            Object obj =  mAdapter.getItem(position);
            if (obj != null) {
                Intent intent = new Intent();
                if (obj.getClass().equals(TreatPlanList.TreatPlanEntity.class)) {
                    intent.putExtra("data", (TreatPlanList.TreatPlanEntity)obj);
                } else if (obj.getClass().equals(DiagList.DiagEntity.class)) {
                    intent.putExtra("data", (DiagList.DiagEntity)obj);
                } else if (obj.getClass().equals(TestItemList.ClasslistEntity.class)) {//选择检验项目
                    intent.putExtra("data", (TestItemList.ClasslistEntity)obj);
                } else if (obj.getClass().equals(CheckOutItemList.CKOEntity.class)) {//选择检查项目
                    intent.putExtra("data", (CheckOutItemList.CKOEntity)obj);
                }

                aty.setResult(Activity.RESULT_OK, intent);
                AppManager.getAppManager().finishActivity(aty);
            }
        }
    }
}
