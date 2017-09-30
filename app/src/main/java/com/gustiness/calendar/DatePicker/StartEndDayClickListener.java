package com.gustiness.calendar.DatePicker;

/**
 * Created by gustiness on 2017/4/27.
 */

public interface StartEndDayClickListener {
    void startDayClicked(Item item, int day);
    void endDayClicked(Item startItem, int startDay,Item endItem, int endDay);
}
