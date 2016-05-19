package com.android.doctor.ui.chat;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.helper.DeviceHelper;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.ui.base.BaseActivity;
import com.yuntongxun.kitsdk.utils.TextUtil;

import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnEditorAction;

/**
 * Created by Yong on 2016/3/26.
 */
public class AddContactActivity extends BaseActivity {

    @InjectView(R.id.edt_search_box)
    protected EditText mEdtContactName;

    @Override
    protected void setContentLayout() {
        setContentView(R.layout.activity_add_contact);
    }

    @Override
    protected void initView() {
        setActionBar(R.string.add_contact);
    }

    private void onCheckNull() {
        String gname = mEdtContactName.getText().toString();
        if (TextUtil.isEmpty(gname)) {
            UIHelper.showToast("组名不能为空");
            return;
        }
    }

    /**添加患者*/
    @OnClick(R.id.rl_add_patient)
    protected void onAddPatient() {
        UIHelper.showtAty(this, PatientListActivity.class);
    }

    /**添加医生*/
    @OnClick(R.id.rl_add_doctor)
    protected void onAddDoctor() {
        UIHelper.showtAty(this, DoctorListActivity.class);
    }

    @OnEditorAction(R.id.edt_search_box)
    protected boolean onSearch(TextView v, int actionId,
                               KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            String word = mEdtContactName.getText().toString().trim();
            if (!TextUtils.isEmpty(word)) {
                DeviceHelper.hideSoftKeyboard(v);
            }
            return true;
        }
        return false;
    }
}
