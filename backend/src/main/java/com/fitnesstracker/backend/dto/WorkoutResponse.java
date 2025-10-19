package com.fitnesstracker.backend.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class WorkoutResponse {
    private Long id;
    private String name;
    private LocalDate date;
    private List<ExerciseResponse> exercises;
}