package com.android.doctor.ui.fragment;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnItemClick;

/**
 * Created by Yong on 2016-02-14.
 */
public class FragmentDinnerClassify extends BaseFragment{
    @InjectView(R.id.tv_label)
    protected TextView mTvLabel;
    @InjectView(R.id.gv_category)
    protected GridView mGvCategory;
    private int mCatalog;

    public FragmentDinnerClassify(){}

    public static FragmentDinnerClassify newInstance(int catalog) {
        Bundle b = new Bundle();
        b.putInt("catalog", catalog);
        FragmentDinnerClassify f = new FragmentDinnerClassify();
        f.setArguments(b);
        return f;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_classify;
    }

    @Override
    protected void initData() {
        Bundle b = getArguments();
        if (b != null) {
            this.mCatalog = b.getInt("catalog");
        }
        fillView();
    }

    private void onLoad() {
        fillView();
    }

    public void fillView() {
        String []data_list = {"日韩料理","川菜","粤菜","日韩料理","川菜","粤菜"};
        List<Map<String, Object>> list_map = new ArrayList<>();
        for (String str : data_list) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("text", str);
            list_map.add(map);
        }

        String from[] = {"text"};
        int []to = {R.id.tv_text};

        SimpleAdapter adapter = new SimpleAdapter(getContext(), list_map, R.layout.textview, from, to);
        mGvCategory.setAdapter(adapter);
    }

    @OnItemClick(R.id.gv_category)
    protected void showMerchantAty() {
        UIHelper.showMerchantListAty(getContext());
    }
}
