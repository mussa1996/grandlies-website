package com.grandlies.reviewservices.models;

import com.grandlies.reviewservices.entity.Review;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewResponse {
    private String message;
    private Review review;

    public ReviewResponse(String message, Review review) {
        this.message = message;
        this.review = review;
    }

}


