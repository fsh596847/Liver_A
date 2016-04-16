package com.android.doctor.ui.activity;

import android.app.FragmentManager;
import android.os.Bundle;

import com.android.doctor.R;
import com.android.doctor.ui.base.BaseActivity;

public class AboutUsActivity extends BaseActivity {

	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_about_us);
	        setActionBar("关于我们");
	    }

	    public void replaceFragment(int viewId, android.app.Fragment fragment) {
	        FragmentManager fragmentManager = getFragmentManager();
	        fragmentManager.beginTransaction().replace(viewId, fragment).commit();
	    }
	   
}
