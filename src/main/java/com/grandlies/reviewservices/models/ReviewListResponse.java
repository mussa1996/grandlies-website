package com.grandlies.reviewservices.models;

import com.grandlies.reviewservices.entity.Review;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class ReviewListResponse {
    private List<Review> reviews;
    private String message;

    public ReviewListResponse(List<Review> reviews, String message) {
        this.reviews = reviews;
        this.message = message;
    }
}
