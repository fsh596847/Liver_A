package com.android.doctor.ui.patient;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.android.doctor.model.User;
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

/**
 * Created by Yong on 2016/3/28.
 */
public class VisitPatientListActivity extends BaseActivity {

    public static final int REQUEST_CODE_SELECT_PATIENT = 100;
    @InjectView(R.id.fl_popup_menu)
    protected FrameLayout mFlPopupMenu;
    @InjectView(R.id.view_mask)
    protected View mMaskView;
    @InjectView(R.id.header_view_frame)
    protected PtrClassicFrameLayout mPtrFrame;
    @InjectView(R.id.fl_container)
    protected FrameLayout mFlContainer;
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
    @InjectView(R.id.tv_cancel)
    protected TextView mTvCancel;

    private int mCurTabPosition = -1;
    private List<User.UserEntity.GroupsEntity> mListTab = new ArrayList<>();
    private FragmentVisitPatientList mFrgList;

    @Override
    protected void setContentLayout() {
        setContentView(R.layout.activity_list_visitpatient);
    }

    @Override
    protected void initView() {
        setActionBar(R.string.visit_patient);
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
                RecyclerView rcView = (RecyclerView) mFlContainer.findViewById(R.id.recyc_view);
                LinearLayoutManager lm = (LinearLayoutManager) rcView.getLayoutManager();
                if (lm.findFirstVisibleItemPosition() == 0 && mSlideMenuViews.getVisibility() != View.VISIBLE) {
                    return true;
                }
                return false;
                //return PtrDefaultHandler.checkContentCanBePulledDown(frame, mFlMain, header);
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

    /**
     * 初始化医生列表视图
     */
    private void initListView() {
        mFrgList = new FragmentVisitPatientList();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction trans = fm.beginTransaction();
        trans.replace(R.id.fl_container, mFrgList).commitAllowingStateLoss();
        initSlidedownTab();
    }

    /***
     * 请求数据
     */
    @Override
    protected void initData() {
        super.initData();
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

    private void initSlidedownTab() {
        User.UserEntity u = AppContext.context().getUser();
        if (u != null) {
            List<User.UserEntity.GroupsEntity>  tabGroup = u.getGroups();
            for (int i = 1; i < tabGroup.size()-1 ; ++i) {
                mListTab.add(tabGroup.get(i));
            }
            if (mListTab == null) return;
            List<String> tabText = new ArrayList<>();
            for (int i = 0; i < mListTab.size() ; ++i) {
                User.UserEntity.GroupsEntity group = mListTab.get(i);
                if (group != null) {
                    mSlideMenuViews.addView(getSlidedownView(group, i), i);
                    tabText.add(group.getGroupname());
                }
            }
            for (int i = 0; i < mListTab.size(); ++i) {
                addTab(tabText, i);
            }
        }
    }

    /***
     * 创建下拉视图
     * @param data
     * @param type
     * @return
     */
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
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        Map<String,String> map = new HashMap<>();
        if (0 <= tabIndex && tabIndex < mListTab.size()) {
            for (int i = 0; i < mSlideMenuViews.getChildCount(); ++i) {
                ListView lView = (ListView) mSlideMenuViews.getChildAt(i).findViewById(R.id.list);
                SlideDownMenuAdapter adapter = (SlideDownMenuAdapter) lView.getAdapter();
                User.UserEntity.GroupsEntity group = mListTab.get(adapter.getmType());
                if (group != null) {
                    List<User.UserEntity.GroupsEntity.ChildgroupsEntity> dc = group.getChildgroups();
                    if (tabIndex == i) {
                        adapter.setCheckItem(position);
                        if (position == 0) {
                            setTabText(mListTab.get(tabIndex).getGroupname());
                        } else {
                            User.UserEntity.GroupsEntity.ChildgroupsEntity cg1 = dc.get(position - 1);
                            setTabText(cg1.getGroupname());
                        }
                    }
                    int checkPos = adapter.getCheckItemPosition();
                    if (checkPos == 0) continue;
                    if (dc != null) {
                        User.UserEntity.GroupsEntity.ChildgroupsEntity cg = dc.get(checkPos - 1);
                        if (i == 0) map.put("diagname", cg.getValue());
                        else map.put("bq", cg.getValue());
                    }
                }
            }
            mFrgList.onFilter(map);
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
        mFrgList.onClearSearch("py");
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
    protected boolean onSearch(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            String word = mEdtSearch.getText().toString().trim();
            if (!TextUtils.isEmpty(word)) {
                DeviceHelper.hideSoftKeyboard(v);
                mFrgList.onSearch("py", word);
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

    private void addTab(@NonNull List<String> tabTexts, int i) {
        final TextView tab = new TextView(this);
        tab.setSingleLine();
        tab.setEllipsize(TextUtils.TruncateAt.END);
        tab.setGravity(Gravity.CENTER);

        tab.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
        tab.setTextSize(12);
        tab.setTextColor(getResources().getColor(R.color.app_theme_primary_textcolor));
        tab.setText(tabTexts.get(i));
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
            View view = new View(this);
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
