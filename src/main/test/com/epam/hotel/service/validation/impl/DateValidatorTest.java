package com.epam.hotel.service.validation.impl;

import com.epam.hotel.service.validation.Validator;
import com.epam.hotel.service.validation.ValidatorManager;
import com.epam.hotel.service.validation.ValidatorName;

import org.junit.Test;


import java.util.Date;

import static org.junit.Assert.*;


public class DateValidatorTest {
   private Validator validator = ValidatorManager.getInstance().getValidator(ValidatorName.DATE);


    @Test
    public void testForNull(){
        Date date = null;

        assertFalse(validator.isValid(date));
    }

    @Test
    public void testCurrentDate(){
        Date date = new Date();

        assertTrue(validator.isValid(date));
    }

    @Test
    public void testBeforeDate(){
        Date date = new Date();

        assertFalse(validator.isValid(date.getTime()-100));
    }
}
