package com.android.doctor.ui.chat;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.app.AppContext;
import com.android.doctor.helper.AnimUtils;
import com.android.doctor.helper.DeviceHelper;
import com.android.doctor.interf.OnScrollChangedListener;
import com.android.doctor.model.DoctorKSLabelList;
import com.android.doctor.model.DoctorList;
import com.android.doctor.model.DoctorZCLabelList;
import com.android.doctor.model.RespEntity;
import com.android.doctor.model.User;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RestClient;
import com.android.doctor.ui.adapter.SlideDownMenuAdapter;
import com.android.doctor.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Yong on 2016/3/28.
 */
public class DoctorListActivity extends BaseActivity {
    private static final String TAG = DoctorListActivity.class.getSimpleName();
    @InjectView(R.id.fl_popup_menu)
    protected FrameLayout mFlPopupMenu;
    @InjectView(R.id.view_mask)
    protected View mMaskView;
    @InjectView(R.id.header_view_frame)
    protected PtrClassicFrameLayout mPtrFrame;
    @InjectView(R.id.fl_container)
    protected FrameLayout mFlContainer;
    @InjectView(R.id.tv_zc)
    protected TextView mTabZC;
    @InjectView(R.id.tv_ks)
    protected TextView mTabKS;
    //@InjectView(R.id.scrollview)
    //protected ScrollView mScrollView;
    @InjectView(R.id.ll_tab_menu)
    protected LinearLayout mLlTabMenu;
    @InjectView(R.id.fl_popup_menu)
    protected FrameLayout mSlideMenuViews;
    @InjectView(R.id.iv_clear)
    protected ImageView mIvClear;
    @InjectView(R.id.edt_search_box)
    protected EditText mEdtSearch;
    @InjectView(R.id.lsearch_view)
    protected LinearLayout mLsearchView;
    @InjectView(R.id.tv_cancel)
    protected TextView mTvCancel;

    private int mCurTabPosition = -1;
    private List<DoctorZCLabelList.ZCLabelEntity> mZCLabelList;
    private List<DoctorKSLabelList.KSLabelEntity> mKSLabelList;
    private FragmentDoctorList mFragmentDocList;
    private boolean bZCLoad = false;
    private boolean bKSLoad = false;

    @Override
    protected void setContentLayout() {
        setContentView(R.layout.activity_list_doctor);
    }

    @Override
    protected void initView() {
        setActionBar(R.string.find_doctor);
        initHeaderView();
        initListView();
    }

