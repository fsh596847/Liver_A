package com.fidots.restaurant.ui.activity;

import android.app.FragmentManager;
import android.os.Bundle;

import com.fidots.restaurant.R;

public class AboutUsActivity extends BaseActivity {

	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_about_us);
	        setActionBar();
	    }

	    public void replaceFragment(int viewId, android.app.Fragment fragment) {
	        FragmentManager fragmentManager = getFragmentManager();
	        fragmentManager.beginTransaction().replace(viewId, fragment).commit();
	    }
	   
}
