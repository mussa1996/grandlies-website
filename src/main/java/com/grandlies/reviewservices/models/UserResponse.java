package com.grandlies.reviewservices.models;

import com.grandlies.reviewservices.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private String message;
    private User user;

    public UserResponse(String message, User user) {
        this.message = message;
        this.user = user;
    }
}
