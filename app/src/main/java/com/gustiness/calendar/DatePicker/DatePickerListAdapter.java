package com.gustiness.calendar.DatePicker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gustiness.calendar.R;

import java.util.ArrayList;

/**
 * Created by gustiness on 2017/4/25.
 */

public class DatePickerListAdapter extends BaseAdapter{
    private Context mContext;
    private ArrayList<Item> items;
    private Controler mControler;

//    private boolean oneItem = false;
//    private int firstChooseItem;
//    private int secondChooseItem;
//    private int firstItemStartDate;
//    private int firstItemEndDate;
//    private int secondItemStartDate;
//    private int secondItemEndDate;
//    private boolean sameMonth = true;

    public DatePickerListAdapter(Context context, ArrayList<Item> items, Controler controler){
        this.mContext = context;
        this.items = items;
        this.mControler = controler;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view;
        final ViewHolder viewHolder;
        if (convertView == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.item_day_picker,null);
            viewHolder = new ViewHolder(view);
            viewHolder.itemView.setmControler(mControler);
            view.setTag(viewHolder);
        }else{
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.itemView.setItem(items.get(position));
        viewHolder.itemView.invalidate();
        StringBuilder sb = new StringBuilder();
        sb.append(items.get(position).getYear())
                .append("年")
                .append(items.get(position).getMonth())
                .append("月");
        viewHolder.tv_year_month.setText(sb.toString());
//        if (!sameMonth){
//            viewHolder.itemView.setSelectedStart(0);
//            viewHolder.itemView.setSelectedEnd(items.get(position).getDayCount()-1);
//        }
//        viewHolder.itemView.setDayCount(items.get(position).getDayCount());
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final int pos = position;
//                final DatePickerItemView itemView = viewHolder.itemView;
//                if (firstChooseItem == -1){        //选择开始时间
//                    firstChooseItem = pos;
//                    firstItemStartDate = itemView.getSelectedStart();
//                }else if (secondChooseItem == -1){ //选择结束时间
//                    secondChooseItem = pos;
//                    if (secondChooseItem == firstChooseItem){       //开始时间和结束时间在同一个月
//                        oneItem = true;
//                        firstItemEndDate = itemView.getSelectedEnd();
//                        Log.d("ldx","start,end:"+firstItemStartDate+","+firstItemEndDate);
//                    }else {                         //开始时间和结束时间不在同一个月
//                        secondItemEndDate = itemView.getSelectedStart();        //第一次点击自定义view的时候设置为selectedStart
//                                                                                //第二次点击自定义view的时候设置为selectedEnd
//                        itemView.setSelectedEnd(itemView.getDayCount()-1);
//                        Log.d("ldx","start,end:"+firstItemStartDate+","+firstItemEndDate);
//                        sameMonth = false;
//                        notifyDataSetChanged();
//                        sameMonth = true;
//                    }
//                }else{                                  //重新选择时间
//                    firstChooseItem = pos;
//                    firstItemStartDate = itemView.getSelectedStart();
//                    secondChooseItem = -1;
//                    oneItem = false;
//                }
////                Log.d("ldx","first,second:"+firstChooseItem+","+secondChooseItem);
//            }
//        });
        return view;
    }

    private static class ViewHolder{
        private DatePickerItemView itemView;
        private TextView tv_year_month;
        public ViewHolder(View view){
            itemView = (DatePickerItemView)view.findViewById(R.id.item);
            tv_year_month = (TextView) view.findViewById(R.id.tv_year_month);
        }
    }
}
