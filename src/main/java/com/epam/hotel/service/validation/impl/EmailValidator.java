package com.epam.hotel.service.validation.impl;

import com.epam.hotel.service.validation.Validator;

/**
 * This {@link Validator} implementation validates email.
 *
 * @author Artsem Lashuk
 */
public class EmailValidator implements Validator {
    /**
     * Pattern for matching incoming e-mail.
     */
    private static final String EMAIL_PATTERN="^[\\w]+@[a-zA-Z]+\\.[a-z]+$";

    /**
     * Validates e-mail.
     *
     * @param email String with user input data during registration
     * @return true if the incoming e-mail matches the pattern
     */
    @Override
    public boolean isValid(Object email) {
        if (email==null){
            return false;
        }
        if (!(String.class==email.getClass())){
            return false;
        }

        String stringEmail=(String)email;

        return stringEmail.matches(EMAIL_PATTERN);

    }

}
