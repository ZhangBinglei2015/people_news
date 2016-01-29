package com.jsonhu.people_news.utills;

import android.os.CountDownTimer;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Administrator on 2015/4/25.
 */
public class TimeUtils {

    public TimeUtils(){
        super();
    }


    public DownFinish downFinish;



    public void setDownFinish(DownFinish downFinish) {
        this.downFinish = downFinish;
    }

    public interface DownFinish{
        void finish();
    }
    private static  Calendar calendar = Calendar.getInstance();
    private static  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    private  CountDownTimer timer;



    public CountDownTimer countDown(long interval, final TextView textView,final DownFinish downFinish){
        CountDownTimer timer = new CountDownTimer(interval,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished/1000;

                long one_day = 60 * 60 * 24;
                long one_hour  = 60 * 60;
                long one_minute = 60;
                long minute,second = 0L;;
                String time = "";
                minute = seconds % one_day % one_hour /  one_minute;
                second = seconds % one_day % one_hour %  one_minute;

                String leftminute = "";
                String leftseconds = "";


                if (minute<10){
                    leftminute = "0"+minute;
                }else {
                    leftminute = minute+"";
                }

                if (second<10){
                    leftseconds = "0"+second;
                }else {
                    leftseconds = second+"";
                }

                /*if(seconds < one_minute){
                    time = leftseconds + "秒";
                }else if(seconds >= one_minute && seconds < one_hour){
                    time = leftminute + ":" + leftseconds + ":";
                }*/
                textView.setText(leftminute+":"+leftseconds);
            }

            @Override
            public void onFinish() {
                textView.setText("00:00");
                if (downFinish != null){
                    downFinish.finish();
                }
            }
        };

        timer.start();
        return  timer;
    }





}
