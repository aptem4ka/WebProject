package com.epam.hotel.service.validation.impl;

import com.epam.hotel.service.validation.Validator;

/**
 * This {@link Validator} implementation validates passwords.
 *
 * @author Artsem Lashuk
 */
public class PasswordValidator implements Validator {
    /**
     * Min password length.
     */
    private static final int MIN_PASSWORD_LENGTH=3;
    /**
     * Max password length.
     */
    private static final int MAX_PASSWORD_LENGTH=16;
    /**
     * Password pattern. Only latin and digits.
     */
    private static final String PASSWORD_PATTERN="[a-zA-z0-9]+";

    /**
     * This method checks user password after submitting sign in or registration form.
     * @param pass entered password
     * @return true if password matches the pattern
     */
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
