package com.fitnesstracker.backend.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.List;

@Data
public class WorkoutRequest {

    @NotBlank(message = "Workout name is required.")
    private String name;

    private LocalDate date;

    @NotEmpty(message = "A workout must have at least one exercise.")
    private List<ExerciseRequest> exercises;
}