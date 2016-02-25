package com.fidots.restaurant.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.fidots.restaurant.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Yong on 2016-02-12.
 */
public class FragmentLocalYellowPage_old extends Fragment{
   // @InjectView(R.id.gv_yellow_page)
    protected GridView mGview;

    private SimpleAdapter mAdapter;
    // 图片封装为一个数组
    private int[] icon = { R.drawable.yellow_item, R.drawable.yellow_item,
            R.drawable.yellow_item, R.drawable.yellow_item, R.drawable.yellow_item,
            R.drawable.yellow_item, R.drawable.yellow_item, R.drawable.yellow_item};

    private String[] iconName = { "餐饮美食", "宾馆酒店", "旅游票务", "休闲娱乐",
            "公共事业", "家政服务", "汽车修理", "我的收藏"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_local_yellow_page, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);
        initView();
    }

    private void initView() {
        String [] from ={"image","text"};
        int [] to = {R.id.img_item,R.id.tv_text};
        mAdapter = new SimpleAdapter(getActivity(), getData(), R.layout.yellow_page_item, from, to);
        //配置适配器
        mGview.setAdapter(mAdapter);
    }

    public List<Map<String, Object>> getData(){
        List<Map<String, Object>> data_list = new ArrayList<>();
        for(int i=0;i<icon.length;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", icon[i]);
            map.put("text", iconName[i]);
            data_list.add(map);
        }
        return data_list;
    }
}
