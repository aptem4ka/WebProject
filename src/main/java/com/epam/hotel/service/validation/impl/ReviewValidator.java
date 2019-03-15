package com.epam.hotel.service.validation.impl;

import com.epam.hotel.entity.Review;
import com.epam.hotel.service.validation.Validator;

public class ReviewValidator implements Validator {

    @Override
    public boolean isValid(Object review) {
        if (review==null){
            return false;
        }
        if (!(Review.class==review.getClass())){
            return false;
        }

        Review rev = (Review)review;

        if (rev.getReviewID()<1){
            return false;
        }

        if (rev.getStatus()== null){
            return false;
        }else if (rev.getStatus()== Review.Status.POSTED){
                return !(rev.getAnswer()==null);

        }

        return true;
    }
}
