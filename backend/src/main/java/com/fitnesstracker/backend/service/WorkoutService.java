package com.fitnesstracker.backend.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import com.fitnesstracker.backend.dto.ExerciseRequest;
import com.fitnesstracker.backend.dto.WorkoutRequest;
import com.fitnesstracker.backend.dto.ExerciseResponse;
import com.fitnesstracker.backend.dto.WorkoutResponse;
import com.fitnesstracker.backend.entity.Exercise;
import com.fitnesstracker.backend.entity.User;
import com.fitnesstracker.backend.entity.Workout;
import com.fitnesstracker.backend.repository.ExerciseRepository;
import com.fitnesstracker.backend.repository.UserRepository;
import com.fitnesstracker.backend.repository.WorkoutRepository;
import com.fitnesstracker.backend.exception.ResourceNotFoundException;
import com.fitnesstracker.backend.exception.UnauthorizedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final ExerciseRepository exerciseRepository;
    private final UserRepository userRepository;

    @Transactional
    public Workout createWorkout(WorkoutRequest workoutRequest, String userEmail) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + userEmail));

        Workout workout = new Workout();
        workout.setName(workoutRequest.getName());
        workout.setDate(workoutRequest.getDate() != null ? workoutRequest.getDate() : java.time.LocalDate.now());
        workout.setUser(user);

        Workout savedWorkout = workoutRepository.save(workout);

        for (ExerciseRequest exerciseRequest : workoutRequest.getExercises()) {
            Exercise exercise = new Exercise();
            exercise.setName(exerciseRequest.getName());
            exercise.setSets(exerciseRequest.getSets());
            exercise.setReps(exerciseRequest.getReps());
            exercise.setWeight(exerciseRequest.getWeight());
            exercise.setWorkout(savedWorkout);

            exerciseRepository.save(exercise);
        }

        return savedWorkout;
    }

    @Transactional(readOnly = true)
    public WorkoutResponse getWorkoutById(Long workoutId, String userEmail) {
        Workout workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new ResourceNotFoundException("Workout not found with id: " + workoutId));

        if (!workout.getUser().getEmail().equals(userEmail)) {
            throw new UnauthorizedException("User does not have permission to access this workout.");
        }

        return mapWorkoutToResponse(workout);
    }

    private WorkoutResponse mapWorkoutToResponse(Workout workout) {
        WorkoutResponse response = new WorkoutResponse();
        response.setId(workout.getId());
        response.setName(workout.getName());
        response.setDate(workout.getDate());

        List<ExerciseResponse> exerciseResponses = workout.getExercises().stream()
                .map(this::mapExerciseToResponse)
                .collect(Collectors.toList());

        response.setExercises(exerciseResponses);
        return response;
    }

    private ExerciseResponse mapExerciseToResponse(Exercise exercise) {
        ExerciseResponse response = new ExerciseResponse();
        response.setId(exercise.getId());
        response.setName(exercise.getName());
        response.setSets(exercise.getSets());
        response.setReps(exercise.getReps());
        response.setWeight(exercise.getWeight());
        return response;
    }

    @Transactional
    public void deleteWorkout(Long workoutId, String userEmail) {
        Workout workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new ResourceNotFoundException("Workout not found with id: " + workoutId));

        if (!workout.getUser().getEmail().equals(userEmail)) {
            throw new UnauthorizedException("User does not have permission to delete this workout.");
        }

        workoutRepository.delete(workout);
    }
}