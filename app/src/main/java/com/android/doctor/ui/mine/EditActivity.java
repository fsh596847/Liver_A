package com.android.doctor.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.model.Constants;
import com.android.doctor.ui.base.BaseActivity;

import butterknife.InjectView;
import butterknife.OnClick;

public class EditActivity extends BaseActivity {

	@InjectView(R.id.edttext)
	protected EditText mEditText;
    @InjectView(R.id.tips)
    protected TextView mTvTips;
    private int type;

    public static void startAty(Context c, int type, String text) {
        Intent intent = new Intent();
        intent.setClass(c, EditActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("content", text);
        c.startActivity(intent);
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

	}
}
