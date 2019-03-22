package com.epam.hotel.service.validation.impl;

import com.epam.hotel.entity.Review;
import com.epam.hotel.service.validation.Validator;

/**
 * This {@link Validator} implementation validates reviews.
 *
 * @author Artsem Lashuk
 * @see Review
 */
public class ReviewValidator implements Validator {

    /**
     * This method cheks if the review instance fields have correct values.
     *
     * @param review incoming parameter to validate
     * @return true is all fields of {@link Review} instance are correct
     */
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
