package com.epam.hotel.service.validation.impl;

import com.epam.hotel.entity.Order;
import com.epam.hotel.exception.ServiceException;
import com.epam.hotel.service.validation.Validator;
import com.epam.hotel.service.validation.ValidatorManager;
import com.epam.hotel.service.validation.ValidatorName;

/**
 * This {@link Validator} implementation validates orders.
 *
 * @author Artsem Lashuk
 * @see Order
 */
public class OrderValidator implements Validator {

    /**
     * This method checks if the order instance fields have correct values.
     *
     * @param obj incoming parameter to validate
     * @return true if all fields has correct values
     */
    @Override
    public boolean isValid(Object obj) {
        if (obj == null){
            return false;
        }
        if (obj.getClass()!=Order.class){
            return false;
        }

        Order order = (Order)obj;

        if (!ValidatorManager.getInstance().getValidator(ValidatorName.DATE).isValid(order.getResFrom())
                || !ValidatorManager.getInstance().getValidator(ValidatorName.DATE).isValid(order.getResTo())
                || order.getResFrom().after(order.getResTo()) || order.getResFrom().equals(order.getResTo()) ){
            return false;
        }

        return !(order.getTotalPrice()<=0 && order.getRoomID()<=0 && order.getUserID()<0);
    }
}
