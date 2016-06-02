package com.android.doctor.ui.mine;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.app.AppContext;
import com.android.doctor.model.Constants;
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

public class EditActivity extends BaseActivity {

	@InjectView(R.id.edttext)
	protected EditText mEditText;
    @InjectView(R.id.tips)
    protected TextView mTvTips;
    private int type;

    public static void startAty(Activity c, int type, String text) {
        Intent intent = new Intent();
        intent.setClass(c, EditActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("content", text);
        c.startActivityForResult(intent, Constants.REQ_CODE_FOR_UPDATE);
    }

	@Override
	protected void setContentLayout() {
		setContentView(R.layout.activity_edittext);
	}

    @Override
    protected void init() {
        super.init();
    }

    @Override
	protected void initView() {
		super.initView();
        Intent intent = getIntent();
        type = intent.getIntExtra("type", 0);
        String content = intent.getStringExtra("content");
        if (type == Constants.REQUEST_CODE_EDIT_EDU_EXP) {
            setActionBar(R.string.edu_exp);
        } else if (type == Constants.REQUEST_CODE_EDIT_CARRER_EXP) {
            setActionBar(R.string.career_exp);
        } else if (type == Constants.REQUEST_CODE_EDIT_FAVOR){
            setActionBar(R.string.favorite);
        } else if (type == Constants.REQUEST_CODE_EDIT_SUGGEST) {
            setActionBar(R.string.feedback_suggestion);
            mTvTips.setVisibility(View.VISIBLE);
        }
        mEditText.setText(content);
	}

	@OnClick(R.id.img_complete)
	protected void onComp() {
        onSave();
	}

    private void onSave() {
        showProcessDialog();
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity> call = service.uptsingle(getParam());
        call.enqueue(new RespHandler() {
            @Override
            public void onSucceed(RespEntity resp) {
                onProResult(resp);
                setResult(RESULT_OK);
            }

            @Override
            public void onFailed(RespEntity resp) {
                onProResult(resp);
            }
        });
    }

    public Map<String,String> getParam() {
        User.UserEntity u = AppContext.context().getUser();
        Map<String, String> map = new HashMap<>();
        map.put("duid", u.getDuid());
        if (type == Constants.REQUEST_CODE_EDIT_CARRER_EXP) {
            map.put("experience", mEditText.getText().toString());
        } else if (type == Constants.REQUEST_CODE_EDIT_FAVOR) {
            map.put("good", mEditText.getText().toString());
        }
        return map;
    }
}
