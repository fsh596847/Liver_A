package com.android.doctor.ui.app;

import android.text.TextUtils;
import android.widget.EditText;

import com.android.doctor.app.AppContext;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.R;
import com.android.doctor.model.RespEntity;
import com.android.doctor.model.User;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RespHandler;
import com.android.doctor.rest.RestClient;
import com.android.doctor.ui.base.BaseActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;


public class ChangePasswordActivity extends BaseActivity {

    private static final String TAG = ChangePasswordActivity.class.getSimpleName();

    @InjectView(R.id.edt_input_old_password)
    protected EditText mEdtOldPwd;
    @InjectView(R.id.edt_input_new_password)
    protected EditText mEdtNewPwd;
    @InjectView(R.id.edt_input_new_password_again)
    protected EditText mEdtNewPwd2;

    @Override
    protected void setContentLayout() {
        setContentView(getLayoutId());
    }

    protected int getLayoutId() {
        return R.layout.activity_change_password;
    }

    protected void initView() {
        setActionBar(R.string.change_password);
    }

    protected boolean preCheck() {
        String oldPwd = mEdtOldPwd.getText().toString();
        String newPwd = mEdtNewPwd.getText().toString();
        String newPwd2 = mEdtNewPwd2.getText().toString();
        if (TextUtils.isEmpty(oldPwd)) {
            UIHelper.showToast("请输入旧密码");
            return false;
        }
        if (TextUtils.isEmpty(newPwd)) {
            UIHelper.showToast("请输入新密码");
            return false;
        }
        if (TextUtils.isEmpty(newPwd2)) {
            UIHelper.showToast("请再次输入新密码");
            return false;
        }
        if (!newPwd.equals(newPwd2)) {
            UIHelper.showToast("两次输入的新密码不一致");
            return false;
        }
        return true;
    }

    @OnClick(R.id.tv_save)
    protected void saveChange() {
        if (!preCheck()) return;

        showProcessDialog();
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity> call = service.updatepwd(getParam());
        call.enqueue(new RespHandler() {
            @Override
            public void onSucceed (RespEntity resp){
                onProResult(resp);
            }

            @Override
            public void onFailed (RespEntity resp){
                onProResult(resp);
            }
        });
    }

    public Map<String,String> getParam() {
        User.UserEntity u = AppContext.context().getUser();
        Map<String, String> map = new HashMap<>();
        map.put("duid", u.getDuid());
        map.put("oldpassword", mEdtOldPwd.getText().toString());
        map.put("password", mEdtNewPwd.getText().toString());
        return map;
    }
}
