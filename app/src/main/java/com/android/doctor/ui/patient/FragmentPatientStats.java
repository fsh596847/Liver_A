package com.android.doctor.ui.patient;

import android.graphics.Color;
import android.util.Log;
import android.view.View;

import com.android.doctor.R;
import com.android.doctor.app.AppContext;
import com.android.doctor.model.PatientStats;
import com.android.doctor.model.RespEntity;
import com.android.doctor.model.User;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RespHandler;
import com.android.doctor.rest.RestClient;
import com.android.doctor.ui.base.BaseFragment;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Yong on 2016/3/9.
 */
public class FragmentPatientStats extends BaseFragment {
    @InjectView(R.id.chart_pie_state)
    protected PieChart mCharState;
    @InjectView(R.id.chart_column_disease)
    protected BarChart mChartDisease;
    @InjectView(R.id.chart_column_region)
    protected BarChart mChartRegion;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_patient_stat;
    }

    @Override
    protected void initView(View view) {
        initPieChartView(mCharState);
        initBarChartView(mChartDisease);
        initBarChartView(mChartRegion);
    }

    private void initPieChartView(PieChart pieChart) {
        pieChart.setUsePercentValues(true);
        pieChart.setDescription("");
        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        //tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        //pieChart.setCenterTextTypeface(Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf"));
        pieChart.setCenterText("患者统计");
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);
        pieChart.setHoleRadius(58f);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setDrawCenterText(true);
        pieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);
        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
        //pieChart.setOnChartValueSelectedListener(this);

        pieChart.animateY(3000, Easing.EasingOption.EaseOutElastic);
        pieChart.spin(3000, 0, 360,Easing.EasingOption.EaseOutElastic);

        Legend l = pieChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
        //l.setExtra(null, Arrays.asList(new String[]{"按状态"}));
        // undo all highlights
        /*pieChart.highlightValues(null);
        pieChart.setBackgroundResource(android.R.color.white);
        pieChart.invalidate();*/
    }


    private void initBarChartView(BarChart barChart) {
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.setDescription("");

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        barChart.setMaxVisibleValueCount(60);
        barChart.animateY(5000, Easing.EasingOption.EaseInOutQuad);
        // scaling can now only be done on x- and y-axis separately
        barChart.setPinchZoom(false);

        barChart.setDrawGridBackground(false);
        // mChart.setDrawYLabels(false);

        //mTf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //xAxis.setTypeface(mTf);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(2);

        //YAxisValueFormatter custom = new MyYAxisValueFormatter();

        YAxis leftAxis = barChart.getAxisLeft();
        //leftAxis.setTypeface(mTf);
        leftAxis.setLabelCount(8, false);
        //leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawLabels(false);
        leftAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawLabels(false);
        //rightAxis.setTypeface(mTf);
        rightAxis.setLabelCount(8, false);
        //rightAxis.setValueFormatter(custom);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)

        Legend l = barChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
        // l.setExtra(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });
        // l.setCustom(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });

        // setting data
        //mSeekBarY.setProgress(50);
        //mSeekBarX.setProgress(12);

        //mSeekBarY.setOnSeekBarChangeListener(this);
        //mSeekBarX.setOnSeekBarChangeListener(this);
    }

    @Override
    protected void initData() {
        onLoad();
    }

    private void onLoad() {
        User.UserEntity u = AppContext.context().getUser();
        if (u == null) {
            return;
        }
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<PatientStats>> call = service.getDoctorPStats(u.getDuid());
        call.enqueue(new RespHandler<PatientStats>() {
            @Override
            public void onSucceed(RespEntity<PatientStats> resp) {
                if (resp != null)
                onSuccess(resp.getResponse_params());
            }

            @Override
            public void onFailed(RespEntity<PatientStats> resp) {

            }
        });
    }

    private void onSuccess(PatientStats data) {
        if (data == null) return;
        PatientStats.DataEntity de = data.getData();
        if (de != null) {
            Log.d("FragmentPatientStats", "onSucceed");
            setPieChartData(de.getStatus());
            setDiagChartData(de.getDiag());
            setRegionChartData(de.getRegion());
        }
    }

    private void setPieChartData(List<PatientStats.DataEntity.StatusEntity> data) {
        if (data == null || data.isEmpty()) return;

        int count = data.size();
        ArrayList<Entry> yVals = new ArrayList<Entry>();
        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            PatientStats.DataEntity.StatusEntity e = data.get(i);
            yVals.add(new Entry((float) e.getNums(), i));
            xVals.add("" + e.getName());
        }
        PieDataSet dataSet = new PieDataSet(yVals, "按状态");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        colors.add(Color.rgb(255, 255, 255));

        /*for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);*/
        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);
        PieData pdata = new PieData(xVals, dataSet);

        pdata.setValueFormatter(new PercentFormatter());
        pdata.setValueTextSize(11f);
        pdata.setValueTextColor(Color.WHITE);
        //data.setValueTypeface(tf);
        mCharState.setData(pdata);
        mCharState.highlightValues(null);
        mCharState.invalidate();
    }

    private void setDiagChartData( List<PatientStats.DataEntity.DiagEntity> data) {
        if (data == null || data.isEmpty()) return;

        int count = data.size();
        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<BarEntry> yVals = new ArrayList<BarEntry>();
        for (int i = 0; i < count; i++) {
            PatientStats.DataEntity.DiagEntity e = data.get(i);
            xVals.add("" + e.getName());
            yVals.add(new BarEntry(e.getNums(), i));
        }

        BarDataSet set1 = new BarDataSet(yVals, "按病种");
        set1.setBarSpacePercent(35f);

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        set1.setColors(colors);
        BarData bdata = new BarData(xVals, dataSets);
        bdata.setValueTextSize(10f);
        //data.setValueTypeface(mTf);
        mChartDisease.setData(bdata);
    }

    private void setRegionChartData( List<PatientStats.DataEntity.RegionEntity> data) {
        if (data == null || data.isEmpty()) return;
        int count = data.size();
        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<BarEntry> yVals = new ArrayList<BarEntry>();
        for (int i = 0; i < count; i++) {
            PatientStats.DataEntity.RegionEntity e = data.get(i);
            xVals.add("" + e.getName());
            yVals.add(new BarEntry(e.getNums(), i));
        }

        BarDataSet set1 = new BarDataSet(yVals, "按区域");
        set1.setBarSpacePercent(35f);

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);

        BarData bdata = new BarData(xVals, dataSets);
        bdata.setValueTextSize(10f);
        //data.setValueTypeface(mTf);
        mChartRegion.setData(bdata);
    }
}
