package com.fitnesstracker.backend.controller;

import com.fitnesstracker.backend.dto.UpdateProfileRequest;
import com.fitnesstracker.backend.dto.UserProfileResponse;
import com.fitnesstracker.backend.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getCurrentUserProfile(Authentication authentication) {
        String userEmail = authentication.getName();
        UserProfileResponse profile = profileService.getUserProfile(userEmail);
        return ResponseEntity.ok(profile);
    }

    @PutMapping("/me")
    public ResponseEntity<UserProfileResponse> updateCurrentUserProfile(
            @Valid @RequestBody UpdateProfileRequest request,
            Authentication authentication) {
        String userEmail = authentication.getName();
        UserProfileResponse updatedProfile = profileService.updateUserProfile(userEmail, request);
        return ResponseEntity.ok(updatedProfile);
    }
}