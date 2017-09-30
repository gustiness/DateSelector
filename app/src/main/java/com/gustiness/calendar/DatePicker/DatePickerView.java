package com.gustiness.calendar.DatePicker;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gustiness.calendar.R;

import java.util.ArrayList;

/**
 * Created by gustiness on 2017/5/4.
 */

public class DatePickerView extends RelativeLayout{
    private String[] weekDayTitle = {"日","一","二","三","四","五","六"};
    private Context mContext;
    private LinearLayout llWeekDayTitle;
    private RelativeLayout rlListViewContainer;
    private TextView yearMonthTitle;
    private ListView datePickListView;
    private Controler mControler;
    private int lastFirstVisibleItem = -1;
    private ArrayList<Item> items;
    public DatePickerView(Context context) {
        super(context);
    }

    public DatePickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = getContext();
        init();
        addWeekTitle();
        addMonthTitleAndListView();
    }
    private void init(){

    }

    /**
     * 将星期几的标题加入到view中
     */
    private void addWeekTitle(){
        llWeekDayTitle = new LinearLayout(mContext);
        llWeekDayTitle.setId(R.id.ll_week_title);
        llWeekDayTitle.setOrientation(LinearLayout.HORIZONTAL);
        llWeekDayTitle.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        for(int i = 0; i < weekDayTitle.length; i++){
            TextView tv = new TextView(mContext);
            if (i == 0 || i == weekDayTitle.length - 1){
                tv.setTextColor(Color.BLUE);
            }
            tv.setText(weekDayTitle[i]);
            tv.setGravity(Gravity.CENTER);
            tv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,1.0f));
            tv.setPadding(0,Utils.dp2Px(mContext,10),0,Utils.dp2Px(mContext,10));
            llWeekDayTitle.addView(tv);
        }

        addView(llWeekDayTitle);
    }

    /**
     * 将显示月份的标题和listview加入到view中
     */
    private void addMonthTitleAndListView(){
        rlListViewContainer = new RelativeLayout(mContext);
        LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.BELOW,llWeekDayTitle.getId());
        rlListViewContainer.setLayoutParams(lp);

        datePickListView = new ListView(mContext);
        datePickListView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        datePickListView.setSelector(new ColorDrawable());
        rlListViewContainer.addView(datePickListView);

        yearMonthTitle = new TextView(mContext);
        yearMonthTitle.setPadding(0,Utils.dp2Px(mContext,5),0,Utils.dp2Px(mContext,5));
        yearMonthTitle.setGravity(Gravity.CENTER);
        yearMonthTitle.setBackgroundColor(getResources().getColor(R.color.colorAlphaGray));
        LayoutParams tvLp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tvLp.addRule(RelativeLayout.ALIGN_PARENT_TOP,TRUE);
        yearMonthTitle.setLayoutParams(tvLp);
        rlListViewContainer.addView(yearMonthTitle);

        addView(rlListViewContainer);
    }

    /**
     * 设置ArrayList<Item>之后，重新设置ListView的Adapt和Controler
     */
    private void resetControlerAndListView(){
        mControler = new Controler(items);
        DatePickerListAdapter datePickerListAdapter = new DatePickerListAdapter(mContext,items,mControler);
        mControler.setDatePickerListAdapter(datePickerListAdapter);
        datePickListView.setAdapter(datePickerListAdapter);
        datePickListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.d("ldx","firstVisibleItem:"+firstVisibleItem);
                if (firstVisibleItem != lastFirstVisibleItem) {
                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) yearMonthTitle.getLayoutParams();
                    params.topMargin = 0;
                    yearMonthTitle.setLayoutParams(params);

                    StringBuilder sb = new StringBuilder();
                    sb.append(items.get(firstVisibleItem).getYear())
                            .append("年")
                            .append(items.get(firstVisibleItem).getMonth())
                            .append("月");
                    yearMonthTitle.setText(sb.toString());
                }
                View childView = view.getChildAt(0);
                if (childView != null) {
//                    int titleHeight = tv_titles.getHeight();
                    int titleBottom = yearMonthTitle.getHeight();
                    int bottom = childView.getBottom();
                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) yearMonthTitle.getLayoutParams();
                    if (bottom < titleBottom) {
                        float pushedDistance = bottom - titleBottom;
                        params.topMargin = (int) pushedDistance;
                        yearMonthTitle.setLayoutParams(params);
                    } else {
                        if (params.topMargin != 0) {
                            params.topMargin = 0;
                            yearMonthTitle.setLayoutParams(params);
                        }
                    }
                }
                lastFirstVisibleItem = firstVisibleItem;
            }
        });
    }
    public void setItems(ArrayList<Item> items) {
        this.items = items;
        resetControlerAndListView();
    }

    /**
     * 设置选择入住时间和离店时间后执行的回调函数
     * @param startEndDaySelectListener
     */
    public void setStartEndDaySelectListener(StartEndDayClickListener startEndDaySelectListener){
        mControler.setListener(startEndDaySelectListener);
    }
    public ArrayList<Item> getItems() {
        return items;
    }

}
