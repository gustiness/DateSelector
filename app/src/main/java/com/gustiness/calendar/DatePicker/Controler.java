package com.gustiness.calendar.DatePicker;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by gustiness on 2017/4/27.
 */

public class Controler {
    /**
     * 日历要显示的所有月份
     */
    private ArrayList<Item> items;
    /**
     * 入住对应的月份
     */
    private Item startItem;
    /**
     * 离店对应的月份
     */
    private Item endItem;
    /**
     * 入住的日期(几号)
     */
    private int startDay;
    /**
     *离店的日期(几号)
     */
    private int endDay;
    /**
     * 是否为首次点击，即是否在选择入住时间
     */
    private boolean first;     //是否为首次点击
    /**
     * ListView的Adapter
     */
    private DatePickerListAdapter datePickerListAdapter;
    /**
     * 当选择完入住、离店日期的时候执行的回调函数
     */
    private StartEndDayClickListener mListener;

    public Controler(ArrayList<Item> items){
      //  endItem = new Item();
        first = true;
        startDay = -1;
        endDay = -1;
        this.items = items;
    }

    /**
     * 当点击一个日期时，调用该方法
     * @param item
     * @param day
     */
    public void viewClicked(Item item, int day){
        if (first){     //选择入住时间
            resetAll();     //将所有月份都设置为未被选中
            startItem = item;
            startItem.setYear(item.getYear());
            startItem.setMonth(item.getMonth());
            startItem.setType(DatePickerItemView.START_MONTH);  //设置此月份为入住对应的月份
            startItem.setStart(day);
            startItem.setEnd(day);

            endItem = item;
            endItem.setYear(item.getYear());
            endItem.setMonth(item.getMonth());

            startDay = day;
            endDay = day;

            if (mListener != null){
                mListener.startDayClicked(item,day);        //选择开始时间的回调函数
            }

            first = false;
            Log.d("ldx","11111");
            Log.d("ldx",startItem.getYear()+","+startItem.getMonth());
        }else {     //已经选择过入住时间，此次为设置离店时间
            Log.d("ldx","22222");

            //如果第二次选择的时间晚于开始时间
            if (item.getYear() > startItem.getYear()
                    || (item.getYear() == startItem.getYear() && item.getMonth() > startItem.getMonth())
                    ||(item.getYear() == startItem.getYear() && item.getMonth() == startItem.getMonth() && startDay <= day)){

                endItem = item;
                endItem.setYear(item.getYear());
                endItem.setMonth(item.getMonth());
                ////
                // endItem = item;
                endDay = day;
                if (endItem.getMonth() == startItem.getMonth() && endItem.getYear() == startItem.getYear()){
                    item.setType(DatePickerItemView.SAME_MONTH);  //入住和离店在同一个月
                    Log.d("ldx","2.11111");
                    Log.d("ldx",startItem.getYear()+","+startItem.getMonth()+"|"+endItem.getYear()+","+endItem.getMonth());
                }else {
                    //入住和离店不在一个月
                    startItem.setEnd(Utils.getDaysInMonth(startItem.getMonth(),startItem.getYear()));
                    item.setType(DatePickerItemView.END_MONTH);
                    item.setStart(1);
                    Log.d("ldx","2.222222");
                }
                setMidMonth();      //把入住时间和离店时间中间的月份的类型设置为MID_MONTH

                item.setEnd(day);
                if (mListener != null){
                    mListener.endDayClicked(startItem,startDay,item,day);      //结束时间的回调函数
                }
                first = true;

            }else {     //如果第二次点击的时间早于开始的时间，相当于选择入住时间，所以逻辑和第一种情况相同
                resetAll();
                startItem = item;
                startItem.setYear(item.getYear());
                startItem.setMonth(item.getMonth());
                startDay = day;
                startItem.setType(DatePickerItemView.START_MONTH);  //设置此月份为入住对应的月份
                startItem.setStart(day);
                startItem.setEnd(day);
                ////
                endItem = item;
                endItem.setYear(item.getYear());
                endItem.setMonth(item.getMonth());
                endDay = day;

                if (mListener != null){
                    mListener.startDayClicked(item,day);        //选择开始时间的回调函数
                }
                first = false;
                Log.d("ldx","33333333");
            }
        }
        datePickerListAdapter.notifyDataSetChanged();     //刷新ListView
    }

    /**
     * 将入住和离店时间之间的月份的类型设为MID_MONTH
     */
    private void setMidMonth(){
        int startIndex = items.indexOf(startItem);
        int endIndex = items.indexOf(endItem);

        for (int i = startIndex + 1; i < endIndex; i++){
            items.get(i).setType(DatePickerItemView.MID_MONTH);
            items.get(i).setStart(1);
            items.get(i).setEnd(Utils.getDaysInMonth(items.get(i).getMonth(),items.get(i).getYear()));
        }
    }

    /**
     * 将所有月份都设置为未被选中
     */
    private void resetAll(){
        for (int i = 0; i < items.size(); i++){
            items.get(i).setStart(-1);
            items.get(i).setEnd(-1);
            items.get(i).setType(-1);
        }
    }

    public void setListener(StartEndDayClickListener mListener) {
        this.mListener = mListener;
    }
    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public void setDatePickerListAdapter(DatePickerListAdapter datePickerListAdapter) {
        this.datePickerListAdapter = datePickerListAdapter;
    }

    public int getEndDay() {
        return endDay;
    }

    public Item getEndItem() {
        return endItem;
    }

    public int getStartDay() {
        return startDay;
    }


    public Item getStartItem() {
        return startItem;
    }
}
