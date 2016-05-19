package com.android.doctor.ui.chat;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.ui.base.BaseFragment;

import butterknife.InjectView;

/**
 * Created by Yong on 2016/4/23.
 */
public class FragmentDoctorBaseInfo extends BaseFragment{
    private static final String ARG_INFO = "ARG_INFO";
    @InjectView(R.id.tv_text)
    protected TextView mTxInfo;

    @Override
    protected int getLayoutId() {
        return R.layout.textview;
    }

    public static FragmentDoctorBaseInfo newInstance(String info) {
        FragmentDoctorBaseInfo f = new FragmentDoctorBaseInfo();
        Bundle b = new Bundle();
        b.putString(ARG_INFO, info);
        f.setArguments(b);
        return f;
    }

    @Override
    protected void initData() {
        super.initData();
        Bundle b = getArguments();
        if (b != null) {
            String text = b.getString(ARG_INFO);
            mTxInfo.setText(Html.fromHtml(text.replace("\n", "<br>")));
        }
    }
}
