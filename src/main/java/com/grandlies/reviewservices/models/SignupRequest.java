package com.grandlies.reviewservices.models;

import com.grandlies.reviewservices.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {
    private String name;
    private String email;
    private String picture= "default-picture-url";
    private String password;
    private String confirmPassword;
    private String role="User";
}
