package com.android.doctor.ui.plan;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.android.doctor.R;
import com.android.doctor.model.MedicClassify;
import com.android.doctor.model.RespEntity;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RestClient;
import com.android.doctor.ui.base.BaseActivity;
import com.android.doctor.ui.widget.PageEnableViewPager;

import java.util.List;

import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectMedicineListActivity extends BaseActivity implements OnClickListener {

	private static final String TAG = SelectMedicineListActivity.class.getSimpleName();
	private FragmentTabHost mTabHost;
    //private String tabs[] = new String[4];
    @InjectView(R.id.viewPager)
    protected PageEnableViewPager mViewPager;
    @InjectView(R.id.tabLayout)
    protected TabLayout mTabLayout;

	@Override
	protected void setContentLayout() {
		setContentView(R.layout.activity_main_medicine_list);
	}

	protected void initView(){
        setActionBar("");
        mViewPager.setPagingEnabled(false);
        /*mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        if (Build.VERSION.SDK_INT > 10) {
            mTabHost.getTabWidget().setShowDividers(0);
        }*/
        //mTabHost.setOnTabChangedListener(this);
        //initTabHost();
        onLoad();
	}

    private void initTabLayout() {

    }

    /*private void initTabHost() {
        TabSpec tab1 = mTabHost.newTabSpec("First Tab");
        tab1.setIndicator("First Tab");
        tab1.setContent(new Intent(this, SelectMedicineListActivity.class));
        mTabHost.addTab(tab1);
    }*/

	private void initTabs(List<MedicClassify.MedicEntity> tabs) {
        if (tabs == null) return;
        final int size = tabs.size();
        for (int i = 0; i < size; i++) {
            MedicClassify.MedicEntity e = tabs.get(i);
            if (e == null) continue;
            String t = e.getName();
            mTabLayout.addTab(mTabLayout.newTab().setText(t));
           /* TabSpec tab = mTabHost.newTabSpec(t);
            View indicator = LayoutInflater.from(getApplicationContext())
                    .inflate(R.layout.textview, null);
            
            TextView title = (TextView) indicator.findViewById(R.id.tv_text);
            title.setText(t);

            tab.setIndicator(indicator);
            tab.setContent(new TabContentFactory() {
                @Override
                public View createTabContent(String tag) {
                    return new View(SelectMedicineListActivity.this);
                }
            });

            if (tab == null) {
                LogUtil.e(LogUtil.getLogUtilsTag(SelectMedicineListActivity.class), "tab is null");
            }
            mTabHost.addTab(tab, FragmentTextLists.class, null);
            mTabHost.getTabWidget().getChildAt(i).setOnTouchListener(this);*/
        }
        TextFragmentPagerAdapter adapter = new TextFragmentPagerAdapter(getSupportFragmentManager(),
                this);
        adapter.setmData(tabs);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
	}

    private void onLoad() {
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<MedicClassify>> call = service.getMedicClassify();
        call.enqueue(new Callback<RespEntity<MedicClassify>>() {
            @Override
            public void onResponse(Call<RespEntity<MedicClassify>> call, Response<RespEntity<MedicClassify>> response) {
                RespEntity<MedicClassify> data = response.body();
                if (response.isSuccessful()) {
                    if (data == null) {
                        return;
                    } else if (data.getResponse_params() != null) {
                        initTabs(data.getResponse_params().getList());
                    }
                } else {
                    String errMsg = "";
                    if (data != null) {
                        errMsg = data.getError_msg();
                    }
                }
            }

            @Override
            public void onFailure(Call<RespEntity<MedicClassify>> call, Throwable t) {
                Log.d("PlanSchemeActivity",t.toString());
            }
        });
    }


	@Override
	public void onClick(View v) {
		if (isFastDoubleClick()) {
			return;
		}
	}

    class TextFragmentPagerAdapter extends FragmentPagerAdapter {
        private List<MedicClassify.MedicEntity> mData;
        private Context context;

        public TextFragmentPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public Fragment getItem(int position) {
            if (mData == null) return null;
            MedicClassify.MedicEntity e = mData.get(position);
            return FragmentMedicInfoList.newInstance("" + e.getCode());
        }

        @Override
        public int getCount() {
            return mData == null ? 0 : mData.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mData == null ? "" : mData.get(position) == null ? null : mData.get(position).getName();
        }

        public void setmData(List<MedicClassify.MedicEntity> mData) {
            this.mData = mData;
        }
    }
}
