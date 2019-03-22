package com.epam.hotel.service.validation;

/**
 * This interface defines method for validating various objects.
 *
 * @author Artsem Lashuk
 */
public interface Validator {

    /**
     * This method validates incoming parameter and returns true or false.
     *
     * @param obj incoming parameter to validate
     * @return result of validation
     */
    boolean isValid(Object obj);
}
