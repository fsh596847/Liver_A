package com.android.doctor.ui.plan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.ui.adapter.TopicListAdapter;
import com.android.doctor.ui.base.BaseRecyViewFragment;
import com.p_v.flexiblecalendar.FlexibleCalendarView;
import com.p_v.flexiblecalendar.entity.CalendarEvent;
import com.p_v.flexiblecalendar.view.BaseCellView;
import com.p_v.flexiblecalendar.view.SquareCellView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Yong on 2016/3/9.
 */
public class FragmentVisitPlan extends BaseRecyViewFragment implements FlexibleCalendarView.OnMonthChangeListener,
        FlexibleCalendarView.OnDateClickListener{
    @InjectView(R.id.calendar_view)
    protected FlexibleCalendarView calendarView;
    @InjectView(R.id.tv_title)
    protected TextView mTvTitle;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_visit_plan;
    }

    @Override
    protected void initView(View view) {

        calendarView.setShowDatesOutsideMonth(true);
        calendarView.setCalendarView(new FlexibleCalendarView.CalendarView() {
            @Override
            public BaseCellView getCellView(int position, View convertView, ViewGroup parent, @BaseCellView.CellType int cellType) {
                BaseCellView cellView = (BaseCellView) convertView;
                if (cellView == null) {
                    LayoutInflater inflater = LayoutInflater.from(getActivity());
                    cellView = (BaseCellView) inflater.inflate(R.layout.calendar_date_cell_view, null);
                }
                if(cellType == BaseCellView.OUTSIDE_MONTH){
                    cellView.setTextColor(getResources().getColor(R.color.divider_color));
                }
                return cellView;
            }

            @Override
            public BaseCellView getWeekdayCellView(int position, View convertView, ViewGroup parent) {
                BaseCellView cellView = (BaseCellView) convertView;
                if (cellView == null) {
                    LayoutInflater inflater = LayoutInflater.from(getActivity());
                    cellView = (SquareCellView) inflater.inflate(R.layout.calendar_week_cell_view, null);
                }
                return cellView;
            }

            @Override
            public String getDayOfWeekDisplayValue(int dayOfWeek, String defaultValue) {
                return null;
            }
        });
        calendarView.setOnMonthChangeListener(this);
        calendarView.setOnDateClickListener(this);
        calendarView.setEventDataProvider(new FlexibleCalendarView.EventDataProvider() {
            @Override
            public List<CalendarEvent> getEventsForTheDay(int year, int month, int day) {
                if (year == 2015 && month == 8 && day == 12) {
                    List<CalendarEvent> eventColors = new ArrayList<>(2);
                    eventColors.add(new CalendarEvent(android.R.color.holo_blue_light));
                    eventColors.add(new CalendarEvent(android.R.color.holo_purple));
                    return eventColors;
                }
                if (year == 2015 && month == 8 && day == 7 ||
                        year == 2015 && month == 8 && day == 29 ||
                        year == 2015 && month == 8 && day == 5 ||
                        year == 2015 && month == 8 && day == 9) {
                    List<CalendarEvent> eventColors = new ArrayList<>(1);
                    eventColors.add(new CalendarEvent(android.R.color.holo_blue_light));
                    return eventColors;
                }

                if (year == 2015 && month == 8 && day == 31 ||
                        year == 2015 && month == 8 && day == 22 ||
                        year == 2015 && month == 8 && day == 18 ||
                        year == 2015 && month == 9 && day == 11) {
                    List<CalendarEvent> eventColors = new ArrayList<>(3);
                    eventColors.add(new CalendarEvent(android.R.color.holo_red_dark));
                    eventColors.add(new CalendarEvent(android.R.color.holo_orange_light));
                    eventColors.add(new CalendarEvent(android.R.color.holo_purple));
                    return eventColors;
                }

                return null;
            }
        });

        Calendar cal = Calendar.getInstance();
        mTvTitle.setText(cal.getDisplayName(Calendar.MONTH, Calendar.LONG,
                this.getResources().getConfiguration().locale) + " " + cal.YEAR);
    }

    private void updateTitle(int year, int month){
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, 1);
        mTvTitle.setText(cal.getDisplayName(Calendar.MONTH, Calendar.LONG,
                this.getResources().getConfiguration().locale) + " " + year);
    }

    @Override
    public void onDateClick(int year, int month, int day) {

    }

    @Override
    public void onMonthChange(int year, int month, @FlexibleCalendarView.Direction int direction) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, 1);
        updateTitle(year, month);
    }

    @Override
    protected void setAdapter() {
        adapter = new TopicListAdapter();
        adapter.setItemOptionClickListener(this);
        recyclerView.setAdapter(adapter);
        adapter.setData(getTestData());
    }


    private List getTestData(){
        List list = new ArrayList();
        list.add(new Object());
        list.add(new Object());
        list.add(new Object());
        list.add(new Object());
        list.add(new Object());
        return list;
    }

    @Override
    public void onItemClick(int position, View view) {

    }

    @OnClick(R.id.img_list)
    protected void onPlanList() {
        UIHelper.showtAty(getActivity(), PlanListActivity.class);
    }

    @OnClick(R.id.img_add)
    protected void onAddPlan() {
        UIHelper.showtAty(getActivity(), AddPlanActivity.class);
    }
}
