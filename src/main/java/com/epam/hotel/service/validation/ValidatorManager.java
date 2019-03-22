package com.epam.hotel.service.validation;

import com.epam.hotel.service.validation.impl.*;

import java.util.HashMap;
import java.util.Map;

/**
 * A realization of "Command" pattern
 *
 * @author Artsem Lashuk
 */
public class ValidatorManager {
    private final static ValidatorManager instance=new ValidatorManager();
    private final Map<ValidatorName, Validator> validators=new HashMap<>();

    /**
     * Constructor initializes validators according to the {@link ValidatorName}
     */
    private ValidatorManager(){
        validators.put(ValidatorName.EMAIL, new EmailValidator());
        validators.put(ValidatorName.PASSWORD, new PasswordValidator());
        validators.put(ValidatorName.NAME, new NameValidator());
        validators.put(ValidatorName.PHONE, new PhoneValidator());
        validators.put(ValidatorName.DATE, new DateValidator());
        validators.put(ValidatorName.REVIEW, new ReviewValidator());
        validators.put(ValidatorName.ORDER, new OrderValidator());
    }

    /**
     * Get instance of validator manager.
     *
     * @return {@link ValidatorManager}
     */
    public static ValidatorManager getInstance() {
        return instance;
    }

    /**
     * Get specified validator according to incoming parameter.
     * @param validatorName {@link ValidatorName} is a enum with a name of
     *                       the desired validator
     * @return specified validator which matches incoming validator name
     */
    public Validator getValidator(ValidatorName validatorName){
        return validators.get(validatorName);
    }
}
