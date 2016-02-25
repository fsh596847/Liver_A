package com.fidots.restaurant.ui.fragment;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.fidots.restaurant.R;
import com.fidots.restaurant.ui.widget.AvatarView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Yong on 2016-02-12.
 */
public class FragmentRestaurantDetails extends Fragment {

    @InjectView(R.id.img_restaurant)
    protected ImageView mImgRest;
    @InjectView(R.id.fl_local_special)
    protected FrameLayout mFlSpecific;
    @InjectView(R.id.fl_dishes_rooms_drinks)
    protected FrameLayout mFlDishRoomDink;
    @InjectView(R.id.rl_location)
    protected RelativeLayout mRlLocation;
    @InjectView(R.id.tv_location)
    protected TextView mTvLocation;
    @InjectView(R.id.rl_call)
    protected RelativeLayout mRlCall;
    @InjectView(R.id.tv_phone_number)
    protected TextView mTvPhoneNumber;
    @InjectView(R.id.ll_book)
    protected LinearLayout mLlBook;
    @InjectView(R.id.ll_favorite)
    protected LinearLayout mLlFavorite;
    @InjectView(R.id.gv_consume_tips)
    protected GridView mGvConsumeTips;
    @InjectView(R.id.tv_business_hour)
    protected TextView mTvBusinessHour;
    @InjectView(R.id.ll_vouchers_container)
    protected LinearLayout mLlVouchesContainer;
    @InjectView(R.id.gv_vouches)
    protected GridView mGvVouches;
    @InjectView(R.id.rl_comment)
    protected RelativeLayout mRlComment;
    @InjectView(R.id.tv_net_friend_comment)
    protected TextView mTvNetComment;
    @InjectView(R.id.ll_comment_brief_container)
    protected LinearLayout mLlCommentBrief;
    @InjectView(R.id.tv_home)
    protected View mVgHome;
    @InjectView(R.id.tv_compliant)
    protected View mVgCompliant;
    @InjectView(R.id.tv_write_comment)
    protected View mVgWriteComment;

    private SimpleAdapter mConsumeTipsAdapter;
    private SimpleAdapter mVouchesAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);
        initView(view);
    }

    private void initView(View view) {
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction trans = fm.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putInt("layout", R.layout.item_dish_specific);
        FragmentTabDishRoom f = new FragmentTabDishRoom();
        f.setArguments(bundle);
        trans.replace(R.id.fl_local_special, f);
        trans.replace(R.id.fl_dishes_rooms_drinks, new FragmentMainDishRoomDrink());
        trans.commit();

        onLoad();
    }

    private void onLoad() {
        initViewConsumeTips(getConsumeTipsData());
        initViewVouchers(getViewVouchersData());
        initViewComments(null);
    }

    private void initViewConsumeTips(List data) {
        String [] from ={"image","text"};
        int [] to = {R.id.img_item,R.id.tv_text};
        mConsumeTipsAdapter = new SimpleAdapter(getActivity(), data, R.layout.item_consume_tips, from, to);
        mGvConsumeTips.setAdapter(mConsumeTipsAdapter);
    }

    private void initViewVouchers(List data) {
        String [] from ={"image","name","price","vendor"};
        int [] to = {R.id.img_voucher,R.id.tv_title, R.id.tv_price, R.id.tv_vendor};
        mVouchesAdapter = new SimpleAdapter(getActivity(), data, R.layout.item_voucher, from, to);
        mGvVouches.setAdapter(mVouchesAdapter);
    }

    private void initViewComments(List data) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.item_comment, null);
        AvatarView aView = (AvatarView) v.findViewById(R.id.avatar_view);
        TextView tvName = (TextView) v.findViewById(R.id.tv_username);
        TextView tvDate = (TextView) v.findViewById(R.id.tv_date);
        TextView tvEnvScore = (TextView) v.findViewById(R.id.tv_environment_score);
        TextView tvDishScore = (TextView) v.findViewById(R.id.tv_dishes_score);
        TextView tvServiceScore = (TextView) v.findViewById(R.id.tv_service_score);
        TextView tvContent = (TextView) v.findViewById(R.id.tv_comment_content);
        ImageView img01 = (ImageView) v.findViewById(R.id.img_01);
        ImageView img02 = (ImageView) v.findViewById(R.id.img_02);
        ImageView img03 = (ImageView) v.findViewById(R.id.img_03);
        mLlCommentBrief.addView(v);
    }

    private List getConsumeTipsData() {
        int[] icon = { R.drawable.wifi, R.drawable.wifi, R.drawable.wifi, R.drawable.wifi};
        String[] iconName = { "Wifi", "停车位", "可外带", "景观位置"};
        List<Map<String, Object>> data_list = new ArrayList<>();
        for(int i=0;i<icon.length;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", icon[i]);
            map.put("text", iconName[i]);
            data_list.add(map);
        }
        return data_list;
    }

    private List getViewVouchersData() {
        int[] icon = { R.drawable.vo, R.drawable.vo, R.drawable.vo};
        String[] name = { "自选套餐1人", "自选套餐2人", "自选套餐3人"};
        String[] price = { "89", "99", "100"};
        String[] vendor = { "美团", "美团", "大众"};
        List<Map<String, Object>> data_list = new ArrayList<>();
        for(int i=0;i<icon.length;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", icon[i]);
            map.put("name", name[i]);
            map.put("price", price[i]);
            map.put("vendor", vendor[i]);
            data_list.add(map);
        }
        return data_list;
    }
}
