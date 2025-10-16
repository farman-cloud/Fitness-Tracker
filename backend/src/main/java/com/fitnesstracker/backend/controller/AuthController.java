package com.fitnesstracker.backend.controller;

import com.fitnesstracker.backend.dto.RegistrationRequest;
import com.fitnesstracker.backend.entity.User;
import com.fitnesstracker.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegistrationRequest registrationRequest) {
        User registeredUser = userService.registerUser(registrationRequest);
        return ResponseEntity.ok(registeredUser);
    }
}