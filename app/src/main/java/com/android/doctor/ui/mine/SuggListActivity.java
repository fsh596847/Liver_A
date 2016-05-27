package com.android.doctor.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.app.AppContext;
import com.android.doctor.model.Constants;
import com.android.doctor.model.RespEntity;
import com.android.doctor.model.SuggClassList;
import com.android.doctor.model.User;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RestClient;
import com.android.doctor.ui.base.BaseActivity;
import com.android.doctor.app.DataCacheManager;
import com.android.doctor.ui.topic.FragmentSuggList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Yong on 2016-02-18.
 */
public class SuggListActivity extends BaseActivity {

    @InjectView(R.id.fl_container)
    protected FrameLayout mFlContainer;

    private SuggClassList.SuggEntity suggEntity;

    public SuggListActivity() {}

    public static void startAty(Context c, String uid) { //查看我收藏的文章列表
        Intent intent = new Intent();
        intent.putExtra("type", Constants.USER_COLLECTED_ARTICLE);
        intent.putExtra("uid", uid);
        intent.setClass(c, SuggListActivity.class);
        c.startActivity(intent);
    }

    public static void startAty(Context c, SuggClassList.SuggEntity sgcls) { //查看某个类别下的文章列表
        Intent intent = new Intent();
        intent.putExtra("type", Constants.ARTICLE_LIST_UNDER_CLASS);
        intent.putExtra("class", sgcls);
        intent.setClass(c, SuggListActivity.class);
        c.startActivity(intent);
    }

    @Override
    protected void setContentLayout() {
        setContentView(R.layout.activity_subs_deta);
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        int t = intent.getIntExtra("type", 0);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction trans = fm.beginTransaction();
        FragmentSuggList f = null;
        if (t == Constants.USER_COLLECTED_ARTICLE) {
            setActionBar(R.string.my_collect);
            String uid = intent.getStringExtra("uid");
            f = FragmentSuggList.newInstance(Constants.USER_COLLECTED_ARTICLE, uid);
        } else {
            suggEntity = intent.getParcelableExtra("class");
            if (suggEntity != null) {
                setActionBar(suggEntity.getName());
                judgeIfSubscribe("" + suggEntity.getCode());
                f = FragmentSuggList.newInstance(Constants.ARTICLE_LIST_UNDER_CLASS, suggEntity.getName());
            }
        }
        if (f != null) {
            trans.replace(R.id.fl_container, f);
            trans.commit();
        }
    }

    private void judgeIfSubscribe(String code) {
        SuggClassList.SuggEntity sg = DataCacheManager.getInstance().findSubjectByCode(code);
        if (sg == null) {
            setToobarRightText(R.string.subscribe);
        } else {
            setToobarRightText(R.string.unsubscribe);
        }
    }

    public void setToobarRightText(int res) {
        TextView view = (TextView) findViewById(R.id.img_subs);
        if (view != null) {
            view.setVisibility(View.VISIBLE);
            view.setText(res);
        }
    }



    @OnClick(R.id.img_subs)
    protected void processSubs(){
        String text = ((TextView) findViewById(R.id.img_subs)).getText().toString();
        final boolean isSubs = getString(R.string.subscribe).equals(text);
        Map<String,Object> param = getParam(isSubs);
        if (param == null) return;

        ApiService apiService = RestClient.createService(ApiService.class);
        Call<RespEntity> call = apiService.subOrUnSub(param);
        call.enqueue(new Callback<RespEntity>() {
            @Override
            public void onResponse(Call<RespEntity> call, Response<RespEntity> response) {
                //UIHelper.showToast(status == 0 ? "收藏成功" : "取消收藏成功");
                setToobarRightText(isSubs ? R.string.unsubscribe : R.string.subscribe);
                DataCacheManager.getInstance().onLoadCollectArticles();
            }

            @Override
            public void onFailure(Call<RespEntity> call, Throwable t) {
                //UIHelper.showToast(status == 0 ? "收藏失败" : "取消收藏失败");
            }
        });
    }

    public Map<String,Object> getParam(boolean isSubs) {
        if (suggEntity == null) return null;
        Map<String, Object> param = new HashMap<>();

        List<String> codeList = new ArrayList<>();
        List<SuggClassList.SuggEntity> collects = DataCacheManager.getInstance().getSubjects();
        if (collects != null) {
            for (int i = 0; i < collects.size(); ++i) {
                codeList.add(collects.get(i).getCode());
            }
        }

        if (isSubs) {
            codeList.add(suggEntity.getCode());
        } else {
            if (collects == null) return null;
            for (int i = 0; i < collects.size(); ++i) {
                SuggClassList.SuggEntity sg = collects.get(i);
                if (sg.getCode().equals(suggEntity.getCode())) {
                    continue;
                }
                codeList.add(sg.getCode());
            }
        }
        User.UserEntity u = AppContext.context().getUser();
        param.put("uid", u.getDuid());
        param.put("classids", codeList);
        return param;
    }
}
