package com.epam.hotel.service.validation;

import com.epam.hotel.service.validation.impl.*;

import java.util.HashMap;
import java.util.Map;

public class ValidatorManager {
    private final static ValidatorManager instance=new ValidatorManager();
    private final Map<ValidatorName, Validator> validators=new HashMap<>();

    private ValidatorManager(){
        validators.put(ValidatorName.EMAIL, new EmailValidator());
        validators.put(ValidatorName.PASSWORD, new PasswordValidator());
        validators.put(ValidatorName.NAME, new NameValidator());
        validators.put(ValidatorName.PHONE, new PhoneValidator());
        validators.put(ValidatorName.DATE, new DateValidator());


    }

    public static ValidatorManager getInstance() {
        return instance;
    }

    public Validator getValidator(ValidatorName validatorName){
        return validators.get(validatorName);
    }
}
