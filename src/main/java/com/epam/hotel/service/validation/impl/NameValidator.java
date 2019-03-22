package com.epam.hotel.service.validation.impl;

import com.epam.hotel.service.validation.Validator;

/**
 * This {@link Validator} implementation validates names and surnames.
 *
 * @author Artsem Lashuk
 */
public class NameValidator implements Validator {
    /**
     * Min name or surname length.
     */
    private static final int MIN_NAME_LENGTH =2;
    /**
     * Max name or surname length.
     */
    private static final int MAX_NAME_LENGTH =16;
    /**
     * Name and surname pattern that rejects any symbols except
     * latin and cyrillic.
     */
    private static final String NAME_PATTERN ="[a-zA-zа-яА-Я]+";

    /**
     * This method validates user name in surname after submitting
     * registration form
     *
     * @param name string which contains user name or surname
     * @return true if name or surname matches the pattern
     */
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
