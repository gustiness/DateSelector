//package com.gustiness.calendar;
//
///**
// * Created by 刘道兴 on 2017/4/26.
// */

//public class StartEndControler {
//    private int startYear;
//    private int startMonth;
//    private int startDay;
//    private int endYear;
//    private int endMonth;
//    private int endDay;
//    private Item startItem;
//    private Item endItem;
//    private boolean first;
//
//    public boolean monthClick(Item item){
//        if (first){
//            startItem.setMonth(item.getMonth());
//            startItem.setYear(item.getYear());
//        }else {
//            endItem.setMonth(item.getMonth());
//            endItem.setYear(item.getYear());
//            first = true;
//        }
//    }
//    public boolean dayClick(Item item){
//        if (first){
//            startItem.setDay(item.getDay());
//        }else {
//            endItem.setDay(item.getDay());
//        }
//    }
//    public StartEndControler(){
//        startYear = -1;
//        startMonth = -1;
//        startDay = -1;
//        endYear = -1;
//        endMonth = -1;
//        endDay = -1;
//    }
//
//    private void monthClick(Item item){
//        if (startMonth == -1){          //第一次点击
//            startMonth = item.getMonth();
//        }else if (endMonth == -1 &&
//                                ((startYear == item.getYear() && item.getMonth() > startMonth)
//                                  || startYear > item.getYear()) ){      //第二次点击，并且结束时间晚于开始时间
//            endMonth = item.getMonth();
//        }else {     //第二次点击&&结束时间早于开始时间 || 选好开始和结束日期之后，又点击一次
//            startMonth = item.getMonth();
//            endMonth = -1;
//        }
//
//        if (startYear == -1){          //第一次点击
//            startYear = item.getYear();
//        }else if (endYear == -1 && item.getYear() > startYear){      //第二次点击，并且结束时间晚于开始时间
//            endYear = item.getYear();
//        }else {     //第二次点击&&结束时间早于开始时间 || 选好开始和结束日期之后，又点击一次
//            startYear = item.getYear();
//            endYear = -1;
//        }
//
//    }
//    private void dayClick(Item item){
//        if (startDay == -1){          //第一次点击
//            startDay = item.getDay();
//        }else if (endDay == -1 && item.getDay() > startDay){      //第二次点击，并且结束时间晚于开始时间
//            endDay = item.getDay();
//        }else {     //第二次点击&&结束时间早于开始时间 || 选好开始和结束日期之后，又点击一次
//            startDay = item.getDay();
//        }
//    }
//}
