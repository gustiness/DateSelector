package com.gustiness.calendar.DatePicker;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by gustiness on 2017/4/25.
 */

public class Item {
    /**
     * 该项对应的年份
     */
    private int year;
    /**
     * 该项对应的月份
     */
    private int month;
    /**
     * 该月份被选中的第一天
     */
    private int start = -1;
    /**
     * 该月份被选中的最后一天
     */
    private int end = -1;
    /**
     * 该月份的类型
     * SAME_MONTH 入住和离店在同一个月，从start到end被选中
     * START_MONTH 入住对应的月份，从start到该月最后一天被选中
     * END_MONTH 离店对应的月份，从1号到end被选中
     */
    private int type;
    /**
     * 该月的1号对应的位置
     * 如果5月1号为五月的第一个星期四，则pos1st为4（从0开始，星期日为0）
     */
    private int pos1st;
    /**
     * 该月份里的节假日
     */
    private ArrayList<HolidayItem> holidayItems;
    /**
     * 用来计算pos1st
     */
    private Calendar calendar;
    public Item(){
    }

    public Item( int year,int month) {
        this.month = month;
        this.year = year;
        calendar = Calendar.getInstance();

        calendar.set(year,month-1,1);
        pos1st = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        holidayItems = new ArrayList<>();
        holidayItems.add(new HolidayItem(6,"劳动节"));
        holidayItems.add(new HolidayItem(10,"随便节"));

    }

    public ArrayList<HolidayItem> getHolidayItems() {
        return holidayItems;
    }

    public void setHolidayItems(ArrayList<HolidayItem> holidayItems) {
        this.holidayItems = holidayItems;
    }

    public int getPos1st() {
        return pos1st;
    }

    public void setPos1st(int pos1st) {
        this.pos1st = pos1st;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setType(int type) {
        this.type = type;
    }
    public int getType(){
        return type;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
