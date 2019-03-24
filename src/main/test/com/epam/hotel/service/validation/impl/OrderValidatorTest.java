package com.epam.hotel.service.validation.impl;

import com.epam.hotel.entity.Order;
import com.epam.hotel.service.validation.Validator;
import com.epam.hotel.service.validation.ValidatorManager;
import com.epam.hotel.service.validation.ValidatorName;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import static org.junit.Assert.*;


public class OrderValidatorTest {
    private Validator validator = ValidatorManager.getInstance().getValidator(ValidatorName.ORDER);

    private Order order = new Order();

    @Before
    public void initOrderExceptDates(){
        order.setUserID(5);
        order.setRoomID(5);
        order.setTotalPrice(5);
    }

    @Test
    public void orderWithTodayReservation(){
        order.setResFrom(new Date());
        Date date = new Date();
        order.setResTo(new Date(date.getTime()+100000));

        assertTrue(validator.isValid(order));
    }

    @Test
    public void orderWithZeroDaysReservation(){
        Date date = new Date();
        order.setResFrom(date);
        order.setResTo(date);

        assertFalse(validator.isValid(order));
    }

    @Test
    public void yesterdayReservation(){
        Date date = new Date();
        date = new Date(date.getTime()-100000000);
        order.setResFrom(date);
        order.setResTo(new Date());
        assertFalse(validator.isValid(order));
    }

    /**
     * the case when reservation end date is earlier than reservation begins.
     */
    @Test
    public void reservationReverse(){
        Date date1 = new Date();
        Date date2 = new Date(date1.getTime()+10000);

        order.setResFrom(date2);
        order.setResTo(date1);

        assertFalse(validator.isValid(order));

    }

}
