package com.android.doctor.ui.topic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.android.doctor.R;
import com.android.doctor.helper.MenuHelper;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.interf.OnRefreshDataListener;
import com.android.doctor.model.Constants;
import com.android.doctor.model.RespEntity;
import com.android.doctor.model.TopicBarList;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RespHandler;
import com.android.doctor.rest.RestClient;
import com.android.doctor.ui.base.BaseFragment;

import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import retrofit2.Call;

/**
 * Created by Yong on 2016/3/9.
 */
public class FragmentMainTopic extends BaseFragment {

    @InjectView(R.id.header_view_frame)
    protected PtrClassicFrameLayout mPtrFrame;
    @InjectView(R.id.fl_container)
    protected FrameLayout frameLayout;
    @InjectView(R.id.img_menu)
    protected View viewMenu;
    private int mType = 1;
    private PopupMenu mPopup;
    private List<TopicBarList.TopicbarsEntity> mTopicBarList;
    private TopicBarList.TopicbarsEntity bar;
    private FragmentTopicList fragmentTopicList;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_topic;
    }

    @Override
    protected void init() {
        Bundle b = getArguments();
        if (b != null) {
            mType = b.getInt("type");
        }
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        initPRefreshView(view);
    }

    private void initPRefreshView(View view) {
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return fragmentTopicList.isScrollTop();
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mPtrFrame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fragmentTopicList.onRefresh();
                    }
                }, 500);
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        fragmentTopicList = FragmentTopicList.newInstance(Constants.TOPIC_TYPE_LIST_BY_BARID);
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction trans = fm.beginTransaction();
        trans.replace(R.id.fl_container, fragmentTopicList);
        trans.commitAllowingStateLoss();
        fragmentTopicList.setRefreshDataListener(new OnRefreshDataListener() {
            @Override
            public void onRefreshComplete(String msg) {
                mPtrFrame.refreshComplete();
            }
        });
        onLoadTopicBarList();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Constants.REQ_CODE_FOR_CREATE) {
                fragmentTopicList.onRefresh();
            }
        }
    }

    private void setTopicBarListView() {
        if (mPopup == null) {
            mPopup = new PopupMenu(getActivity(), viewMenu);
        }
        Menu menu = mPopup.getMenu();
        if (mTopicBarList == null) return;
        for (int i=0; i < mTopicBarList.size(); ++i) {
            TopicBarList.TopicbarsEntity tBar = mTopicBarList.get(i);
            menu.add(0, i, Menu.NONE, tBar.getTopicbarname());
        }
        onFilterTpByBarID(0);
    }

    @OnClick(R.id.img_menu)
    protected void showTopicBar(View view) {
        if (mTopicBarList == null || mTopicBarList.isEmpty()) return;

        MenuHelper.showPopupMenu(mPopup, new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                onFilterTpByBarID(item.getItemId());
                return false;
            }
        });
    }

    private void onFilterTpByBarID(int pos) {
        if (mTopicBarList == null) return;
        if (0 <= pos && pos < mTopicBarList.size()) {
            bar = mTopicBarList.get(pos);
            if (bar == null) return;
            String id = bar.getTopicbarid();
            fragmentTopicList.setmTopicBarId(id);
            fragmentTopicList.onRefresh();
        }
    }

    private void onLoadTopicBarList() {
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<TopicBarList>> call = service.getTopicBarList();
        call.enqueue(new RespHandler<TopicBarList>() {
            @Override
            public void onSucceed(RespEntity<TopicBarList> resp) {
                if (resp == null || resp.getResponse_params() == null) {
                    return;
                } else  {
                    mTopicBarList = resp.getResponse_params().getTopicbars();
                    setTopicBarListView();
                }
            }

            @Override
            public void onFailed(RespEntity<TopicBarList> resp) {
            }
        });
    }

    @OnClick(R.id.img_pub)
    protected void onPub() {
        if (bar == null) return;
        Intent intent = new Intent(getContext(), PubTopicActivity.class);
        intent.putExtra("barid", bar.getTopicbarid());
        intent.putExtra("barname", bar.getTopicbarname());
        startActivityForResult(intent, Constants.REQ_CODE_FOR_CREATE);
    }
}
