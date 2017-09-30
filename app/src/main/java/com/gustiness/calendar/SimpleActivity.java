package com.gustiness.calendar;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.gustiness.calendar.DatePicker.DatePickerView;
import com.gustiness.calendar.DatePicker.Item;
import com.gustiness.calendar.DatePicker.StartEndDayClickListener;

import java.util.ArrayList;

/**
 * Created by gustiness on 2017/5/4.
 */

public class SimpleActivity extends Activity{
    private ArrayList<Item> items;
    private DatePickerView datePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);

        items = new ArrayList<>();
        for (int i = 1; i < 12; i++){
            items.add(new Item(1901,i));
        }
        datePicker = (DatePickerView) findViewById(R.id.datePicker);
        datePicker.setItems(items);
        datePicker.setStartEndDaySelectListener(new StartEndDayClickListener(){
            @Override
            public void startDayClicked(Item item, int day) {
                StringBuilder sb = new StringBuilder();
                sb.append("开始时间：")
                        .append(item.getYear())
                        .append("-")
                        .append(item.getMonth())
                        .append("-")
                        .append(day);
                String str = sb.toString();
                Toast.makeText(SimpleActivity.this,str,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void endDayClicked(Item startItem, int startDay, Item endItem, int endDay) {
                StringBuilder sb = new StringBuilder();
                sb.append("开始时间：")
                        .append(startItem.getYear())
                        .append("-")
                        .append(startItem.getMonth())
                        .append("-")
                        .append(startDay)
                        .append(",结束时间：")
                        .append(endItem.getYear())
                        .append("-")
                        .append(endItem.getMonth())
                        .append("-")
                        .append(endDay);
                String str = sb.toString();
                Toast.makeText(SimpleActivity.this,str,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
