package com.android.doctor.ui.chat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.app.AppConfig;
import com.android.doctor.app.AppContext;
import com.android.doctor.helper.ChatUtils;
import com.android.doctor.helper.DialogUtils;
import com.android.doctor.helper.PermissionUtil;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.model.ContactList;
import com.android.doctor.model.DoctorInfo;
import com.android.doctor.model.DoctorList;
import com.android.doctor.model.RespEntity;
import com.android.doctor.model.User;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RespHandler;
import com.android.doctor.rest.RestClient;
import com.android.doctor.ui.base.BaseActivity;
import com.android.doctor.app.DataCacheManager;

import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnCheckedChanged;
import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Yong on 2016/3/26.
 */
public class DoctorProfileActivity extends BaseActivity {
    private static final String TAG = DoctorProfileActivity.class.getSimpleName();
    private static final String ARG_ID = "ARG_ID";
    private static final String ARG_IS_FRIEND = "ARG_IS_FRIEND";

    @InjectView(R.id.tv_name)
    protected TextView mTvDName;
    @InjectView(R.id.tv_patient_num)
    protected TextView mTvPaNum;
    @InjectView(R.id.tv_topic_num)
    protected TextView mTvArticleNum;
    @InjectView(R.id.tv_attention_num)
    protected TextView mTvAtteNum;
    @InjectView(R.id.rdbtn_doctor_intro)
    protected RadioButton mRdbDoctorIntro;
    @InjectView(R.id.rdbtn_visit_time)
    protected RadioButton mRdbDoctorVisit;
    @InjectView(R.id.fl_container)
    protected FrameLayout frameLayout;
    @InjectView(R.id.fabspeed)
    protected FabSpeedDial fab;

    private String duid;
    private boolean isFriend;
    private FragmentDoctorTime fragTime;
    private FragmentDoctorBaseInfo fragBase;
    private DoctorList.DoctorEntity doctorEntity;
    private ContactList.ContactEntity contactEntity;

    public static void startAty(Context ctx, String id) {
        Intent intent = new Intent(ctx, DoctorProfileActivity.class);
        intent.putExtra(ARG_ID, id);
        ctx.startActivity(intent);
    }

    @Override
    protected void setContentLayout(){
        setContentView(R.layout.activity_doctor_profile);
    }

    @Override
    protected void init() {
        super.init();
        duid = getIntent().getStringExtra(ARG_ID);
    }

    @Override
    protected void initView() {
        setActionBar("");
    }

    /***
     * 初始化数据
     */
    @Override
    protected void initData() {
        super.initData();
        onLoad();
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
     * 填充视图
     * @param r
     */
    private void setViewData(DoctorInfo r) {
        if (r != null) {
            doctorEntity = r.getData();
            if (doctorEntity == null) return;
            mTvDName.setText(doctorEntity.getUsername());
            mTvPaNum.setText(doctorEntity.getPstatcount());
            DoctorList.DoctorEntity.StatEntity stat = doctorEntity.getStat();
            if (stat != null) {
                mTvArticleNum.setText(stat.getTopics());
                mTvAtteNum.setText(stat.getSuggests());
            }
            DoctorList.DoctorEntity.BaseEntity baseEntity = doctorEntity.getBase();
            if (baseEntity != null) {
                String text = String.format(getString(R.string.doctor_brief_info),
                        doctorEntity.getHosname(), doctorEntity.getDeptname(), baseEntity.getTitleex(),
                        baseEntity.getExperience(), baseEntity.getGood());
                fragBase = FragmentDoctorBaseInfo.newInstance(text);
                updateFragment(fragBase);
            }
            fragTime = FragmentDoctorTime.newInstance(duid);
        }
        setFabMenuItem();

        fab.setMenuListener(new SimpleMenuListenerAdapter(){
            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.action_call) {
                    call();
                } else if (id == R.id.action_add_friend) {
                    addFriend();
                } else if (id == R.id.action_chat){
                    chat();
                } else if (id == R.id.action_delete) {
                    DialogUtils.getConfirmDialog(DoctorProfileActivity.this, "确认删除该好友吗？",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    deleteFriend();
                                }
                            }).show();
                }
                return super.onMenuItemSelected(menuItem);
            }
        });
    }

    /***
     * 设置FAB Menu资源ID
     */
    private void setFabMenuItem() {
        contactEntity = DataCacheManager.getInstance().findContact(AppConfig.APP_CONTACT_DOCTOR, duid);
        if (contactEntity != null) {
            isFriend = true;
            fab.setMenuId(R.menu.doctor_menu_contact);
        } else {
            Log.d(AppConfig.TAG, "[DoctorProfileActivity-> setFabMenuItem] contact is null " + duid);
        }

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

    @OnCheckedChanged(R.id.rdbtn_doctor_intro)
    protected void setTab1() {
        if (mRdbDoctorIntro.isChecked()) {
            updateFragment(fragBase);
        }
    }

    @OnCheckedChanged(R.id.rdbtn_visit_time)
    protected void setTab2() {
        if (mRdbDoctorVisit.isChecked()) {
            updateFragment(fragTime);
        }
    }

    /***
     * 拨打电话
     */
    private void call() {
        if (doctorEntity == null) return;
        if (!PermissionUtil.checkPermission(DoctorProfileActivity.this, Manifest.permission.CALL_PHONE)){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 0);
            return;
        }
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + doctorEntity.getMobilephone()));
        startActivity(intent);
    }

    /***
     * 在线聊天
     */
    private void chat() {
        if (doctorEntity == null) return;
        ChatUtils.chat2(this, doctorEntity.getUsername(),
                doctorEntity.getDuid(),
                doctorEntity.getUsername(),
                doctorEntity.getDuuid(),
                String.valueOf(0));
    }

    /***
     * 删除好友
     */

    private void deleteFriend() {
        if (contactEntity == null) return;
        showProcessDialog();
        ApiService service = RestClient.createService(ApiService.class);
        String param = "{" + "linkuuid:" + contactEntity.getLinkuuid() + ",friendlinkuuid:" + contactEntity.getFriendlinkuuid() +"}";
        Call<RespEntity> call = service.deleteFriend(param);
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
                Log.d(TAG, t.toString());
                dismissProcessDialog();
            }
        });
    }

    /***
     * 增加好友
     */
    private void addFriend() {
        showProcessDialog();
        Map<String, String> map = new HashMap<>();
        User.UserEntity userEntity = AppContext.context().getUser();
        if (userEntity == null) return;
        map.put("uid", userEntity.getDuid()); map.put("utype", "0");
        map.put("linktype", "医生");map.put("friendid", doctorEntity.getDuid());

        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity> call = service.addFriend(map);
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
                Log.d(TAG, t.toString());
                dismissProcessDialog();
            }
        });
    }
}
