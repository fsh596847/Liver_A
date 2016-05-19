package com.android.doctor.ui.chat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.android.doctor.R;
import com.android.doctor.model.DoctorTimeSheet;
import com.android.doctor.model.RespEntity;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RestClient;
import com.android.doctor.ui.adapter.DoctorTimeListAdapter;
import com.android.doctor.ui.base.BaseFragment;
import com.android.doctor.ui.widget.EmptyLayout;

import java.util.List;

import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Yong on 2016/3/30.
 */
public class FragmentDoctorTime extends BaseFragment {
    public static final String ARG_ID = "ARG_ID";
    @InjectView(R.id.listview)
    protected ListView mListView;

    private DoctorTimeListAdapter mAdapter;

    public static FragmentDoctorTime newInstance(String duid) {
        FragmentDoctorTime f = new FragmentDoctorTime();
        Bundle b = new Bundle();
        b.putString(ARG_ID, duid);
        f.setArguments(b);
        return f;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_doctor_time;
    }


    @Override
    protected void initView(View view) {
        super.initView(view);
        mAdapter = new DoctorTimeListAdapter(getActivity(), R.layout.item_doctor_time);
        mListView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        mListView.post(new Runnable() {
            @Override
            public void run() {
                Bundle b = getArguments();
                if (b != null) {
                    onLoad(b.getString(ARG_ID));
                }
            }
        });
    }

    private void onLoad(String duid) {
        setMaskLayout(View.VISIBLE, EmptyLayout.NETWORK_LOADING, "");
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<DoctorTimeSheet>> call = service.getDoctorTimeSheet(duid);
        call.enqueue(new Callback<RespEntity<DoctorTimeSheet>>() {
            @Override
            public void onResponse(Call<RespEntity<DoctorTimeSheet>> call, Response<RespEntity<DoctorTimeSheet>> response) {
                RespEntity<DoctorTimeSheet> data = response.body();
                if (response.isSuccessful()) {
                    onSuccess(data.getResponse_params());
                } else {
                    String errMsg = "";
                    if (data != null) {
                        errMsg = data.getError_msg();
                        onFail(errMsg);
                    }
                }
            }

            @Override
            public void onFailure(Call<RespEntity<DoctorTimeSheet>> call, Throwable t) {
                onFail(t.toString());
            }
        });
    }

    private void onSuccess(final DoctorTimeSheet sheet) {
        mListView.post(new Runnable() {
            @Override
            public void run() {
                if (sheet != null) {
                    DoctorTimeSheet.TimeSheetEntity ts = sheet.getData();
                    if (ts != null) {
                        List<DoctorTimeSheet.WeekDayEntity> list = ts.getStaticX().getWeekDayList();
                        if (list != null && 0 < list.size()) {
                            DoctorTimeSheet.DayTimeEntity d1 = new DoctorTimeSheet.DayTimeEntity();
                            DoctorTimeSheet.DayTimeEntity d2 = new DoctorTimeSheet.DayTimeEntity();
                            DoctorTimeSheet.DayTimeEntity d3 = new DoctorTimeSheet.DayTimeEntity();
                            DoctorTimeSheet.DayTimeEntity d4 = new DoctorTimeSheet.DayTimeEntity();
                            d1.setStyle("上午");d2.setStyle("下午");d3.setStyle("小夜");d4.setStyle("夜间");
                            list.add(0, new DoctorTimeSheet.WeekDayEntity(d1,d2,d3,d4));
                            mAdapter.setmData(list);
                        }
                    }
                }
            }
        });
        setMaskLayout(View.GONE, EmptyLayout.HIDE_LAYOUT, "");
    }

    private void onFail(String msg) {
        Log.d(this.getClass().getSimpleName(), msg);
        setMaskLayout(View.VISIBLE, EmptyLayout.NETWORK_ERROR, msg);
    }
}
