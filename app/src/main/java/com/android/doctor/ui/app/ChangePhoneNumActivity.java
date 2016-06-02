package com.android.doctor.ui.app;

import android.os.CountDownTimer;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.android.doctor.app.AppContext;
import com.android.doctor.helper.DeviceHelper;
import com.android.doctor.helper.JsonUtil;
import com.android.doctor.helper.StringHelper;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.model.RespEntity;
import com.android.doctor.model.RespError;
import com.android.doctor.model.User;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RespHandler;
import com.android.doctor.rest.RestClient;
import com.android.doctor.ui.base.BaseActivity;
import com.android.doctor.R;
import com.yuntongxun.kitsdk.utils.LogUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChangePhoneNumActivity extends BaseActivity {
	
	private static final String TAG = ChangePhoneNumActivity.class.getSimpleName();
    @InjectView(R.id.tv_tips)
    protected TextView mTips;
	@InjectView(R.id.edt_input_phone_number)
	protected EditText phnEdt;
	@InjectView(R.id.edt_input_v_code)
	protected EditText vCodeEdt;
	@InjectView(R.id.tv_get_vcode)
	protected TextView mTvGetVCode;
	@InjectView(R.id.btn_commit)
    protected AppCompatButton mBtnCommit;

	protected CountDownTimer timer = new CountDownTimer(60000, 1000) {
		@Override
		public void onTick(long millisUntilFinished) {
			mTvGetVCode.setClickable(false);
			mTvGetVCode.setText((millisUntilFinished / 1000) + "秒");
		}

		@Override
		public void onFinish() {
			mTvGetVCode.setClickable(true);
			mTvGetVCode.setText(R.string.get_message_vcode);
		}
	};

    @Override
    protected void setContentLayout() {
    	setContentView(getLayoutId());
    }

    protected void initView() {
        setActionBar(R.string.change_phone_num);
    }

    protected int getLayoutId() {
    	return R.layout.activity_change_mobile;
    }
	
	protected void initData() {
        User.UserEntity userEntity = AppContext.context().getUser();
        if (userEntity != null) {
            String text = String.format(getString(R.string.change_mobile_tips), userEntity.getMobilephone());
            mTips.setText(text);
        }
	}

    private boolean checkPhNum() {
        String mbl = phnEdt.getText().toString();
        if (TextUtils.isEmpty(mbl)) {
            UIHelper.showToast("请输入手机号");
            return false;
        }
        if (!StringHelper.isPhoneNumber(mbl)) {
            UIHelper.showToast("请输入正确的手机号");
            return false;
        }
        return true;
    }

    private boolean checkVcode() {
        if (TextUtils.isEmpty(vCodeEdt.getText())) {
            UIHelper.showToast("请输入短信验证码");
            return false;
        }
        return true;
    }

    protected void onGenVCode() {
        if (!checkPhNum()) return;

        timer.start();
        ApiService api = RestClient.createService(ApiService.class);
        String did = DeviceHelper.getDeviceId(this);
        String phoneNumber = phnEdt.getText().toString();
        String jstr = "{mobilephone:" + phoneNumber + "}";
        LogUtil.d(LogUtil.getLogUtilsTag(RegisterPhoneActivity.class), jstr);
        Call<RespError> call = api.genUptPhNumVcode(JsonUtil.toJson(jstr));
        call.enqueue(new Callback<RespError>() {
            @Override
            public void onResponse(Call<RespError> call, Response<RespError> response) {
                UIHelper.showToast(response.body().getError_msg());
            }
            @Override
            public void onFailure(Call<RespError> call, Throwable t) {
                UIHelper.showToast("验证码发送失败");
            }
        });
    }

    public Map<String,String> getParam() {
        User.UserEntity u = AppContext.context().getUser();
        Map<String, String> map = new HashMap<>();
        map.put("uid", u.getDuid());
        map.put("mobilephone", phnEdt.getText().toString());
        map.put("validcode", vCodeEdt.getText().toString());
        return map;
    }

    protected void onUptPhoneNum() {
        if (!checkPhNum() || !checkVcode()) return;
        mBtnCommit.setClickable(false);
        showProcessDialog();
        ApiService api = RestClient.createService(ApiService.class);
        Call<RespEntity> call = api.uptPhoneNum(getParam());
        call.enqueue(new RespHandler() {
            @Override
            public void onSucceed(RespEntity resp) {
                UIHelper.showToast(resp.getError_msg());
                finish();
            }

            @Override
            public void onFailed(RespEntity resp) {
                mBtnCommit.setClickable(true);
                UIHelper.showToast("更换手机号失败");
                dismissProcessDialog();
            }
        });
    }

	@OnClick(R.id.tv_get_vcode)
	protected void genMsgCode() {
        if (isFastDoubleClick()) return;
		onGenVCode();
	}

    @OnClick(R.id.btn_commit)
    protected void commit() {
        if (isFastDoubleClick()) return;
        onUptPhoneNum();
    }
}
