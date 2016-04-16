package com.android.doctor.ui.activity;

import android.os.CountDownTimer;
import android.support.v7.widget.AppCompatButton;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.android.doctor.app.AppContext;
import com.android.doctor.helper.StringHelper;
import com.android.doctor.rest.ApiClient;
import com.android.doctor.R;
import com.android.doctor.ui.base.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.InjectView;


public class ChangePasswordActivity extends BaseActivity {

	private static final String TAG = ChangePasswordActivity.class.getSimpleName();
	@InjectView(R.id.tv_userphone)
	protected TextView mTvUserPhone;
	@InjectView(R.id.edt_input_phone_number)
	protected EditText mEdtCode;
	@InjectView(R.id.btn_regain)
    protected AppCompatButton mBtnRegain;
	private AppCompatButton btnNext;
	
	private String userPhone;	
	
	private int typeIntent;

    protected CountDownTimer timer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            mBtnRegain.setClickable(false);
            mBtnRegain.setText((millisUntilFinished / 1000) + "秒后重新获取");
        }

        @Override
        public void onFinish() {
            mBtnRegain.setClickable(true);
            mBtnRegain.setText("重新获取");
        }
    };

	@Override
    protected void setContentLayout() {
    	setContentView(getLayoutId());
    }
	    
	protected int getLayoutId() {
		return R.layout.activity_change_password;
	}	

	protected void initView() {
	}
	
	protected void initData() {
		mTvUserPhone.setText(StringHelper.replace(userPhone, 3, 4, '*'));
	}
	
	private void onNext() {
		btnNext.setEnabled(false);
		String vCode = mEdtCode.getText().toString();
		if (!preCheck(userPhone, vCode)) {
			btnNext.setEnabled(true);
			return;
		}
		onCheckVCode(vCode, userPhone);
	}

    protected void obtainVerifyCode(final String userPhone, boolean isCheckUser) {
        ApiClient apiClient = ApiClient.getClient((AppContext) getApplication());
        //if (!StringHelper.isValidatePhoneNumber(this, userPhone)) {
        //	return;
        //}
    }

    protected void onGenVCode(String userPhone) {
        timer.start();
        ApiClient apiClient = ApiClient.getClient((AppContext) getApplication());
        //String url = ApiService.get_gen_verify_code(userPhone);
    }

    protected boolean preCheck(String strPhone, String vCode) {
        //return StringHelper.isValidatePhoneNumber(this, strPhone) &&
        //		StringHelper.isVerifyCode(vCode);
        return false;
    }

    protected void onCheckVCode(final String vCode, final String userPhone) {
        ApiClient apiClient = ApiClient.getClient((AppContext) getApplication());
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("mobilephone_num", userPhone);
        map.put("verification_code", vCode);

        JSONObject internal = new JSONObject(map);
        JSONObject wrapper = new JSONObject();
        try {
            wrapper.put("user", internal);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (isFastDoubleClick()) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

}
