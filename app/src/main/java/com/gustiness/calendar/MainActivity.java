package com.gustiness.calendar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.gustiness.calendar.DatePicker.*;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<Item> items;
    private LinearLayout ll_weeks_title;
    private TextView tv_titles;
    private int lastFirstVisibleItem = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        items = new ArrayList<>();

        for (int i = 1; i < 12; i++){
            items.add(new Item(1901,i));
        }
        ll_weeks_title = (LinearLayout) findViewById(R.id.ll_week_title);
        tv_titles = (TextView) findViewById(R.id.tv_position);
        Controler controler = new Controler(items);
        DatePickerListAdapter datePickerListAdapter = new DatePickerListAdapter(this,items,controler);
        controler.setDatePickerListAdapter(datePickerListAdapter);
        controler.setListener(new StartEndDayClickListener(){

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
                Toast.makeText(MainActivity.this,str,Toast.LENGTH_SHORT).show();
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
                Toast.makeText(MainActivity.this,str,Toast.LENGTH_SHORT).show();
            }
        });
        listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(datePickerListAdapter);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem != lastFirstVisibleItem) {
                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) tv_titles.getLayoutParams();
                    params.topMargin = 0;
                    tv_titles.setLayoutParams(params);

                    StringBuilder sb = new StringBuilder();
                    sb.append(items.get(firstVisibleItem).getYear())
                            .append("年")
                            .append(items.get(firstVisibleItem).getMonth())
                            .append("月");
                    tv_titles.setText(sb.toString());
                }
                View childView = view.getChildAt(0);
                if (childView != null) {
//                    int titleHeight = tv_titles.getHeight();
                    int titleBottom = tv_titles.getHeight();

                    int bottom = childView.getBottom();
                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) tv_titles.getLayoutParams();
                    if (bottom < titleBottom) {
                        float pushedDistance = bottom - titleBottom;
                        params.topMargin = (int) pushedDistance;
                        tv_titles.setLayoutParams(params);
                    } else {
                        if (params.topMargin != 0) {
                            params.topMargin = 0;
                            tv_titles.setLayoutParams(params);
                        }
                    }
                }
                lastFirstVisibleItem = firstVisibleItem;
            }
        });

    }
}
