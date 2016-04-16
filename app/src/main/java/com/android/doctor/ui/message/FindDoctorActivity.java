package com.android.doctor.ui.message;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.helper.DeviceHelper;
import com.android.doctor.ui.adapter.PopDownMenuAdapter;
import com.android.doctor.ui.base.BaseActivity;

import java.util.Arrays;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by Yong on 2016/3/28.
 */
public class FindDoctorActivity extends BaseActivity {

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
    protected FrameLayout mPopupMenuViews;
    @InjectView(R.id.iv_clear)
    protected ImageView mIvClear;
    @InjectView(R.id.edt_search_box)
    protected EditText mEdtSearch;
    @InjectView(R.id.tv_cancel)
    protected TextView mTvCancel;

    private int current_tab_position = -1;

    private String[] ages = {"14岁以下", "14-22", "23-45", "46-65", "66以上", "58-60"};
    private String[] dis = {"乙肝", "甲肝", "丙肝"};
    private String[] stat = {"慢性", "急性", "重度"};
    private String[] state = {"门诊", "出院", "随诊"};

    @Override
    protected void setContentLayout() {
        setContentView(R.layout.activity_list_doctor);
    }

    @Override
    protected void initView() {
        setActionBar(R.string.find_doctor);
        initHeaderView();
        initListView();
        initPopdownTab();
    }

    private void initHeaderView() {
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                RecyclerView rcView = (RecyclerView) mFlContainer.findViewById(R.id.recyc_view);
                LinearLayoutManager lm = (LinearLayoutManager) rcView.getLayoutManager();
                if (lm.findFirstVisibleItemPosition() == 0) {
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

    private void initListView() {
        FragmentDoctorList fl = new FragmentDoctorList();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction trans = fm.beginTransaction();
        trans.replace(R.id.fl_container, fl).commit();
    }

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

    private void initPopdownTab() {
        setPopdownTabListener();
        mPopupMenuViews.addView(getPopdownView(Arrays.asList(ages)), 0);
        mPopupMenuViews.addView(getPopdownView(Arrays.asList(dis)), 1);
        mPopupMenuViews.addView(getPopdownView(Arrays.asList(stat)), 2);
    }

    private View getPopdownView(List<String> data) {
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.popup_listview, null);
        ListView list = (ListView) view.findViewById(R.id.list);
        PopDownMenuAdapter adapter = new PopDownMenuAdapter(this, data, R.layout.textview, 0, false);
        list.setAdapter(adapter);
        return view;
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
                if (current_tab_position == i) {
                    closeMenu();
                } else {
                    if (current_tab_position == -1) {
                        mPopupMenuViews.setVisibility(View.VISIBLE);
                        mPopupMenuViews.setAnimation(AnimationUtils.loadAnimation(this, R.anim.dd_menu_in));
                        mMaskView.setVisibility(View.VISIBLE);
                        mMaskView.setAnimation(AnimationUtils.loadAnimation(this, R.anim.dd_mask_in));
                        mPopupMenuViews.getChildAt(i / 2).setVisibility(View.VISIBLE);
                    } else {
                        mPopupMenuViews.getChildAt(i / 2).setVisibility(View.VISIBLE);
                    }
                    current_tab_position = i;
                    ((TextView) mLlTabMenu.getChildAt(i))
                            .setTextColor(getResources().getColor(R.color.pop_tab_text_selected_color));
                }
            } else {
                ((TextView) mLlTabMenu.getChildAt(i))
                        .setTextColor(getResources()
                                .getColor(R.color.pop_tab_text_unselected_color));
                mPopupMenuViews.getChildAt(i / 2).setVisibility(View.GONE);
            }
        }
    }


    public void closeMenu() {
        if (current_tab_position != -1) {
            ((TextView) mLlTabMenu.getChildAt(current_tab_position))
                    .setTextColor(getResources().getColor(R.color.pop_tab_text_unselected_color));
            mPopupMenuViews.setVisibility(View.GONE);
            mPopupMenuViews.setAnimation(AnimationUtils.loadAnimation(this, R.anim.dd_menu_out));
            mMaskView.setVisibility(View.GONE);
            mMaskView.setAnimation(AnimationUtils.loadAnimation(this, R.anim.dd_mask_out));
            current_tab_position = -1;
        }
    }

}
