package com.fitnesstracker.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.fitnesstracker.backend.dto.WorkoutRequest;
import com.fitnesstracker.backend.dto.WorkoutResponse;
import com.fitnesstracker.backend.entity.Workout;
import com.fitnesstracker.backend.service.WorkoutService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

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

    @GetMapping("/{id}")
    public ResponseEntity<WorkoutResponse> getWorkoutById(
            @PathVariable Long id,
            Authentication authentication
    ) {
        String userEmail = authentication.getName();
        WorkoutResponse workout = workoutService.getWorkoutById(id, userEmail);
        return ResponseEntity.ok(workout);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkoutResponse> updateWorkout(
            @PathVariable Long id,
            @Valid @RequestBody WorkoutRequest workoutRequest,
            Authentication authentication
    ) {
        String userEmail = authentication.getName();
        WorkoutResponse updatedWorkout = workoutService.updateWorkout(id, workoutRequest, userEmail);
        return ResponseEntity.ok(updatedWorkout);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWorkout(
            @PathVariable Long id,
            Authentication authentication
    ) {
        String userEmail = authentication.getName();
        workoutService.deleteWorkout(id, userEmail);
    }
}