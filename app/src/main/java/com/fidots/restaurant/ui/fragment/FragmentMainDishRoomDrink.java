package com.fidots.restaurant.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;

import com.fidots.restaurant.R;
import com.fidots.restaurant.ui.tabs.RestaurantTab;

import butterknife.ButterKnife;

/**
 * Created by Yong on 2016-02-16.
 */
public class FragmentMainDishRoomDrink extends Fragment implements TabHost.OnTabChangeListener {
    protected FragmentTabHost tabHost;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dishes_rooms_drinks, null);
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    protected void initView(View view) {
        tabHost = (FragmentTabHost) view.findViewById(android.R.id.tabhost);
        tabHost.setup(getActivity(), getChildFragmentManager(), android.R.id.tabcontent);
        tabHost.getTabWidget().setShowDividers(0);
        tabHost.setOnTabChangedListener(this);
        initTabs();
    }

    private void initTabs() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        RestaurantTab[] tabs = RestaurantTab.values();
        final int size = tabs.length;
        for (int i = 0; i < size; i++) {
            RestaurantTab tab1 = tabs[i];
            TabHost.TabSpec tab = tabHost.newTabSpec(getString(tab1.getResName()));
            View indicator = inflater.inflate(R.layout.tab_item, null);

            TextView title = (TextView) indicator.findViewById(R.id.tab_title);
            //Drawable drawable = this.getResources().getDrawable(tab1.getResIcon());
            //title.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
            String resName = getString(tab1.getResName());
            title.setText(resName);

            tab.setIndicator(indicator);
            tab.setContent(new TabHost.TabContentFactory() {
                @Override
                public View createTabContent(String tag) {
                    return new View(getActivity());
                }
            });

            Bundle bundle = new Bundle();
            if (i == 0) {
                bundle.putInt("layout", R.layout.item_dish);
            } else if (i == 2) {
                bundle.putInt("layout", R.layout.item_room);
            } else if (i == 3) {
                bundle.putInt("layout", R.layout.item_drink);
            }

            tabHost.addTab(tab, tab1.getClz(), bundle);
            if (i == 0) {
                tabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.image_sliding_block0);
            } else {
                tabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.color.white);
            }
        }
    }

    @Override
    public void onTabChanged(String tabId) {
        final int size = tabHost.getTabWidget().getTabCount();
        for (int i = 0; i < size; i++) {
            View v = tabHost.getTabWidget().getChildAt(i);
            if (i == tabHost.getCurrentTab()) {
                v.setSelected(true);
                v.setBackgroundResource(R.drawable.image_sliding_block0);
            } else {
                v.setSelected(false);
                v.setBackgroundResource(R.color.white);
            }
        }
    }
}
