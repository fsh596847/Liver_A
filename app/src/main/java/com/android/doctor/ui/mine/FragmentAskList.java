package com.android.doctor.ui.mine;

import android.view.View;
import android.widget.AdapterView;

import com.android.doctor.R;
import com.android.doctor.app.AppContext;
import com.android.doctor.model.AskList;
import com.android.doctor.model.RespEntity;
import com.android.doctor.model.User;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RespHandler;
import com.android.doctor.rest.RestClient;
import com.android.doctor.ui.adapter.AskListAdapter;
import com.android.doctor.ui.base.BaseFragment;
import com.android.doctor.ui.base.BaseListAdapter;
import com.android.doctor.ui.base.BaseListFragment;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by Yong on 2016/5/20.
 */
public class FragmentAskList extends BaseListFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list_view;
    }

    @Override
    protected BaseListAdapter getListAdapter() {
        return new AskListAdapter();
    }

    @Override
    protected void onLoadData(int pageNum, int limit) {

        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<AskList>> call = service.getAskList(getQueryParam(pageNum, limit));
        call.enqueue(new RespHandler<AskList>() {
            @Override
            public void onSucceed(RespEntity<AskList> resp) {
                if (resp.getResponse_params() != null) {
                    onLoadDataSuccess(resp.getResponse_params().getAsks());
                }
            }

            @Override
            public void onFailed(RespEntity<AskList> resp) {
                onLoadDataError(resp.getError_msg());
            }
        });
    }


    public Map<String,String> getQueryParam(int pageNum, int limit) {
        User.UserEntity u = AppContext.context().getUser();
        Map<String, String> map = new HashMap<>();
        map.put("duid", u.getDuid());
        map.put("page_size", "" + limit);
        AskList.AsksEntity lItem = (AskList.AsksEntity) mAdapter.getLastItem();
        if (lItem != null)
            map.put("page_value_min", "" + lItem.getLastreplytime());
        return map;
    }

    @Override
    public void onItemClick(AdapterView parent, View view, int position, long id) {
        super.onItemClick(parent, view, position, id);
        Object obj = mAdapter.getItem(position);
        if (obj != null && obj.getClass().equals(AskList.AsksEntity.class)) {
            AskList.AsksEntity e = (AskList.AsksEntity) obj;
            AskReplyListActivity.startAty(getActivity(), "" + e.getAskid());
        }
    }
}
