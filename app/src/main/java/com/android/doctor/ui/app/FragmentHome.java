package com.android.doctor.ui.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.android.doctor.helper.DeviceHelper;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.interf.OnRefreshDataListener;
import com.android.doctor.model.Constants;
import com.android.doctor.model.User;
import com.android.doctor.ui.adapter.SlideDownMenuAdapter;
import com.android.doctor.ui.base.BaseFragment;
import com.android.doctor.ui.patient.FragmentPatientList;
import com.yuntongxun.kitsdk.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by Yong on 2016-02-12.
 */
public class FragmentHome extends BaseFragment {
    public static final String TAG = LogUtil.getLogUtilsTag(FragmentHome.class);
    public static final String ARG_NAV = "ARG_NAV";
    @InjectView(R.id.fl_popup_menu)
    protected FrameLayout mFlPopupMenu;
    /*@InjectView(R.id.tv_age)
    protected TextView mTvTabAge;
    @InjectView(R.id.tv_disease)
    protected TextView mTvTabDis;
    @InjectView(R.id.tv_illness)
    protected TextView mTvTabIll;
    @InjectView(R.id.tv_state)
    protected TextView mTvTabState;*/

    @InjectView(R.id.view_mask)
    protected View mMaskView;
    @InjectView(R.id.header_view_frame)
    protected PtrClassicFrameLayout mPtrFrame;
    @InjectView(R.id.fl_main)
    protected FrameLayout mFlMain;
    //@InjectView(R.id.scrollview)
    //protected ScrollView mScrollView;
    @InjectView(R.id.ll_tab_menu)
    protected LinearLayout mLlTabMenu;
    @InjectView(R.id.fl_popup_menu)
    protected FrameLayout mSlideMenuViews;
    @InjectView(R.id.edt_search_box)
    protected EditText mEdtSearch;
    @InjectView(R.id.iv_clear)
    protected ImageView mIvClear;
    @InjectView(R.id.tv_cancel)
    protected TextView mTvCancel;
    private int mCurTabPosition = -1;
    private int mType;

    private List<User.UserEntity.GroupsEntity> mTabGroup;
    private FragmentPatientList mFragmentPatient;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    protected void initView(View view) {
        Bundle b = getArguments();
        if (b != null) {
            mType = b.getInt(ARG_NAV);
            if (mType == 1) {
                initActionBar(view);
            }
        }

        initHeaderView(view);
        initListView();
        initSlidedownTab();
    }

    private void initActionBar(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        if (toolbar == null) return;
        AppCompatActivity appAty = ((AppCompatActivity) getActivity());
        appAty.setSupportActionBar(toolbar);
        ActionBar mActionBar = appAty.getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setDisplayShowTitleEnabled(false);
    }

