package com.epam.hotel.service.validation.impl;

import com.epam.hotel.service.validation.Validator;

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

        if (date.before(new Date())){

            System.out.println(date);
            System.out.println(new Date());
            return false;
        }
        return true;
    }
}
