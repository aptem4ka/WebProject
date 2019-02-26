package com.epam.hotel.service.validation;

import com.epam.hotel.service.validation.impl.EmailValidator;
import com.epam.hotel.service.validation.impl.NameValidator;
import com.epam.hotel.service.validation.impl.PasswordValidator;
import com.epam.hotel.service.validation.impl.PhoneValidator;

import java.util.HashMap;
import java.util.Map;

public class ValidatorManager {
    public final static ValidatorManager instance=new ValidatorManager();
    private final Map<ValidatorName, Validator> validators=new HashMap<>();

    private ValidatorManager(){
        validators.put(ValidatorName.EMAIL, new EmailValidator());
        validators.put(ValidatorName.PASSWORD, new PasswordValidator());
        validators.put(ValidatorName.NAME, new NameValidator());
        validators.put(ValidatorName.PHONE, new PhoneValidator());

    }

    public static ValidatorManager getInstance() {
        return instance;
    }

    public Validator getValidator(ValidatorName validatorName){
        return validators.get(validatorName);
    }
}
