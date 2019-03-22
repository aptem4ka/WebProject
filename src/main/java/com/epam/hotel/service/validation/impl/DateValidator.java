package com.epam.hotel.service.validation.impl;

import com.epam.hotel.service.validation.Validator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * This {@link Validator} implementation validates date
 *
 * @author  Artsem Lashuk
 * @see Date
 */
public class DateValidator implements Validator {

    /**
     * Validates Date. Date shouldn't be less then current date.
     *
     * @param obj incoming parameter to validate
     * @return true is the date is valid.
     */
    @Override
    public boolean isValid(Object obj) {
        if (obj==null){
            return false;
        }
        if (Date.class!=obj.getClass()){
            return false;
        }

        Date date=(Date)obj;

       return date.after(getZeroTimeDate(new Date()));
    }

    /**
     * This method is needed to set zero time for current Date object
     * for comparing with and incoming object which time always zero.
     * @param date {@link Date} current date
     * @return current date with zero time
     */
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
