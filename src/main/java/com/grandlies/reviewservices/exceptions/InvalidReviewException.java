package com.grandlies.reviewservices.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class InvalidReviewException extends RuntimeException{
    public InvalidReviewException(String message) {
        super(message);
    }
}
