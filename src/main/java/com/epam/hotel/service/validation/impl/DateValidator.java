package com.epam.hotel.service.validation.impl;

import com.epam.hotel.service.validation.Validator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateValidator implements Validator {
    @Override
    public boolean isValid(Object obj) {
        if (obj==null){
            return false;
        }
        if (Date.class!=obj.getClass()){
            return false;
        }

        Date date=(Date)obj;


        if (date.before(getZeroTimeDate(new Date()))){
            return false;
        }
        return true;
    }

    private Date getZeroTimeDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        date = calendar.getTime();
        return date;
    }
}
