package com.android.doctor.ui.mine;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.doctor.R;
import com.android.doctor.app.AppConfig;
import com.android.doctor.model.AskReplyList;
import com.android.doctor.model.RespEntity;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RespHandler;
import com.android.doctor.rest.RestClient;
import com.android.doctor.ui.adapter.AskListAdapter;
import com.android.doctor.ui.adapter.AskReplyViewAdapter;
import com.android.doctor.ui.base.BaseListAdapter;
import com.android.doctor.ui.base.BaseListFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Created by Yong on 2016/5/20.
 */
public class FragmentAskReplyList extends BaseListFragment {

    private String askID;

    private View.OnTouchListener mTouchListener;

    public static FragmentAskReplyList newInstance(String askid) {
        FragmentAskReplyList f = new FragmentAskReplyList();
        Bundle b = new Bundle();
        b.putString("ask_id", askid);
        f.setArguments(b);
        return f;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list_view;
    }

    @Override
    protected BaseListAdapter getListAdapter() {
        AskReplyViewAdapter adapter = new AskReplyViewAdapter();
        return adapter;
    }

    @Override
    protected void init() {
        super.init();
        Bundle b = getArguments();
        if (b != null) {
            askID = b.getString("ask_id");
        }
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        if (mTouchListener != null) {
            mListView.setOnTouchListener(mTouchListener);
        }
    }

    @Override
    protected void onLoadData(int pageNum, int limit) {

        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<AskReplyList>> call = service.getAskReplyList(askID);
        call.enqueue(new RespHandler<AskReplyList>() {
            @Override
            public void onSucceed(RespEntity<AskReplyList> resp) {
                if (resp.getResponse_params() != null) {
                    List d = new ArrayList();
                    d.add(resp.getResponse_params().getAsk());
                    d.addAll(resp.getResponse_params().getReplies());
                    onLoadDataSuccess(d);
                    Log.d(AppConfig.TAG, "[onLoadData-> onSucceed] " + d.size());
                }
            }

            @Override
            public void onFailed(RespEntity<AskReplyList> resp) {
                Log.d(AppConfig.TAG, "[onLoadData-> onFailed] " + resp.getError_msg());
                onLoadDataError(resp.getError_msg());
            }
        });
    }

    public void setTouchListener(View.OnTouchListener listener) {
        this.mTouchListener = listener;
    }

    public void scrollListViewToLast() {
        if(mListView != null){
            mListView.postDelayed(new Runnable() {

                @Override
                public void run() {
                    int lastVisiblePosition = mListView.getLastVisiblePosition();
                    int count = mListView.getCount() - 1;
                    Log.v(AppConfig.TAG , "scrollListViewToLast  last visible/adapter=" + lastVisiblePosition + "/" + count);
                    if(lastVisiblePosition > count - 1) {
                        mListView.setSelectionFromTop(count - 1, 0);
                    } else {
                        mListView.setSelection(count);
                    }
                }
            }, 10L);
        }
    }
}
