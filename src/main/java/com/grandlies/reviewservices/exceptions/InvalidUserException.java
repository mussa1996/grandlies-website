package com.grandlies.reviewservices.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class InvalidUserException extends RuntimeException{
    public InvalidUserException(String message){super(message);}
}
