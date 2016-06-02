package com.android.doctor.ui.plan;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.android.doctor.R;
import com.android.doctor.app.AppContext;
import com.android.doctor.helper.DialogUtils;
import com.android.doctor.model.Constants;
import com.android.doctor.model.RespEntity;
import com.android.doctor.model.TempPlanList;
import com.android.doctor.model.User;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RespHandler;
import com.android.doctor.rest.RestClient;
import com.android.doctor.ui.adapter.TPlanListAdapter;
import com.android.doctor.ui.adapter.sticky_adapter.StickyRecyclerHeadersAdapter;
import com.android.doctor.ui.adapter.sticky_adapter.StickyRecyclerHeadersDecoration;
import com.android.doctor.ui.base.BaseRecyViewFragment;

import java.util.ArrayList;

import retrofit2.Call;

/**
 * Created by Yong on 2016/3/8.
 */
public class FragmentTPlanList extends BaseRecyViewFragment {

    private int type;

    public static FragmentTPlanList newInstance(int type) {
        FragmentTPlanList f = new FragmentTPlanList();
        Bundle b = new Bundle();
        b.putInt("type", type);
        f.setArguments(b);
        return f;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recycler_view;
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void setAdapter() {
        mAdapter = new TPlanListAdapter(getActivity());
        mAdapter.setItemOptionClickListener(this);
        recyclerView.setAdapter(mAdapter);
        final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration((StickyRecyclerHeadersAdapter)mAdapter);
        recyclerView.addItemDecoration(headersDecor);
        //   setTouchHelper();
        mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                headersDecor.invalidateHeaders();
            }
        });
    }

    @Override
    protected void initData() {
        Bundle b = getArguments();
        if (b != null) {
            type = b.getInt("type");
        }
        onRefresh();
    }

    protected void onLoad(int pageNum, int limit) {
        onLoadMyTPlans();
    }

    private void onLoadMyTPlans() {
        User.UserEntity userEntity = AppContext.context().getUser();
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<TempPlanList>> call = service.getMyTPl(userEntity.getDuid());
        call.enqueue(new RespHandler<TempPlanList>() {
            @Override
            public void onSucceed(RespEntity<TempPlanList> resp) {
                if (resp == null || resp.getResponse_params() == null) {
                    onSuccess(new ArrayList());
                } else  {
                    onSuccess(resp.getResponse_params().getTpls());
                }
                onMyPlanLoaded();
            }

            @Override
            public void onFailed(RespEntity<TempPlanList> resp) {
                onFail(resp.getError_msg());
            }
        });
    }

    private void onMyPlanLoaded() {
        mState = PAGE_STATE_LOADMORE;
        onLoadSysTPlans();
    }

    private void onLoadSysTPlans() {
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<TempPlanList>> call = service.getSysTPl();
        call.enqueue(new RespHandler<TempPlanList>() {
            @Override
            public void onSucceed(RespEntity<TempPlanList> resp) {
                if (resp == null || resp.getResponse_params() == null) {
                    onSuccess(new ArrayList());
                    return;
                } else  {
                    onSuccess(resp.getResponse_params().getTpls());
                }
            }

            @Override
            public void onFailed(RespEntity<TempPlanList> resp) {
                onFail(resp.getError_msg());
            }
        });
    }

    private void showConfirmDialog() {
        String msg_tip = getString(R.string.are_you_sure_to_delete_this_item);
        AlertDialog.Builder builder = DialogUtils.getConfirmDialog(getActivity(), msg_tip,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).setCancelable(false).setTitle(R.string.tips);
        Dialog d = builder.create();
        d.show();
    }


    @Override
    public void onItemClick(int position, View view) {
        int vid = view.getId();
        if (vid == R.id.tv_delete) {//滑动删除
            showConfirmDialog();
        } else { //点击
            if (type == Constants.REQ_CODE_FOR_CREATE) {//从我的随访计划过来的
                TempPlanList.TplsEntity tpls = (TempPlanList.TplsEntity) mAdapter.getItem(position);
                NewPlanActivity.startAty(getActivity(), tpls);
            } else {
                TempPlanList.TplsEntity tplsEntity = (TempPlanList.TplsEntity) mAdapter.getItem(position);
                PlanDetaActivity.startAty(getActivity(), tplsEntity.getTplid(), Constants.PLAN_STATUS_INIT, null);
            }
        }
    }

}
