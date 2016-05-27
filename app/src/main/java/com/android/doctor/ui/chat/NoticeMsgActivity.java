package com.android.doctor.ui.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.doctor.R;
import com.android.doctor.app.AppConfig;
import com.android.doctor.app.AppManager;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.model.RespEntity;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RestClient;
import com.android.doctor.ui.base.BaseActivity;
import com.yuntongxun.kitsdk.db.ConversationSqlManager;

import java.util.Map;

import butterknife.InjectView;
import butterknife.OnCheckedChanged;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoticeMsgActivity extends BaseActivity {
	public static final String ARG_TYPE = "ARG_TYPE";
    public static final String ARG_SESSION_ID = "ARG_SESSION_ID";
	public static final String TAB_1 = "TAB_NOT_PROCESS";
	public static final String TAB_2 = "TAB_ALL";

	private FragmentTabHost mTabHost;
    @InjectView(R.id.rdg_btn)
	protected RadioGroup mRdoGrp;
    @InjectView(R.id.rbtn_tab1)
    protected RadioButton mTab1;
    @InjectView(R.id.rbtn_tab2)
    protected RadioButton mTab2;

	public static void startAty(Context aty, int type, String sessionId) {
		Intent intent = new Intent(aty, NoticeMsgActivity.class);
		intent.putExtra(ARG_TYPE, type);
        intent.putExtra(ARG_SESSION_ID, sessionId);
		aty.startActivity(intent);
        Log.d(AppConfig.TAG, "[NoticeMsgActivity-> startAty] type: " + type + ", sessionId: " + sessionId);
	}

	@Override
	protected void setContentLayout() {
		setContentView(R.layout.activity_notice_msg);
	}

	public void initView() {
		int tp = getIntent().getIntExtra(ARG_TYPE, 0);
        String sid = getIntent().getStringExtra(ARG_SESSION_ID);
        long mThread = ConversationSqlManager.querySessionIdForBySessionId(sid);
        ConversationSqlManager.setChattingSessionRead(mThread);

		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

		mTabHost.addTab(mTabHost.newTabSpec(TAB_1).setIndicator(TAB_1), FragmentNoticeMsgList.class,
				getBundle(tp, FragmentNoticeMsgList.TYPE_MSG_NOT_PROCESS, sid));
		mTabHost.addTab(mTabHost.newTabSpec(TAB_2).setIndicator(TAB_2), FragmentNoticeMsgList.class,
				getBundle(tp, FragmentNoticeMsgList.TYPE_MSG_ALL, sid));
        setActionBar(getMsgType(tp));
        setTab1();
	}

	private Bundle getBundle(int tp, int st, String sid) {
		Bundle b = new Bundle();
		b.putInt("type", tp);
		b.putInt("state", st);
        b.putString(ARG_SESSION_ID, sid);
		return b;
	}

    @Override
    protected void initData() {
        super.initData();
    }

    private String getMsgType(int tp) {
        String str = "";
        switch (tp) {
            case 200:
                str = "联系人助手";
                break;
            case 300:
                str = "患者咨询问诊";
                break;
            case 400:
                str = "随诊管理";
                break;
            case 500:
                str = "群助手";
                break;
            case 700:
                str = "患者报道";
                break;
        }
        return str;
    }

    private Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentByTag(
                mTabHost.getCurrentTabTag());
    }



    private void onFinished(RespEntity r) {
        if (r != null && r.getError_code() == 0) {
            setResult(RESULT_OK);
            AppManager.getAppManager().finishActivity(this);
        }
    }

    @OnCheckedChanged(R.id.rbtn_tab1)
	protected void setTab1() {
        if (mTab1.isChecked()) {
            mTabHost.setCurrentTabByTag(TAB_1);
        }
	}

    @OnCheckedChanged(R.id.rbtn_tab2)
    protected void setTab2() {
        if (mTab2.isChecked()) {
            mTabHost.setCurrentTabByTag(TAB_2);
        }
    }


}
