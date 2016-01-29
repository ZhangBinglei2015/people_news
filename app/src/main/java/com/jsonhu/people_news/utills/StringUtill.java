package com.jsonhu.people_news.utills;

import android.text.TextUtils;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class StringUtill {

    private final static String LOG_TAG = "StringUtill";
    public static String toDate(long sdate,String format) {
        SimpleDateFormat sdf= new SimpleDateFormat(format);
        //前面的lSysTime是秒数，先乘1000得到毫秒数，再转为java.util.Date类型
        Date dt = new Date(sdate * 1000);
        String sDateTime = sdf.format(dt);  //得到精确到秒的表示：08/31/2006 21:08:00
        return sDateTime;
    }
    public static List<String> sortStr(List<String> list) {
        Collections.sort(list);
        StringBuffer sb = new StringBuffer();
        for (String string : list) {
            sb.append(string);
        }
        return list;
    }

    public static boolean isHttpOk(String code) {
        if (code == null) {
            return false;

        } else if (code.equals("0")) {
            return true;
        }
        return false;

    }


    public static boolean isEmptyIncludeZero(String str) {
        if (str == null) {
            return true;
        } else if (str.equals("") || str.equals("0")) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(String str) {
        if (str == null) {
            return true;
        } else if (str.equals("")) {
            return true;
        }
        return false;
    }

    public static String getData(String dataStr) {
        try {
            Long date = Long.valueOf(dataStr + "000");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            return format.format(date);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;


    }

    public static String getDate(Object dataStr, String formatStr) {
        try {
            String s = String.valueOf(dataStr);
            long date = 0;
            if (s.length() == 10) {
                date = Long.valueOf(dataStr + "000");
            } else {
                date = Long.valueOf(s);
            }

            SimpleDateFormat format = new SimpleDateFormat(formatStr);
            return format.format(date);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;


    }

    /**
     * 获得距离现在多长时间
     *
     * @param timeStr
     * @return
     */

    public static String getHowLong(String timeStr) {
        String[] times = new String[4];
        try {
            if (timeStr.length() == 10) {
                timeStr += "000";
            }
            long l1 = Long.valueOf(timeStr);
            long l2 = new Date().getTime();
            if (l1 < l2) {
                return "-1";
            }
            long l3 = l1 - l2;

            long onesec = 1000;

            long oneMinute = 60 * onesec;
            long onehour = 60 * oneMinute;
            long oneDay = 24 * onehour;


            long day = l3 / oneDay;

            long hour = (l3 % oneDay) / onehour;
            long minute = (l3 % onehour) / oneMinute;
            long second = (l3 % oneMinute) / 1000;
            String dayStr = "";
            String horStr = "";
            String minuteStr = "";
            String secondStr = "";


            if (day > 0) {
                dayStr = day + "天 ";
                times[0] = dayStr;

            }
            if (hour > 0) {
                horStr = hour + "小时 ";
                times[1] = horStr;
            }
            if (minute > 0) {
                minuteStr = minute + "分 ";
                times[2] = minuteStr;
            }

            if (second > 0) {
                secondStr = second + "秒";
                times[3] = secondStr;
            }

        } catch (Exception e) {
            e.printStackTrace();

            return "未知";
        }

        StringBuffer sb = new StringBuffer();
        for (String s : times) {
            if (s != null) {
                sb.append(s);
            }
        }


        return sb.toString();
    }

    /**
     * 计算距离五小时还有多少
     *
     * @param timeStr 开始计时的时间
     * @return
     */

    public static String getHowLongInFiveHours(String timeStr) {
        String[] times = new String[4];

        long onesec = 1000;

        long oneMinute = 60 * onesec;
        long onehour = 60 * oneMinute;
        long oneDay = 24 * onehour;
        try {
            if (timeStr.length() == 10) {
                timeStr += "000";
            }
            long l1 = new Date().getTime();//当期时间
            long l2 = Long.valueOf(timeStr) + onehour * 5;//从最开始计时到5个小时之后的时间
            if (l2 < l1) {
                return "-1";
            }
            long l3 = l2 - l1;


            long day = l3 / oneDay;

            long hour = (l3 % oneDay) / onehour;
            long minute = (l3 % onehour) / oneMinute;
            long second = (l3 % oneMinute) / 1000;
            String dayStr = "";
            String horStr = "";
            String minuteStr = "";
            String secondStr = "";


            if (day > 0) {
                dayStr = day + "天 ";
                times[0] = dayStr;

            }
            if (hour > 0) {
                horStr = hour + "小时 ";
                times[1] = horStr;
            }
            if (minute > 0) {
                minuteStr = minute + "分 ";
                times[2] = minuteStr;
            }

            if (second > 0) {
                secondStr = second + "秒";
                times[3] = secondStr;
            }

        } catch (Exception e) {
            e.printStackTrace();

            return "未知";
        }

        StringBuffer sb = new StringBuffer();
        for (String s : times) {
            if (s != null) {
                sb.append(s);
            }
        }


        return sb.toString();
    }


    public static String getFormatString(String str) {

        if (StringUtill.isEmpty(str)) {
            return str;
        }

        if (str.length() < 5) {
            if (str.length() == 1) {
                return "0000" + str;
            } else if (str.length() == 2) {
                return "000" + str;
            } else if (str.length() == 3) {
                return "00" + str;

            } else if (str.length() == 4) {
                return "0" + str;

            }
        } else {
            return str;
        }

        return "";


    }

    public static boolean isTodaySin(String timeStr) {

        String formatStr = "yyyy年MM月dd日";
        String timeMiliStr = new Date().getTime() + "";

        String now = StringUtill.getDate(timeMiliStr, formatStr);
        String comTime = StringUtill.getDate(timeStr, formatStr);

        if (now.equals(comTime)) {
            return true;
        }
        return false;


    }

    public static String formatNumber(String number){
        String string = "";

        if (TextUtils.isEmpty(number)||number.equals("")|| number.equals("不做") || number.equals("沟通") || number.equals("待确认")){
            string = "沟通";
        }else {
            if (number.contains("万")){
                string  = number.substring(0,number.indexOf("万"));
            }else if (number.contains("千")){
                string  = number.substring(0,number.indexOf("千"));
            }else if (number.contains("亿")){
                string  = number.substring(0,number.indexOf("亿"));
            }else {
                string = number;
            }
        }

        return string;
    }


//    /**
//     * 格式化单位
//     * @param number
//     * @param type
//     * @return
//     */
//    public static String formatUnit(String number,String type){
//        String s;
//
//        if (TextUtils.isEmpty(number) || number.equals("不做") || number.equals("沟通") || number.equals("待确认")){
//            s = "";
//        }else {
//            if (number.contains("万")){
//                s = "万"+type;
//            }else if (number.contains("千")){
//                s = "千"+type;
//            } else if (number.contains("亿")){
//                s = "亿"+type;
//            }else if (number.equals("0")){
//                s = "万"+type;
//            }else {
//                s = type;
//            }
//        }
//
//        return s;
//    }

    public static void setWxPublishWay(TextView tv_position,String publish_position){
        if (publish_position.equals("1")){
            tv_position.setText("首条");
        }else if (publish_position.equals("2")){
            tv_position.setText("二条");
        }else if (publish_position.equals("3")){
            tv_position.setText("三条");
        }else if (publish_position.equals("4")){
            tv_position.setText("四条");
        }else if (publish_position.equals("5")){
            tv_position.setText("五条");
        }else if (publish_position.equals("6")){
            tv_position.setText("六条");
        }else if (publish_position.equals("7")){
            tv_position.setText("七条");
        }else if (publish_position.equals("8")){
            tv_position.setText("八条");
        } else {
            tv_position.setText("末条");
        }
    }

    public static void setWeiboPublishWay(TextView tv_position,String publish_position){
        if (publish_position.equals("1")){
            tv_position.setText("直发");
        }else if (publish_position.equals("2")){
            tv_position.setText("转发");
        }else {
            tv_position.setText("不限");
        }
    }

}
