package com.android.doctor.ui.plan;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.app.AppContext;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.model.RespEntity;
import com.android.doctor.model.ScheduleCountList;
import com.android.doctor.model.User;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RespHandler;
import com.android.doctor.rest.RestClient;
import com.android.doctor.ui.base.BaseFragment;
import com.p_v.flexiblecalendar.FlexibleCalendarView;
import com.p_v.flexiblecalendar.entity.CalendarEvent;
import com.p_v.flexiblecalendar.view.BaseCellView;
import com.p_v.flexiblecalendar.view.SquareCellView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * Created by Yong on 2016/3/9.
 */
public class FragmentVisitPlan extends BaseFragment implements FlexibleCalendarView.OnMonthChangeListener,
        FlexibleCalendarView.OnDateClickListener{
    @InjectView(R.id.calendar_view)
    protected FlexibleCalendarView calendarView;
    @InjectView(R.id.tv_title)
    protected TextView mTvTitle;
    @InjectView(R.id.fl_container)
    protected FrameLayout mFlContainer;

    private FragmentRemindList fragmentRemind;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_visit_plan;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);

        initCalView();

        fragmentRemind = new FragmentRemindList();
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction trans = fm.beginTransaction();
        trans.replace(R.id.fl_container, fragmentRemind);
        trans.commitAllowingStateLoss();
        /*mTvTitle.setText(cal.getDisplayName(Calendar.MONTH, Calendar.LONG,
                this.getResources().getConfiguration().locale) + " " + cal.YEAR);*/
    }

    private void initCalView() {
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
    }

    private void updateTitle(int year, int month){
        month = month + 1;

        String tx = year + " " + month;
        mTvTitle.setText(tx);

        onLoadSchedule(year, month);
        fragmentRemind.onLoadRemindList(year ,month);
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


    private void setViewScheduleData(final int y, final int m, final Map<String, ScheduleCountList.DayEventCountEntity> map) {
        if (map == null) return;
        calendarView.refresh();
        calendarView.setEventDataProvider(new FlexibleCalendarView.EventDataProvider() {
            @Override
            public List<CalendarEvent> getEventsForTheDay(int year, int month, int day) {
                for (final ScheduleCountList.DayEventCountEntity v : map.values()) {
                    if (year == y && month + 1 == m && day == v.getDay()) {
                        List<CalendarEvent> eventColors = new ArrayList<>(1);
                        eventColors.add(new CalendarEvent(android.R.color.holo_purple));
                        eventColors.add(new CalendarEvent(android.R.color.holo_red_dark));
                        return eventColors;
                    }
                }
                return null;
            }
        });

    }

    @Override
    protected void initData() {
        super.initData();
        Calendar cal = Calendar.getInstance();
        int y = cal.get(Calendar.YEAR);
        int m = cal.get(Calendar.MONTH);
        updateTitle(y, m);
    }


    /**
     *
     * @param
     */
    private void onLoadSchedule(final int y, final int m) {

        List<String> dates = new ArrayList<>();
        String year = String.valueOf(y);
        String month = String.valueOf(m < 10 ? "0" + m : m);
        dates.add(year + month);
        User.UserEntity userEntity = AppContext.context().getUser();
        if (userEntity == null) { return;}

        Map<String, Object> map1 = new HashMap<>();
        map1.put("duid", userEntity.getDuid());
        map1.put("times", dates);
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<ScheduleCountList>> call = service.getDoctorSchedule(map1);
        call.enqueue(new RespHandler<ScheduleCountList>() {
            @Override
            public void onSucceed(RespEntity<ScheduleCountList> resp) {
                if (resp == null || resp.getResponse_params() == null) return;
                setViewScheduleData(y, m, resp.getResponse_params().getData());
            }

            @Override
            public void onFailed(RespEntity<ScheduleCountList> resp) {}
        });
    }


    @OnClick(R.id.img_list)
    protected void onPlanList() {
        User.UserEntity userEntity = AppContext.context().getUser();
        if (userEntity == null) return;
        PlanListActivity.startAty(getActivity(), userEntity.getDuid());
    }

    @OnClick(R.id.img_add)
    protected void onAddPlan() {
        UIHelper.showtAty(getActivity(), AddPlanActivity.class);
    }
}
