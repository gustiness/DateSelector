package com.gustiness.calendar.DatePicker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.gustiness.calendar.R;

import java.util.ArrayList;

/**
 * Created by gustiness on 2017/4/23.
 */

public class DatePickerItemView extends View {
    private static final int COLUME = 7;
//    private static final String[] weeks = {"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};
//    private static final int WEEK_HEIGHT = 50;
    private static final int WEEK_HEIGHT = 0;

//    private static final int ITEM_PADDING = 5;
    public static final int SAME_MONTH = 0;    //0代表开始和结束在同一个月，1,2,3代表开始和结束不在同一个月
    public static final int START_MONTH = 1;    //开头的那个月
    public static final int END_MONTH = 2;      //中间的月份
    public static final int MID_MONTH = 3;      //结束的月份

    private Controler mControler;
    private Item item;
//    private int dayCount = 30;
    private float mWidth;
    private float mHeight;
    private float radius;
    private float mTopsub;
    private float mBottomsub;

    private float mTextTopsub;
    private float mTextBottomsub;
//    private int selectedStart = -1;
//    private int selectedEnd = -1;

    private Paint mDayNumberPaint;
    private Paint mDaySlectedBackGrountPaint;
    private Paint mDayTextPaint;

    public DatePickerItemView(Context context) {
        super(context);
        init();
    }

