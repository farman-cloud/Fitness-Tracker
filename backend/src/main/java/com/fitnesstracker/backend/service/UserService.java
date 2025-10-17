package com.fitnesstracker.backend.service;

import com.fitnesstracker.backend.dto.RegistrationRequest;
import com.fitnesstracker.backend.entity.User;
import com.fitnesstracker.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

}