    private void initHeaderView(View view) {
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                RecyclerView rcView = (RecyclerView) mFlMain.findViewById(R.id.recyc_view);
                LinearLayoutManager lm = (LinearLayoutManager) rcView.getLayoutManager();
                if (lm.findFirstVisibleItemPosition() <= 0 &&
                        mSlideMenuViews.getVisibility() != View.VISIBLE ) {
                    return true;
                }
                return false;
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mPtrFrame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mFragmentPatient.onRefresh();
                    }
                }, 500);
            }
        });
    }

    private void initListView() {
        mFragmentPatient = FragmentPatientList.newInstance(Constants.PATIENT_TYPE_IS_DOCTOR, "");
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction trans = fm.beginTransaction();
        trans.replace(R.id.fl_container, mFragmentPatient).commit();
        mFragmentPatient.setRefreshDataListener(new OnRefreshDataListener() {
            @Override
            public void onRefreshComplete(String msg) {
                mPtrFrame.refreshComplete();
            }
        });
    }
        /*
        // the following are default settings
        mPtrFrame.setResistance(1.7f);
        mPtrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrFrame.setDurationToClose(200);
        mPtrFrame.setDurationToCloseHeader(1000);
        // default is false
        mPtrFrame.setPullToRefresh(false);
        // default is true
        mPtrFrame.setKeepHeaderWhenRefresh(true);
        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrame.autoRefresh();
            }
        }, 100);*/

    private void setPopdownTabListener() {
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

    private void initSlidedownTab() {
        User.UserEntity u = AppContext.context().getUser();
        if (u != null) {
            mTabGroup = u.getGroups();
            if (mTabGroup == null) return;
            List<String> tabText = new ArrayList<>();
            for (int i = 0; i < mTabGroup.size(); ++i) {
                User.UserEntity.GroupsEntity group = mTabGroup.get(i);
                if (group != null) {
                    mSlideMenuViews.addView(getSlidedownView(group, i), i);
                    tabText.add(group.getGroupname());
                }
            }
            for (int i = 0; i < mTabGroup.size(); ++i) {
                addTab(tabText, i);
            }
            //mLlTabMenu.invalidate();
            //setPopdownTabListener();
        }
    }

    private View getSlidedownView(User.UserEntity.GroupsEntity data, int type) {
        List<String> list = new ArrayList<>();
        if (data != null) {
            List<User.UserEntity.GroupsEntity.ChildgroupsEntity> dc = data.getChildgroups();
            if (dc != null) {
                list.add("全部");
                for (User.UserEntity.GroupsEntity.ChildgroupsEntity e : dc) {
                    list.add(e.getGroupname());
                }
            }
        }
        Context context = getContext();
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.popup_listview, null);
        ListView listView = (ListView) view.findViewById(R.id.list);
        final SlideDownMenuAdapter adapter = new SlideDownMenuAdapter(context, list, R.layout.textview, type, false);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onMenuItemClick(adapter.getmType(), position);
            }
        });
        return view;
    }

    private void onMenuItemClick(int tabIndex, int position) {
        if (0 <= tabIndex && tabIndex < mTabGroup.size()) {
            List<User.UserEntity.GroupsEntity.ChildgroupsEntity> gParam = new ArrayList<>();
            for (int i = 0; i < mSlideMenuViews.getChildCount(); ++i) {
                ListView lView = (ListView) mSlideMenuViews.getChildAt(i).findViewById(R.id.list);
                SlideDownMenuAdapter adapter = (SlideDownMenuAdapter) lView.getAdapter();
                User.UserEntity.GroupsEntity group = mTabGroup.get(adapter.getmType());
                if (group != null) {
                    List<User.UserEntity.GroupsEntity.ChildgroupsEntity> dc = group.getChildgroups();
                    if (tabIndex == i) {
                        adapter.setCheckItem(position);
                        if (position == 0) {
                            setTabText(mTabGroup.get(tabIndex).getGroupname());
                        } else {
                            User.UserEntity.GroupsEntity.ChildgroupsEntity cg1 = dc.get(position - 1);
                            setTabText(cg1.getGroupname());
                        }
                    }
                    int checkPos = adapter.getCheckItemPosition();
                    if (checkPos == 0) continue;
                    if (dc != null) {
                        User.UserEntity.GroupsEntity.ChildgroupsEntity cg = dc.get(checkPos - 1);
                        gParam.add(cg);
                    }
                }
            }
            mFragmentPatient.onFilter(gParam);
        }
        closeMenu();
    }

    @OnTextChanged(R.id.edt_search_box)
    public void onEdtPhoneNum(CharSequence s, int start, int before, int count) {
        mIvClear.setVisibility(TextUtils.isEmpty(s) ? View.INVISIBLE : View.VISIBLE);
    }

    @OnClick(R.id.iv_clear)
    public void onClear() {
        mEdtSearch.setText("");
        mFragmentPatient.clearOption("keywords");
    }

    @OnFocusChange(R.id.edt_search_box)
    protected void onEditTextFocusChange(View v, boolean hasFocus) {
        setCancelViewBg(hasFocus);
        closeMenu();
    }

    @OnClick(R.id.tv_cancel)
    protected void onClickCancelView(){
        boolean focused = mEdtSearch.isFocused();
        setCancelViewBg(!focused);
    }

    @OnEditorAction(R.id.edt_search_box)
    protected boolean onSearch(TextView v, int actionId,
                               KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            String word = mEdtSearch.getText().toString().trim();
            if (!TextUtils.isEmpty(word)) {
                DeviceHelper.hideSoftKeyboard(v);
                mFragmentPatient.onSearch("keywords", word);
            }
            return true;
        }
        return false;
    }

    private void setCancelViewBg(boolean focus) {
        if (focus) {
            mEdtSearch.requestFocus();
            DeviceHelper.showSoftKeyboard(mEdtSearch);
            mTvCancel.setTextColor(getResources().getColor(R.color.appThemePrimary));
        } else {
            mEdtSearch.clearFocus();
            DeviceHelper.hideSoftKeyboard(mEdtSearch);
            mTvCancel.setTextColor(getResources().getColor(R.color.gray));
        }
    }

    @OnClick(R.id.view_mask)
    protected void onViewMaskClk() {
        closeMenu();
    }

    @OnClick(R.id.img_stat)
    protected void onViewStatClick() {
        UIHelper.showStatAty(getActivity());
    }

    @OnClick(R.id.img_add)
    protected void onAddPatientClick() {
        UIHelper.showAddPatientAty(getActivity());
    }

    private void switchMenu(View target) {
        for (int i = 0; i < mLlTabMenu.getChildCount(); i = i + 2) {
            if (target == mLlTabMenu.getChildAt(i)) {
                if (mCurTabPosition == i) {
                    closeMenu();
                } else {
                    if (mCurTabPosition == -1) {
                        mSlideMenuViews.setVisibility(View.VISIBLE);
                        mSlideMenuViews.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dd_menu_in));
                        mMaskView.setVisibility(View.VISIBLE);
                        mMaskView.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dd_mask_in));
                        mSlideMenuViews.getChildAt(i / 2).setVisibility(View.VISIBLE);
                    } else {
                        mSlideMenuViews.getChildAt(i / 2).setVisibility(View.VISIBLE);
                    }
                    mCurTabPosition = i;
                    ((TextView) mLlTabMenu.getChildAt(i))
                            .setTextColor(getResources()
                            .getColor(R.color.pop_tab_text_selected_color));
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
            mSlideMenuViews.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dd_menu_out));
            mMaskView.setVisibility(View.GONE);
            mMaskView.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dd_mask_out));
            mCurTabPosition = -1;
        }

    }

    private void addTab(@NonNull List<String> tabTexts, int i) {
        final TextView tab = new TextView(getActivity());
        tab.setSingleLine();
        tab.setEllipsize(TextUtils.TruncateAt.END);
        tab.setGravity(Gravity.CENTER);
        //Log.d("addTab", tabTexts.get(i));
        //tab.setTextSize(TypedValue.COMPLEX_UNIT_PX,menuTextSize);
        tab.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
        tab.setTextSize(12);
        tab.setTextColor(getResources().getColor(R.color.app_theme_primary_textcolor));
        tab.setText(tabTexts.get(i));
        //tab.setPadding((int)DeviceHelper.dpToPixels(5), (int)DeviceHelper.dpToPixels(5), (int)DeviceHelper.dpToPixels(5), (int)DeviceHelper.dpToPixels(5));
        //添加点击事件
        tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchMenu(tab);
            }
        });
        mLlTabMenu.addView(tab);
        //添加分割线
        if (i < tabTexts.size() - 1) {
            View view = new View(getActivity());
            view.setLayoutParams(new LinearLayout.LayoutParams(1, ViewGroup.LayoutParams.MATCH_PARENT));
            view.setBackgroundColor(getResources().getColor(R.color.divider_color));
            mLlTabMenu.addView(view);
        }
    }

    public void setTabText(String text) {
        if (mCurTabPosition != -1) {
            ((TextView) mLlTabMenu.getChildAt(mCurTabPosition)).setText(text);
        }
    }
}
