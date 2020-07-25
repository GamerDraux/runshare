package com.akproject.runshare.models.staticMethods;

public class TimeConversion{

    public static String displayTimeAsString (Integer time){
        Integer hours = time/3600;
        Integer minutes = (time-(hours*3600))/60;
        Integer seconds = (time%60);
        String minutesZero = "";
        String secondsZero = "";
        if (minutes<10){
            minutesZero="0";
        }
        if (seconds<10){
            secondsZero="0";
        }
        return hours+":"+minutesZero+minutes+":"+secondsZero+seconds;
    }
}
