package com.android.doctor.ui.patient;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.android.doctor.R;
import com.android.doctor.model.ArchStatList;
import com.android.doctor.model.RespEntity;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RespHandler;
import com.android.doctor.rest.RestClient;
import com.android.doctor.ui.base.BaseActivity;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;
import retrofit2.Call;

/**
 * Created by Yong on 2016/4/6.
 */
public class LaborStatActivity extends BaseActivity {
    @InjectView(R.id.chart)
    protected LineChart mChart;
    private String pid;

    public static void startAty(Context c, String pid) {
        Intent intent = new Intent(c, LaborStatActivity.class);
        intent.putExtra("pid", pid);
        c.startActivity(intent);
    }

    @Override
    protected void setContentLayout() {
        setContentView(R.layout.activity_lab_stat);
    }

    @Override
    protected void init() {
        super.init();
        pid = getIntent().getStringExtra("pid");
    }

    @Override
    protected void initView() {
        setActionBar("");
        mChart.setDrawGridBackground(false);
        // no description text
        mChart.setDescription("");
        mChart.setNoDataTextDescription("You need to provide data for the chart.");
        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        // mChart.setScaleXEnabled(true);
        // mChart.setScaleYEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);

        // set an alternative background color
        // mChart.setBackgroundColor(Color.GRAY);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        //MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);

        // set the marker to the chart
        //mChart.setMarkerView(mv);

        // x-axis limit line
        LimitLine llXAxis = new LimitLine(10f, "Index 10");
        llXAxis.setLineWidth(4f);
        llXAxis.enableDashedLine(10f, 10f, 0f);
        llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        llXAxis.setTextSize(10f);

        XAxis xAxis = mChart.getXAxis();
        //xAxis.setValueFormatter(new MyCustomXAxisValueFormatter());
        //xAxis.addLimitLine(llXAxis); // add x-axis limit line

        LimitLine ll1 = new LimitLine(130f, "Upper Limit");
        ll1.setLineWidth(4f);
        ll1.enableDashedLine(10f, 10f, 0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll1.setTextSize(10f);

        LimitLine ll2 = new LimitLine(-30f, "Lower Limit");
        ll2.setLineWidth(4f);
        ll2.enableDashedLine(10f, 10f, 0f);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll2.setTextSize(10f);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        //leftAxis.addLimitLine(ll1);
        //leftAxis.addLimitLine(ll2);
        //leftAxis.setAxisMaxValue(220f);
        leftAxis.setAxisMinValue(0f);
        //leftAxis.setYOffset(20f);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);
        leftAxis.setDrawLabels(false);
        // limit lines are drawn behind data (and not on top)
        leftAxis.setDrawLimitLinesBehindData(true);

        mChart.getAxisRight().setEnabled(false);

        //mChart.getViewPortHandler().setMaximumScaleY(2f);
        //mChart.getViewPortHandler().setMaximumScaleX(2f);

        // add data

//        mChart.setVisibleXRange(20);
//        mChart.setVisibleYRange(20f, AxisDependency.LEFT);
//        mChart.centerViewTo(20, 50, AxisDependency.LEFT);

        mChart.animateX(2500, Easing.EasingOption.EaseInOutQuart);
//        mChart.invalidate();

        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();

        // modify the legend ...
        // l.setPosition(LegendPosition.LEFT_OF_CHART);
        l.setForm(Legend.LegendForm.LINE);

        // // dont forget to refresh the drawing
        // mChart.invalidate();
    }

    @Override
    protected void initData() {
        super.initData();
        getData();
    }

    private void setData(List<ArchStatList.ItemlistEntity> data) {
        if (data == null) return;
        String name = null;
        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < data.size(); i++) {
            ArchStatList.ItemlistEntity item = data.get(i);
            if (item == null) continue;
            xVals.add(item.getBegindate());
            if (TextUtils.isEmpty(name)) name = item.getName();
        }

        ArrayList<Entry> yVals = new ArrayList<Entry>();
        for (int i = 0; i < data.size(); i++) {
            ArchStatList.ItemlistEntity item = data.get(i);
            if (item == null) continue;
            float val = Float.parseFloat(item.getValue());
            yVals.add(new Entry(val, i));
        }

        LineDataSet set1 = new LineDataSet(yVals, name);

        // set the line to be drawn like this "- - - - - -"
        /*set1.enableDashedLine(10f, 5f, 0f);
        set1.enableDashedHighlightLine(10f, 5f, 0f);
        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);
        set1.setLineWidth(1f);
        set1.setCircleRadius(3f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);*/
        //Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_red);
        //set1.setFillDrawable(drawable);
        set1.setDrawFilled(false);

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData xlData = new LineData(xVals, dataSets);

        // set data
        mChart.setData(xlData);
    }

    private void getData() {
        Map<String,String> queryParam = new HashMap<>();
        queryParam.put("card", "140102195011060810");
        queryParam.put("eng", "HGB");
        queryParam.put("pid", pid);
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<ArchStatList>> call = service.getArchStatData(queryParam);
        call.enqueue(new RespHandler<ArchStatList>() {
            @Override
            public void onSucceed(RespEntity<ArchStatList> resp) {
                if (resp == null || resp.getResponse_params() == null){

                } else {
                    setData(resp.getResponse_params().getItemlist());
                }
            }

            @Override
            public void onFailed(RespEntity<ArchStatList> resp) {
            }
        });
    }

}
