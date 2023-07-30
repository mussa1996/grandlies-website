package com.grandlies.reviewservices.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class UnmodifiableReviewException extends RuntimeException{
    public UnmodifiableReviewException(String message) {
        super(message);
    }
}
