package com.example.avjindersinghsekhon.minimaltodo;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by gufeifei on 1/18/16.
 */
public class CountingDate {

    private Date startDate;
    private Date dueDate;
    private Long timeSpan;
    private Date nowDate = Calendar.getInstance().getTime();

    public Date getDueDate() {
        return dueDate;
    }

    public CountingDate(Date startDate, Date dueDate){
        this.startDate = startDate;
        this.dueDate = dueDate;
    }

    public String CountingDate(Date startDate, Date dueDate){
        if((startDate != null) || (dueDate != null)){
            return null;
        }

        /*
        @moss to check how many minutes left
        there are some bugs, if only one minutes left, show chenge the text to "1 minute" left
        still can not show exactly how much time left
         */
//        if(timeSpan)
        if(timeSpan <60*60*1000){
            timeSpan = timeSpan/1000/60;
            return timeSpan + "minutes";
        }else if (timeSpan < 60*60*24*1000){
            timeSpan = timeSpan/60/60/1000;
            return timeSpan + "days";
        }else{
            return "no time left, hurry up";
        }
    }
}
