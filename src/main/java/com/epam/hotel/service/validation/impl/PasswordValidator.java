package com.epam.hotel.service.validation.impl;

import com.epam.hotel.service.validation.Validator;

public class PasswordValidator implements Validator {
    private static final int MIN_PASSWORD_LENGTH=3;
    private static final int MAX_PASSWORD_LENGTH=16;
    private static final String PASSWORD_PATTERN="[a-zA-z0-9]+";

    @Override
    public boolean isValid(Object pass) {
        if (pass==null){
            return false;
        }
        if (!(String.class==pass.getClass())){
            return false;
        }

        String password=(String)pass;
        return password.length()>=MIN_PASSWORD_LENGTH && password.length()<=MAX_PASSWORD_LENGTH && password.matches(PASSWORD_PATTERN);
    }
}