    public DatePickerItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecsize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        if (heightSpecMode == MeasureSpec.AT_MOST){
            int height = widthSpecsize / 7 * 6;
            setMeasuredDimension(widthSpecsize,height);
        }else if (heightSpecMode == MeasureSpec.UNSPECIFIED){
            int height = widthSpecsize / 7 * 6;
            setMeasuredDimension(widthSpecsize,height);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    private void init(){
        mDayNumberPaint = new Paint();
        mDayNumberPaint.setAntiAlias(true);
        mDayNumberPaint.setTextSize(30);
        mDayNumberPaint.setTextAlign(Paint.Align.CENTER);   //text绘制时，x方向居中
        mDayNumberPaint.setFakeBoldText(true);
        Paint.FontMetrics fontMetrics = mDayNumberPaint.getFontMetrics();
        mTopsub = fontMetrics.top / 2;
        mBottomsub = fontMetrics.bottom / 2;

//        mDayNumberPaint.setTextSize(MONTH_DAY_LABEL_TEXT_SIZE);
//        mDayNumberPaint.setColor(mDayTextColor);
//        mDayNumberPaint.setTypeface(Typeface.create(mDayOfWeekTypeface, Typeface.NORMAL));
//        mDayNumberPaint.setStyle(Style.FILL);



//        mDaySlectedBackGrountPaint
//        mMonthPaint
//        mYearPaint

        mDaySlectedBackGrountPaint = new Paint();
//        mDaySlectedBackGrountPaint.setColor(getResources().getColor(R.color.colorpPink));

        mDayTextPaint = new Paint();
        mDayTextPaint.setTextSize(18);
        mDayTextPaint.setAntiAlias(true);
        mDayTextPaint.setTextAlign(Paint.Align.CENTER);   //text绘制时，x方向居中
        mDayTextPaint.setFakeBoldText(true);
        mDayTextPaint.setColor(Color.WHITE);
        Paint.FontMetrics textFm = mDayNumberPaint.getFontMetrics();
        mTextTopsub = textFm.top / 2;
        mTextBottomsub = textFm.bottom / 2;
//        mDaySlectedBackGrountPaint.setFakeBoldText(true);
//        mDaySlectedBackGrountPaint.setAntiAlias(true);
//        mDaySlectedBackGrountPaint.setTextAlign(Align.CENTER);
//        mDaySlectedBackGrountPaint.setStyle(Style.FILL);
//        mDaySlectedBackGrountPaint.setAlpha(SELECTED_CIRCLE_ALPHA);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        drawWeekDay(canvas);
        drawDayNumberBackgroung(canvas);
        drawDayNumber(canvas);
        drawDayText(canvas);
    }
    private void drawDayText(Canvas canvas){
        int pos1st = item.getPos1st();
        int row;
        int col;
        float wid = mWidth / COLUME;
        float widsub = wid / 2;

        ArrayList<HolidayItem> holidayItems = item.getHolidayItems();
        mDayTextPaint.setColor(getResources().getColor(R.color.colorHolidayText));
        for (int i = 0; i < holidayItems.size(); i++){
            row = day2Pos(holidayItems.get(i).getDay()) / COLUME;
            col = day2Pos(holidayItems.get(i).getDay()) % COLUME;
            canvas.drawText(holidayItems.get(i).getName(),wid * col+widsub,wid * row-mTextBottomsub-mTextTopsub+5,mDayTextPaint);
        }

        mDayTextPaint.setColor(Color.WHITE);
        if (item.getType() == START_MONTH && item.getStart() != -1){
            row = day2Pos(item.getStart()) / COLUME;
            col = day2Pos(item.getStart()) % COLUME;
            canvas.drawText("入住",wid * col+widsub,wid * (row+1),mDayTextPaint);
        }else if (item.getType() == END_MONTH && item.getEnd() != -1){
            row = day2Pos(item.getEnd()) / COLUME;
            col = day2Pos(item.getEnd()) % COLUME;
            canvas.drawText("离店",wid * col+widsub,wid * (row+1),mDayTextPaint);
        }else if(item.getType() == SAME_MONTH && item.getEnd() != -1){
            if (item.getStart() != -1){
                row = day2Pos(item.getStart()) / COLUME;
                col = day2Pos(item.getStart()) % COLUME;
                canvas.drawText("入住",wid * col+widsub,wid *  (row+1),mDayTextPaint);
            }
            if (item.getEnd() != -1){
                row = day2Pos(item.getEnd()) / COLUME;
                col = day2Pos(item.getEnd()) % COLUME;
                canvas.drawText("离店",wid * col+widsub,wid * (row+1),mDayTextPaint);
            }
        }

    }


//    private void drawWeekDay(Canvas canvas){
//        float wid = mWidth / weeks.length;
//        for (int i = 0; i < weeks.length; i++){
//            canvas.drawText(weeks[i],wid*i+wid/2,WEEK_HEIGHT / 2 - mBottomsub - mTopsub,mDayNumberPaint);
//        }
//    }

    /**
     * 求本月的x号对应的位置，如果4月1日是四月第一个星期三，那么返回值为1日在该项对应的位置：3(从0开始)
     * @param day
     * @return
     */
    private int day2Pos(int day){
        return day - 1 + item.getPos1st();
    }
    private void drawDayNumberBackgroung(Canvas canvas){
        float wid = mWidth / COLUME;
        float widsub = wid / 2;
//        float r = widsub - ITEM_PADDING;
        int row;
        int col;
        switch (item.getType()){
            case START_MONTH:
                for (int i = item.getStart(); i <= item.getEnd() && i > 0; i++){
                    if (i == item.getStart()){
                        mDaySlectedBackGrountPaint.setColor(getResources().getColor(R.color.colorSelectBg));
                    }else {
                        mDaySlectedBackGrountPaint.setColor(getResources().getColor(R.color.colorNormalBg));
                    }
                    row = day2Pos(i) / COLUME;
                    col = day2Pos(i) % COLUME;
//                    canvas.drawCircle(col * wid + widsub,row * wid +WEEK_HEIGHT+widsub,r,mDaySlectedBackGrountPaint);
                    canvas.drawRect(col * wid,row * wid,(col+1)*wid,(row+1)*wid,mDaySlectedBackGrountPaint);
                }
                break;
            case MID_MONTH:
                mDaySlectedBackGrountPaint.setColor(getResources().getColor(R.color.colorNormalBg));
                for (int i = 1; i <= Utils.getDaysInMonth(item.getMonth(),item.getYear()); i++){
                    row = day2Pos(i) / COLUME;
                    col = day2Pos(i) % COLUME;
//                    canvas.drawCircle(col * wid + widsub,row * wid +WEEK_HEIGHT+widsub,r,mDaySlectedBackGrountPaint);
                    canvas.drawRect(col * wid,row * wid,(col+1)*wid,(row+1)*wid,mDaySlectedBackGrountPaint);

                }
                break;
            case SAME_MONTH:
                for (int i = item.getStart(); i <= item.getEnd() && i > 0; i++){
                    if (i == item.getStart() || i == item.getEnd()){
                        mDaySlectedBackGrountPaint.setColor(getResources().getColor(R.color.colorSelectBg));
                    }else {
                        mDaySlectedBackGrountPaint.setColor(getResources().getColor(R.color.colorNormalBg));
                    }
                    row = day2Pos(i) / COLUME;
                    col = day2Pos(i) % COLUME;
//                    canvas.drawCircle(col * wid + widsub,row * wid +WEEK_HEIGHT+widsub,r,mDaySlectedBackGrountPaint);
                    canvas.drawRect(col * wid,row * wid,(col+1)*wid,(row+1)*wid,mDaySlectedBackGrountPaint);

                }
                break;
            case END_MONTH:
                for (int i = item.getStart(); i <= item.getEnd(); i++){
                    if (i == item.getEnd()){
                        mDaySlectedBackGrountPaint.setColor(getResources().getColor(R.color.colorSelectBg));
                    }else {
                        mDaySlectedBackGrountPaint.setColor(getResources().getColor(R.color.colorNormalBg));
                    }
                    row = day2Pos(i) / COLUME;
                    col = day2Pos(i) % COLUME;
//                    canvas.drawCircle(col * wid + widsub,row * wid +WEEK_HEIGHT+widsub,r,mDaySlectedBackGrountPaint);
                    canvas.drawRect(col * wid,row * wid,(col+1)*wid,(row+1)*wid,mDaySlectedBackGrountPaint);

                }
                break;
            default:
                break;
        }

    }
    private void drawDayNumber(Canvas canvas){
        float wid = mWidth / COLUME;
        float widsub = wid / 2;
        float offset = widsub+WEEK_HEIGHT-mBottomsub-mTopsub;
        int row;
        int col;
        int pos1st = item.getPos1st();
        int dayCount = Utils.getDaysInMonth(item.getMonth(),item.getYear());
        for (int i = 0; i < dayCount; i++){
            if (((item.getType() == START_MONTH || item.getType() == SAME_MONTH) && i+1 == item.getStart())
                    || ((item.getType() == END_MONTH || item.getType() == SAME_MONTH) && i+1 == item.getEnd())){
                mDayNumberPaint.setColor(Color.WHITE);
            }else {
                mDayNumberPaint.setColor(Color.BLACK);
            }
            row = (pos1st + i) / COLUME;
            col = (pos1st + i) % COLUME;
            canvas.drawText((i+1)+"",wid * col+widsub,wid * row+offset,mDayNumberPaint);
        }
//        for (int i = item.getPos1st(); i < Utils.getDaysInMonth(item.getMonth(),item.getYear())+item.getPos1st()-1; i++){
//            row = i / COLUME;
//            col = i % COLUME;
//            canvas.drawText(i+"",wid * col+widsub,wid * row+offset,mDayNumberPaint);
//        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float wid = mWidth / COLUME;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
//            float x = event.getX();
//                float y = event.getY();
//                if (y < WEEK_HEIGHT || y > WEEK_HEIGHT + wid * 6){
//                    break;
//                }
//                int row = (int) ((y - WEEK_HEIGHT) / wid);
//                int col = (int)( x / wid);
//                int pos = row * COLUME + col;
//                Log.d("ldx","pos:"+pos);
//                int endPos = Utils.getDaysInMonth(item.getMonth(),item.getYear()) +item.getPos1st() - 1;
//                if (pos <= endPos && pos >= item.getPos1st()) {
////                    if (selectedStart == -1){                   //选择开始时间
////                        selectedStart = row * COLUME + col;
////                        selectedEnd = selectedStart;
////                    }else if (selectedStart == selectedEnd){    //选择结束时间
////                        selectedEnd = row * COLUME + col;
////                        if (selectedEnd < selectedStart){       //如果选择的结束时间早于开始时间
////                            selectedEnd = -1;
////                            selectedStart = -1;
////                        }
////                    }else {                                     //重新选择时间
////                        selectedStart = row * COLUME + col;
////                        selectedEnd = selectedStart;
////                    }
////                    invalidate();
//
//                    mControler.viewClicked(item,pos-item.getPos1st()+1);
//                    Log.d("ldx","real pos"+(pos-item.getPos1st()+1));
////                    Toast.makeText(getContext(),"row,col:"+row+","+col,Toast.LENGTH_SHORT).show();
//                }
                break;
            case MotionEvent.ACTION_UP:
                float x = event.getX();
                float y = event.getY();
                if (y < WEEK_HEIGHT || y > WEEK_HEIGHT + wid * 6){
                    break;
                }
                int row = (int) ((y - WEEK_HEIGHT) / wid);
                int col = (int)( x / wid);
                int pos = row * COLUME + col;
                Log.d("ldx","pos:"+pos);
                int endPos = Utils.getDaysInMonth(item.getMonth(),item.getYear()) +item.getPos1st() - 1;
                if (pos <= endPos && pos >= item.getPos1st()) {
//                    if (selectedStart == -1){                   //选择开始时间
//                        selectedStart = row * COLUME + col;
//                        selectedEnd = selectedStart;
//                    }else if (selectedStart == selectedEnd){    //选择结束时间
//                        selectedEnd = row * COLUME + col;
//                        if (selectedEnd < selectedStart){       //如果选择的结束时间早于开始时间
//                            selectedEnd = -1;
//                            selectedStart = -1;
//                        }
//                    }else {                                     //重新选择时间
//                        selectedStart = row * COLUME + col;
//                        selectedEnd = selectedStart;
//                    }
//                    invalidate();

                    mControler.viewClicked(item,pos-item.getPos1st()+1);
                    Log.d("ldx","real pos"+(pos-item.getPos1st()+1));
                }
                break;
        }
        return true;
    }

    public void setItem(Item item) {
        this.item = item;
    }

//    public int getDayCount() {
//        return dayCount;
//    }
//
//    public void setDayCount(int dayCount) {
//        this.dayCount = dayCount;
//    }

    public void setmControler(Controler mControler) {
        this.mControler = mControler;
    }



}
