package com.epam.hotel.service.validation.impl;

import com.epam.hotel.service.validation.Validator;

public class PhoneValidator implements Validator {
    private static final String PHONE_PATTERN="^\\+(?:[0-9] ?){6,14}[0-9]$";

    @Override
    public boolean isValid(Object phone) {
        if (phone==null){
            return false;
        }
        if (!(String.class==phone.getClass())){
            return false;
        }

        String stringPhone=(String)phone;

        return stringPhone.matches(PHONE_PATTERN);
    }
}
