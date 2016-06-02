package com.android.doctor.ui.topic;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.model.Constants;
import com.android.doctor.model.DoctorInfo;
import com.android.doctor.model.DoctorList;
import com.android.doctor.model.RespEntity;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RespHandler;
import com.android.doctor.rest.RestClient;
import com.android.doctor.ui.base.BaseActivity;

import butterknife.InjectView;
import butterknife.OnCheckedChanged;
import retrofit2.Call;

/**
 * Created by Yong on 2016/3/26.
 */
public class UserProfileActivity extends BaseActivity {

    private static final String ARG_ID = "ARG_ID";
    private static final String ARG_IS_FRIEND = "ARG_IS_FRIEND";

    @InjectView(R.id.tv_name)
    protected TextView mTvDName;
    @InjectView(R.id.rdbtn_1)
    protected RadioButton mRdb1;
    @InjectView(R.id.rdbtn_2)
    protected RadioButton mRdb2;
    @InjectView(R.id.rdbtn_3)
    protected RadioButton mRdb3;
    @InjectView(R.id.fl_container)
    protected FrameLayout frameLayout;


    private String duid;
    private FragmentTopicList fragIPub;
    private FragmentTopicList fragIInvole;
    private FragmentSuggList fragSugg;

    public static void startAty(Context ctx, String id) {
        Intent intent = new Intent(ctx, UserProfileActivity.class);
        intent.putExtra(ARG_ID, id);
        ctx.startActivity(intent);
    }

    @Override
    protected void setContentLayout(){
        setContentView(R.layout.activity_user_profile);
    }

    @Override
    protected void init() {
        super.init();
        duid = getIntent().getStringExtra(ARG_ID);
    }

    @Override
    protected void initView() {
        setActionBar("");
        fragIPub = FragmentTopicList.newInstance(Constants.TOPIC_TYPE_I_PUB);
        fragIInvole = FragmentTopicList.newInstance(Constants.TOPIC_TYPE_I_INVOVLE);
        fragSugg = FragmentSuggList.newInstance(Constants.USER_COLLECTED_ARTICLE, duid);
    }

    /***
     * 初始化数据
     */
    @Override
    protected void initData() {
        super.initData();
        onLoad();
        setTab1();
    }

    public void setViewData(DoctorInfo info) {
        if (info != null) {
            DoctorList.DoctorEntity d = info.getData();
            if (d != null) {
                mTvDName.setText(d.getUsername());
            }
        }
    }

    /***
     * 请求数据
     */
    private void onLoad() {
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<DoctorInfo>> call = service.getDoctorBaseInfo(duid);
        call.enqueue(new RespHandler<DoctorInfo>() {
            @Override
            public void onSucceed(RespEntity<DoctorInfo> resp) {
                if (resp.getResponse_params() != null) {
                    setViewData(resp.getResponse_params());
                }
            }

            @Override
            public void onFailed(RespEntity<DoctorInfo> resp) {
                onLoadResult(resp);
            }
        });
    }

    /***
     * 切换Fragment
     * @param fragment
     */
    private void updateFragment(Fragment fragment) {
        if (fragment == null || isFinishing()) return;
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction trans = fm.beginTransaction();
        trans.replace(R.id.fl_container, fragment);
        trans.commitAllowingStateLoss();
    }

    @OnCheckedChanged(R.id.rdbtn_1)
    protected void setTab1() {
        if (mRdb1.isChecked()) {
            updateFragment(fragIPub);
        }
    }

    @OnCheckedChanged(R.id.rdbtn_2)
    protected void setTab2() {
        if (mRdb2.isChecked()) {
            updateFragment(fragIInvole);
        }
    }


    @OnCheckedChanged(R.id.rdbtn_3)
    protected void setTab3() {
        if (mRdb3.isChecked()) {
            updateFragment(fragSugg);
        }
    }
}