    /**
     * 初始化头部刷新视图
     */
    private void initHeaderView() {
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return isCanDoRefresh();
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mPtrFrame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPtrFrame.refreshComplete();
                    }
                }, 1000);
            }
        });
    }

    private boolean isCanDoRefresh() {
        if (mFragmentDocList.isScrollTop()
                && mLsearchView.getVisibility() == View.VISIBLE
                && mSlideMenuViews.getVisibility() != View.VISIBLE ) {
            return true;
        }
        return false;
    }

    /**
     * 初始化医生列表视图
     */
    private void initListView() {
        mFragmentDocList = new FragmentDoctorList();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction trans = fm.beginTransaction();
        trans.replace(R.id.fl_container, mFragmentDocList).commitAllowingStateLoss();
        mFragmentDocList.setScrollChangedListener(new OnScrollChangedListener() {
            @Override
            public void onScrollChanged(RecyclerView recyclerView) {
                if (mFragmentDocList.isScrollTop() && mSlideMenuViews.getVisibility() != View.VISIBLE ) {
                    showSearchView();
                } else {
                    hideSearchView();
                }
            }
        });
    }

    private void showSearchView() {
        if (mLsearchView.getVisibility() != View.VISIBLE) {
            mLsearchView.setVisibility(View.VISIBLE);
            //AnimUtils.expand(mLsearchView);
        }
    }

    private void hideSearchView() {
        if (mLsearchView.getVisibility() != View.GONE) {
            mLsearchView.setVisibility(View.GONE);
            //AnimUtils.collapse(mLsearchView);
        }
    }
    /***
     * 请求数据
     */
    @Override
    protected void initData() {
        super.initData();
        onLoadZCLabel();
        onLoadKSLabel();
    }

    /***
     * 设置SlideDownTab监听
     */
    private void setSlidedownTabListener() {
        for (int i = 0; i < mLlTabMenu.getChildCount(); i = i + 2) {
            final View view = mLlTabMenu.getChildAt(i);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switchMenu(view);
                }
            });
        }
    }

    /***
     * 获取职称过滤标签
     */
    private void onLoadZCLabel() {
        Map<String, String> map = new HashMap<>();
        map.put("code", "10004");
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<DoctorZCLabelList>> call = service.getDoctorZCFilterList(map);
        call.enqueue(new Callback<RespEntity<DoctorZCLabelList>>() {
            @Override
            public void onResponse(Call<RespEntity<DoctorZCLabelList>> call, Response<RespEntity<DoctorZCLabelList>> response) {
                RespEntity<DoctorZCLabelList> respEntity = response.body();
                if (respEntity.getError_code() ==0) {
                    mZCLabelList = respEntity.getResponse_params().getData();
                    bZCLoad = true;
                    setSlideViewData();
                }
            }

            @Override
            public void onFailure(Call<RespEntity<DoctorZCLabelList>> call, Throwable t) {
                Log.d(TAG, t.toString());
            }
        });
    }

    /***
     * 获取科室过滤标签
     */
    private void onLoadKSLabel() {
        User.UserEntity u = AppContext.context().getUser();
        if (u == null) {
            return;
        }
        String hosid =  u.getHosid();
        Map<String, String> map = new HashMap<>();
        map.put("code", "10003");
        map.put("hosid", hosid);

        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<DoctorKSLabelList>> call = service.getDoctorKsFilterList(map);
        call.enqueue(new Callback<RespEntity<DoctorKSLabelList>>() {
            @Override
            public void onResponse(Call<RespEntity<DoctorKSLabelList>> call, Response<RespEntity<DoctorKSLabelList>> response) {
                RespEntity<DoctorKSLabelList> respEntity = response.body();
                if (respEntity.getError_code() ==0) {
                    mKSLabelList = respEntity.getResponse_params().getData();
                    bKSLoad = true;
                    setSlideViewData();
                }
            }

            @Override
            public void onFailure(Call<RespEntity<DoctorKSLabelList>> call, Throwable t) {
                Log.d(TAG, t.toString());
            }
        });
    }

    /***
     * 填充SlideDown视图数据
     */
    private void setSlideViewData() {
        Log.d(TAG, "setSlideViewData, " + bZCLoad + ", " + bZCLoad);
       if (bKSLoad && bZCLoad) {
           setZCSlideViewData();
           setKSSlideViewData();
           setSlidedownTabListener();
       }
    }

    /***
     * 填充职称视图数据
     */
    private void setZCSlideViewData() {
        if (mZCLabelList != null) {
            List<String> list = new ArrayList<>();
            list.add("全部");
            for (int i = 0; i < mZCLabelList.size(); ++i) {
                DoctorZCLabelList.ZCLabelEntity zcLabel = mZCLabelList.get(i);
                list.add(zcLabel.getName());
            }
            mSlideMenuViews.addView(getSlidedownView(list, 0), 0);
        }
    }

    /***
     * 填充科室视图数据
     */
    private void setKSSlideViewData() {
        if (mKSLabelList != null) {
            List<String> list = new ArrayList<>();
            list.add("全部");
            for (int i = 0; i < mKSLabelList.size(); ++i) {
                DoctorKSLabelList.KSLabelEntity ksLabel = mKSLabelList.get(i);
                list.add(ksLabel.getName());
            }
            mSlideMenuViews.addView(getSlidedownView(list, 1), 1);
        }
    }

    /***
     * 创建下拉视图
     * @param list
     * @param type
     * @return
     */
    private View getSlidedownView(List<String> list, int type) {
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.popup_listview, null);
        ListView listView = (ListView) view.findViewById(R.id.list);
        final SlideDownMenuAdapter adapter = new SlideDownMenuAdapter(this, list, R.layout.textview, type, false);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onMenuItemClick(adapter.getmType(), position);
            }
        });
        return view;
    }

    /***
     * 下拉标签点击处理
     * @param tabIndex
     * @param position
     */
    private void onMenuItemClick(int tabIndex, int position) {
        if (0 <= tabIndex && tabIndex < mSlideMenuViews.getChildCount()) {
            Map<String,String> map = new HashMap<>();
            for (int i = 0; i < mSlideMenuViews.getChildCount(); ++i) {
                ListView lView = (ListView) mSlideMenuViews.getChildAt(i).findViewById(R.id.list);
                SlideDownMenuAdapter adapter = (SlideDownMenuAdapter) lView.getAdapter();
                int tabType = adapter.getmType();
                if (tabIndex == i) {
                    adapter.setCheckItem(position);
                    if (position == 0) {
                        if (tabType == 0) {
                            setTabText(getString(R.string.job_title));
                        } else if (tabType == 1) {
                            setTabText(getString(R.string.department));
                        }
                    } else {
                        if (tabType == 0) {
                            setTabText(mZCLabelList.get(position-1).getName());
                        } else if (tabType == 1) {
                            setTabText(mKSLabelList.get(position-1).getName());
                        }
                    }
                }

                int checkPos = adapter.getCheckItemPosition();
                if (checkPos == 0) continue;
                if (tabType == 0) {
                    map.put("titleid", mZCLabelList.get(position-1).getCode());
                } else if (tabType == 1) {
                    map.put("deptid", "" + mKSLabelList.get(position-1).getCode());
                }
            }
            mFragmentDocList.onFilter(map);
        }
        closeMenu();
    }

    @OnTextChanged(R.id.edt_search_box)
    public void onEdtTextChange(CharSequence s, int start, int before, int count) {
        mIvClear.setVisibility(TextUtils.isEmpty(s) ? View.INVISIBLE : View.VISIBLE);
    }

    @OnFocusChange(R.id.edt_search_box)
    protected void onEditTextFocusChange(View v, boolean hasFocus) {
        setCancelViewBg(hasFocus);
    }

    @OnClick(R.id.iv_clear)
    public void onClear() {
        mEdtSearch.setText("");
        mFragmentDocList.clearOption("username");
    }

    @OnClick(R.id.view_mask)
    protected void onViewMaskClk() {
        closeMenu();
    }

    @OnClick(R.id.tv_cancel)
    protected void onClickCancelView(){
        boolean focused = mEdtSearch.isFocused();
        setCancelViewBg(!focused);
        if (focused) {
            mEdtSearch.clearFocus();
            DeviceHelper.hideSoftKeyboard(mEdtSearch);
        } else {
            mEdtSearch.requestFocus();
            DeviceHelper.showSoftKeyboard(mEdtSearch);
        }
    }

    @OnEditorAction(R.id.edt_search_box)
    protected boolean onSearch(TextView v, int actionId,
                               KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            String word = mEdtSearch.getText().toString().trim();
            if (!TextUtils.isEmpty(word)) {
                DeviceHelper.hideSoftKeyboard(v);
                DoctorList.ColGLEntity glEntity = new DoctorList.ColGLEntity("username", word);
                mFragmentDocList.onSearch(glEntity);
            }
            return true;
        }
        return false;
    }

    private void setCancelViewBg(boolean focus) {
        if (focus) {
            mTvCancel.setTextColor(getResources().getColor(R.color.appThemePrimary));
        } else {
            mTvCancel.setTextColor(getResources().getColor(R.color.gray));
        }
    }

    private void switchMenu(View target) {
        for (int i = 0; i < mLlTabMenu.getChildCount(); i = i + 2) {
            if (target == mLlTabMenu.getChildAt(i)) {
                if (mCurTabPosition == i) {
                    closeMenu();
                } else {
                    if (mCurTabPosition == -1) {
                        mSlideMenuViews.setVisibility(View.VISIBLE);
                        mSlideMenuViews.setAnimation(AnimationUtils.loadAnimation(this, R.anim.dd_menu_in));
                        mMaskView.setVisibility(View.VISIBLE);
                        mMaskView.setAnimation(AnimationUtils.loadAnimation(this, R.anim.dd_mask_in));
                        mSlideMenuViews.getChildAt(i / 2).setVisibility(View.VISIBLE);
                    } else {
                        mSlideMenuViews.getChildAt(i / 2).setVisibility(View.VISIBLE);
                    }
                    mCurTabPosition = i;
                    ((TextView) mLlTabMenu.getChildAt(i))
                            .setTextColor(getResources().getColor(R.color.pop_tab_text_selected_color));
                    hideSearchView();
                }
            } else {
                ((TextView) mLlTabMenu.getChildAt(i))
                        .setTextColor(getResources()
                                .getColor(R.color.pop_tab_text_unselected_color));
                mSlideMenuViews.getChildAt(i / 2).setVisibility(View.GONE);
            }
        }
    }


    public void closeMenu() {
        if (mCurTabPosition != -1) {
            ((TextView) mLlTabMenu.getChildAt(mCurTabPosition))
                    .setTextColor(getResources().getColor(R.color.pop_tab_text_unselected_color));
            mSlideMenuViews.setVisibility(View.GONE);
            mSlideMenuViews.setAnimation(AnimationUtils.loadAnimation(this, R.anim.dd_menu_out));
            mMaskView.setVisibility(View.GONE);
            mMaskView.setAnimation(AnimationUtils.loadAnimation(this, R.anim.dd_mask_out));
            mCurTabPosition = -1;
        }
    }

    public void setTabText(String text) {
        if (mCurTabPosition != -1) {
            ((TextView) mLlTabMenu.getChildAt(mCurTabPosition)).setText(text);
        }
    }
}
