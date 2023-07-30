package com.grandlies.reviewservices.controllers;


import com.grandlies.reviewservices.entity.User;
import com.grandlies.reviewservices.models.AuthResponse;
import com.grandlies.reviewservices.models.LoginRequest;
import com.grandlies.reviewservices.models.SignupRequest;
import com.grandlies.reviewservices.models.UserResponse;
import com.grandlies.reviewservices.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signup(@RequestBody @Validated SignupRequest signupRequest) {
    UserResponse userResponse = userService.signupUser(signupRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
}


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Validated LoginRequest loginRequest) {
        AuthResponse authResponse = userService.loginUser(loginRequest);
        return ResponseEntity.ok(authResponse);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id).getUser();
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        User updatedUserData = userService.updateUser(id, updatedUser).getUser();
        return ResponseEntity.ok(updatedUserData);
    }
@DeleteMapping("/{id}")
public ResponseEntity<UserResponse> deleteUser(@PathVariable Long id) {
    UserResponse response = userService.deleteUserById(id);
    return ResponseEntity.ok(response);
}



}

