package com.epam.hotel.service.validation.impl;

import com.epam.hotel.entity.Order;
import com.epam.hotel.exception.ServiceException;
import com.epam.hotel.service.validation.Validator;
import com.epam.hotel.service.validation.ValidatorManager;
import com.epam.hotel.service.validation.ValidatorName;

public class OrderValidator implements Validator {

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
                || order.getResFrom().after(order.getResTo())){
            return false;
        }
        if (order.getTotalPrice()<=0 || order.getRoomID()<=0 || order.getUserID()<0){
            return false;
        }


        return true;
    }
}
