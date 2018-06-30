package com.summer.demo.StaticClass;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateParser {
    private static DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
    public static Date stringToDate(String s){
        Date res=new Date();
        try {
            res=format1.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static String dateToString(Date date){
        return format1.format(date);
    }

    public static Date getCurrentDate(){
        Date currentTime = new Date();

        String dateString = format1.format(currentTime);
        Date res=new Date();
        try {
            res=format1.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return res;
    }
}
