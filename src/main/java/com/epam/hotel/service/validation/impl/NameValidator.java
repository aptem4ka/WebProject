package com.epam.hotel.service.validation.impl;

import com.epam.hotel.service.validation.Validator;

public class NameValidator implements Validator {
    private static final int MIN_NAME_LENGTH =2;
    private static final int MAX_NAME_LENGTH =16;
    private static final String NAME_PATTERN ="[a-zA-zа-яА-Я]+";

    @Override
    public boolean isValid(Object name) {
        if (name==null){
            return false;
        }
        if (!(String.class==name.getClass())){
            return false;
        }

        String stringName=(String)name;
        return stringName.length()>= MIN_NAME_LENGTH && stringName.length()<= MAX_NAME_LENGTH && stringName.matches(NAME_PATTERN);
    }
}
