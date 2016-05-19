package com.android.doctor.ui.app;

import android.os.CountDownTimer;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.android.doctor.app.AppContext;
import com.android.doctor.helper.StringHelper;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.model.User;
import com.android.doctor.ui.base.BaseActivity;
import com.android.doctor.R;

import butterknife.InjectView;
import butterknife.OnClick;


public class ChangePhoneNumActivity extends BaseActivity {
	
	private static final String TAG = ChangePhoneNumActivity.class.getSimpleName();
    @InjectView(R.id.tv_tips)
    protected TextView mTips;
	@InjectView(R.id.edt_input_phone_number)
	protected EditText userPhoneEditText;
	@InjectView(R.id.edt_input_v_code)
	protected EditText vCodeEditText;
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

    private boolean preCheck() {
        String mbl = userPhoneEditText.getText().toString();
        if (TextUtils.isEmpty(mbl)) {
            UIHelper.showToast("请输入手机号");
            return false;
        }
        if (StringHelper.isPhoneNumber(mbl)) {
            UIHelper.showToast("请输入正确的手机号");
            return false;
        }
        if (TextUtils.isEmpty(vCodeEditText.getText())) {
            UIHelper.showToast("请输入短信验证码");
            return false;
        }
        return true;
    }

	@OnClick(R.id.tv_get_vcode)
	protected void genMsgCode() {
		timer.start();
	}

    @OnClick(R.id.btn_commit)
    protected void commit() {
        if (!preCheck())return;
    }
}
