package com.android.doctor.ui.topic;

import android.widget.EditText;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.ui.base.BaseActivity;

import butterknife.InjectView;

/**
 * Created by Yong on 2016/4/6.
 */
public class PubTopicActivity extends BaseActivity{

    @InjectView(R.id.tv_topic)
    protected TextView mTvTopic;
    @InjectView(R.id.edt_title)
    protected EditText mEdtTitle;
    @InjectView(R.id.edt_content)
    protected EditText mEdtContent;

    @Override
    protected void setContentLayout() {
        setContentView(R.layout.activity_pub_topic);
    }

    @Override
    protected void initView() {
        setActionBar("");
    }
}
