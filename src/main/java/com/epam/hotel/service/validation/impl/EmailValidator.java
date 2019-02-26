package com.epam.hotel.service.validation.impl;

import com.epam.hotel.service.validation.Validator;

public class EmailValidator implements Validator {
    private static final String EMAIL_PATTERN="^[\\w]+@[a-zA-Z]+\\.[a-z]+$";

    @Override
    public boolean isValid(Object email) {
        if (email==null){
            return false;
        }
        if (!(String.class==email.getClass())){
            return false;
        }

        String stringEmail=(String)email;
        System.out.println("email matcher: "+ stringEmail.matches(EMAIL_PATTERN));
        return stringEmail.matches(EMAIL_PATTERN);

    }

}
