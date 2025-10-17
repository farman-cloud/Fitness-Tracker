package com.fitnesstracker.backend.service;

import com.fitnesstracker.backend.dto.RegistrationRequest;
import com.fitnesstracker.backend.entity.User;
import com.fitnesstracker.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(RegistrationRequest registrationRequest) {
        User user = new User();
        user.setUsername(registrationRequest.getUsername());
        user.setEmail(registrationRequest.getEmail());
        // To-do HASH this password before using!
        user.setPassword(registrationRequest.getPassword());
        return userRepository.save(user);
    }
}