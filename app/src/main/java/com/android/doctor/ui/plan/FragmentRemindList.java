package com.android.doctor.ui.plan;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.doctor.R;
import com.android.doctor.app.AppContext;
import com.android.doctor.model.RemindResultList;
import com.android.doctor.model.RespEntity;
import com.android.doctor.model.User;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RespHandler;
import com.android.doctor.rest.RestClient;
import com.android.doctor.ui.adapter.ScheduleListAdapter;
import com.android.doctor.ui.adapter.sticky_adapter.StickyRecyclerHeadersAdapter;
import com.android.doctor.ui.adapter.sticky_adapter.StickyRecyclerHeadersDecoration;
import com.android.doctor.ui.base.BaseRecyViewFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by Yong on 2016/3/9.
 */
public class FragmentRemindList extends BaseRecyViewFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recycler_view;
    }

    @Override
    protected void setAdapter() {
        mAdapter = new ScheduleListAdapter();
        mAdapter.setItemOptionClickListener(this);
        recyclerView.setAdapter(mAdapter);
        final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration((StickyRecyclerHeadersAdapter)mAdapter);
        recyclerView.addItemDecoration(headersDecor);
        mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                headersDecor.invalidateHeaders();
            }
        });
        mAdapter.setFooterVisible(false);
    }

    public void onLoadRemindList(int y, int m) {
        List<String> dates = new ArrayList<>();
        String year = String.valueOf(y);
        String month = String.valueOf(m < 10 ? "0" + m : m);
        dates.add(year + month);
        User.UserEntity userEntity = AppContext.context().getUser();
        if (userEntity == null) { return;}

        Map<String, String> map2 = new HashMap<>();
        map2.put("duid", userEntity.getDuid());
        map2.put("begintime", year+"/"+month+"/"+"01");
        map2.put("endtime", year+"/"+month+"/"+"30");
        map2.put("utype", "0");

        onLoadRemindResult(map2);
    }

    private void onLoadRemindResult(Map<String,String> map) {
        onRefresh();

        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<RemindResultList>> call = service.getDoctorRemindResultList(map);
        call.enqueue(new RespHandler<RemindResultList>() {
            @Override
            public void onSucceed(RespEntity<RemindResultList> resp) {
                if (resp == null || resp.getResponse_params() == null){
                    onSuccess(null);
                } else {
                    onSuccess(resp.getResponse_params().getData());
                }
            }

            @Override
            public void onFailed(RespEntity<RemindResultList> resp) {
                onFail(resp.getError_msg());
            }
        });
    }

    @Override
    public void onItemClick(int position, View view) {
        if (view.getId() == R.id.ll_remind_content) {
            RemindResultList.RemindResultEntity result = (RemindResultList.RemindResultEntity) mAdapter.getItem(position);
            if (result != null) {
                RemindEventActivity.startAty(getActivity(), result);
            }
        }
    }
}
