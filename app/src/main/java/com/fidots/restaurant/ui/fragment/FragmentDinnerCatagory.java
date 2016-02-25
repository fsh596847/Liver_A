package com.fidots.restaurant.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.fidots.restaurant.R;
import com.fidots.restaurant.helper.UIHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;

/**
 * Created by Yong on 2016-02-14.
 */
public class FragmentDinnerCatagory extends Fragment{
    @InjectView(R.id.tv_label)
    protected TextView mTvLabel;
    @InjectView(R.id.gv_category)
    protected GridView mGvCategory;
    private int mCatalog;

    public FragmentDinnerCatagory(){}

    public FragmentDinnerCatagory(int catalog) {
        this.mCatalog = catalog;
    }

    public static FragmentDinnerCatagory newInstance(int catalog) {
        return new FragmentDinnerCatagory(catalog);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_dinner_category, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);
        fillUI();
    }

    private void onLoad() {

    }

    public void fillUI() {
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
