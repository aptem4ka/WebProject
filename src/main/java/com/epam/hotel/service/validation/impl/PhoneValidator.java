package com.epam.hotel.service.validation.impl;

import com.epam.hotel.service.validation.Validator;

/**
 * This {@link Validator} implementation is used to validate phone numbers.
 *
 * @author Artsem Lashuk
 */
public class PhoneValidator implements Validator {
    /**
     * Phone number pattern.
     */
    private static final String PHONE_PATTERN="^\\+(?:[0-9] ?){6,14}[0-9]$";

    /**
     * This method validates phone number.
     *
     * @param phone is a form input
     * @return true if string matches the pattern.
     */
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
