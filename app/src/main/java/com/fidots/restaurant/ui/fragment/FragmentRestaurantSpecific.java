package com.fidots.restaurant.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.android.volley.VolleyError;
import com.fidots.restaurant.R;
import com.fidots.restaurant.api.NetworkResponseHandler;
import com.fidots.restaurant.app.AppContext;
import com.fidots.restaurant.models.SpecificDish;
import com.fidots.restaurant.ui.adapter.RestaurantContentAdapter;
import com.fidots.restaurant.ui.fragment.base.BaseRecyViewFragment;
import com.fidots.restaurant.ui.widget.LinearSpacingItemDecoration;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yong on 2016-02-14.
 */
public class FragmentRestaurantSpecific extends BaseRecyViewFragment {

    private View viewMore;
    //private NearBrandFragmentListener mListener;
    private int mLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayout = getArguments().getInt("layout");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recycler_view;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
    }

    @Override
    protected void setLayoutManager() {
        layoutManager = new LinearLayoutManager(getActivity());
        ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
        ((LinearLayoutManager) layoutManager).setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new LinearSpacingItemDecoration(RECY_VIEW_ITEM_SPACE_8,
                LinearLayoutManager.HORIZONTAL));
    }

    @Override
    protected void setAdapter() {
        adapter = new RestaurantContentAdapter(mLayout);
        recyclerView.setAdapter(adapter);

        adapter.setData(getData());
    }

    protected void setupListener() {
        super.setupListener();
        //viewMore.setOnClickListener(this);
    }

    public List getData() {
        List data = new ArrayList();
        data.add(new SpecificDish());
        data.add(new SpecificDish());
        data.add(new SpecificDish());
        data.add(new SpecificDish());
        return data;
    }

    /*public void setListener(NearBrandFragmentListener listener) {
        mListener = listener;
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mDoLoad, new IntentFilter(SyncStateContract.Constants.SYNC()));
    }*/

    @Override
    protected String getURL(int pageNum, int limit) {
        //float []coord = PrefManager.getUserCoord(getActivity());
        //return ApiService.get_main_brands_info(coord);
        return "";
    }

    @Override
    protected NetworkResponseHandler getNetworkRespHandler() {
        return new NetworkResponseHandler() {
            @SuppressWarnings("unchecked")
            @Override
            public void onSuccess(JSONObject jsonObject) {
                /*List<?> list = processResponse(jsonObject);
                int iState = mState;
                setAdapterState(list);
                setViewState(iState);
                setAdapterData(list, iState);
                mListener.onNearBrandSyncFinishedListener(Constants.SYNC_RESULT_SUCCESS);*/
            }

            @Override
            public void onFail(VolleyError error) {
                //setViewState(mState);
                //mListener.onNearBrandSyncFinishedListener(Constants.SYNC_RESULT_FAILED);
            }
        };
    }

    protected List processResponse(JSONObject jsonObject) {
        if (jsonObject == null) {
            Log.e(TAG, "jsonObject is null");
            return null;
        }

        /*List<BaseBrand> list = null;
        Gson gson = new Gson();
        Type arrayType = new TypeToken<List<BaseBrand>>(){}.getType();
        try {
            String jsonStr = jsonObject.getString("retails");
            if (jsonStr == null) {
                Log.e(TAG, "get retails is null");
                return null;
            }

            list = gson.fromJson(jsonStr, arrayType);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        return null;
    }


    @Override
    public void onClick(View v) {
       /* switch (v.getId()) {
            case R.id.tv_more:
                UIHelper.showNearBrandList(getActivity());
                break;

            default:
                break;
        }*/
    }


    @Override
    public void onDestroy() {
        AppContext.context().cancelRequests(TAG);
        super.onDestroy();
    }

    @Override
    public void OnItemClick(int position, View view) {

    }
}
