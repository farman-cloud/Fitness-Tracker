package com.fitnesstracker.backend.controller;

import com.fitnesstracker.backend.dto.WorkoutRequest;
import com.fitnesstracker.backend.entity.Workout;
import com.fitnesstracker.backend.service.WorkoutService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/workouts")
@RequiredArgsConstructor
public class WorkoutController {

    private final WorkoutService workoutService;

    @PostMapping
    public ResponseEntity<Workout> createWorkout(
            @Valid @RequestBody WorkoutRequest workoutRequest,
            Authentication authentication
    ) {
        String userEmail = authentication.getName();

        Workout createdWorkout = workoutService.createWorkout(workoutRequest, userEmail);

        return ResponseEntity
                .created(URI.create("/api/workouts/" + createdWorkout.getId()))
                .body(createdWorkout);
    }
}