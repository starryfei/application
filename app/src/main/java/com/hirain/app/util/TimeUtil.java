package com.hirain.app.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

    public static String getCurrent(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String format = simpleDateFormat.format(new Date());
        return format;
    }

    public static String getTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm");
        String format = simpleDateFormat.format(new Date());
        return format;
    }
}
