package com.fitnesstracker.backend.service;

import com.fitnesstracker.backend.dto.UpdateProfileRequest;
import com.fitnesstracker.backend.dto.UserProfileResponse;
import com.fitnesstracker.backend.entity.User;
import com.fitnesstracker.backend.exception.UsernameConflictException;
import com.fitnesstracker.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserProfileResponse getUserProfile(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + userEmail));

        return mapUserToProfileResponse(user);
    }

    @Transactional
    public UserProfileResponse updateUserProfile(String userEmail, UpdateProfileRequest request) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + userEmail));

        // Update username only if it's provided and different
        if (request.getUsername() != null && !request.getUsername().isBlank() && !request.getUsername().equals(user.getUsername())) {
            // Check if the new username is already taken by someone else
            userRepository.findByUsername(request.getUsername()).ifPresent(existingUser -> {
                // Check if the found user is NOT the current user trying to update their profile
                if (!existingUser.getId().equals(user.getId())) {
                    throw new UsernameConflictException("Username '" + request.getUsername() + "' is already taken.");
                }
            });

            user.setUsername(request.getUsername());
        }

        if (request.getAge() != null) {
            user.setAge(request.getAge());
        }
        if (request.getWeight() != null) {
            user.setWeight(request.getWeight());
        }
        if (request.getHeight() != null) {
            user.setHeight(request.getHeight());
        }

        User updatedUser = userRepository.save(user);
        return mapUserToProfileResponse(updatedUser);
    }

    private UserProfileResponse mapUserToProfileResponse(User user) {
        UserProfileResponse response = new UserProfileResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setAge(user.getAge());
        response.setWeight(user.getWeight());
        response.setHeight(user.getHeight());
        response.setBmi(calculateBmi(user.getWeight(), user.getHeight()));
        return response;
    }

    private Double calculateBmi(Double weightKg, Double heightCm) {
        if (weightKg == null || heightCm == null || weightKg <= 0 || heightCm <= 0) {
            return null;
        }
        try {
            BigDecimal heightM = BigDecimal.valueOf(heightCm).divide(BigDecimal.valueOf(100));
            BigDecimal bmi = BigDecimal.valueOf(weightKg).divide(
                    heightM.multiply(heightM),
                    2,
                    RoundingMode.HALF_UP
            );
            return bmi.doubleValue();
        } catch (ArithmeticException e) {
            return null;
        }
    }
}