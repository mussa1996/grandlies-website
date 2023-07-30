package com.grandlies.reviewservices.services;

import com.grandlies.reviewservices.entity.User;
import com.grandlies.reviewservices.exceptions.InvalidUserException;
import com.grandlies.reviewservices.exceptions.UserNotFoundException;
import com.grandlies.reviewservices.models.*;
import com.grandlies.reviewservices.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Key;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    private  UserRepository userRepository;
    private  PasswordEncoder passwordEncoder;
    private  Key jwtSecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private  long jwtExpirationMs = 86400000; // 24 hours

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

public UserResponse signupUser(SignupRequest signupRequest) {
    if (!signupRequest.getPassword().equals(signupRequest.getConfirmPassword())) {
        throw new InvalidUserException("Passwords do not match.");
    }

    if (userRepository.findByEmail(signupRequest.getEmail()).isPresent()) {
        throw new InvalidUserException("Email already exists.");
    }

    User user = new User();
    user.setName(signupRequest.getName());
    user.setEmail(signupRequest.getEmail());
    user.setPicture(signupRequest.getPicture());
    user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
    user.setRole(signupRequest.getRole());
   User resultUser= userRepository.save(user);

    String successMessage = "User registered successfully";
    return new UserResponse(successMessage, resultUser);
}


    public AuthResponse loginUser(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found."));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidUserException("Invalid email or password.");
        }

        String jwtToken = generateJwtToken(user.getEmail(), user.getRole());
        return new AuthResponse(jwtToken, user.getRole());
    }
    @PreAuthorize("hasRole('Admin') or hasRole('User')")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
@PreAuthorize("hasRole('Admin') or hasRole('User')")
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found."));
        String successMessage = "User retrieved successfully";
        return new UserResponse(successMessage, user);
    }
    @PreAuthorize("hasRole('Admin') or hasRole('User')")
public UserResponse deleteUserById(Long id) {
    User userToDelete = userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException("User not found."));
    userRepository.deleteById(id);
    String successMessage = "User deleted successfully";
    return new UserResponse(successMessage, userToDelete);
}
@PreAuthorize("hasRole('Admin') or hasRole('User')")
    public UserResponse updateUser(Long id, User updatedUser) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found."));
        existingUser.setName(updatedUser.getName());
        existingUser.setPicture(updatedUser.getPicture());
        String newPassword = updatedUser.getPassword();
        if (newPassword != null && !newPassword.isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(newPassword));
        }
        User savedUser = userRepository.save(existingUser);
        String successMessage = "User updated successfully";
        return new UserResponse(successMessage, savedUser);
    }
    private String generateJwtToken(String email, String role) {
        Date expirationDate = new Date(System.currentTimeMillis() + jwtExpirationMs);
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setExpiration(expirationDate)
                .signWith(jwtSecretKey)
                .compact();
    }
}
