package com.gustiness.calendar.DatePicker;

/**
 * Created by gustiness on 2017/5/3.
 */

public class HolidayItem {
    private int day;
    private String name;

    public HolidayItem(int day, String name) {
        this.day = day;
        this.name = name;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
