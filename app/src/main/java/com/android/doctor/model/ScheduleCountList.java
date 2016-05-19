package com.android.doctor.model;

import java.util.Map;

/**
 * Created by Yong on 2016/5/4.
 */
public class ScheduleCountList {

    /**
     * D20160513 : {"day":13,"eventcount":4}
     * D20160523 : {"day":23,"eventcount":9}
     */

    private Map<String, DayEventCountEntity> data;

    public Map<String, DayEventCountEntity> getData() {
        return data;
    }

    public void setData(Map<String, DayEventCountEntity> data) {
        this.data = data;
    }

    public static class DayEventCountEntity {
        private int day;
        private int eventcount;

        public void setDay(int day) {
            this.day = day;
        }

        public void setEventcount(int eventcount) {
            this.eventcount = eventcount;
        }

        public int getDay() {
            return day;
        }

        public int getEventcount() {
            return eventcount;
        }
    }
}
