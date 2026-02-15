package nl.sweatdaddy.workout.controller;

import jakarta.validation.Valid;

import java.util.List;

import nl.sweatdaddy.common.ApiResponse;
import nl.sweatdaddy.workout.dto.CreateWorkoutRequestDto;
import nl.sweatdaddy.workout.dto.WorkoutResponseDto;
import nl.sweatdaddy.workout.service.WorkoutService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workouts")
public class WorkoutController {

    private final WorkoutService workoutService;

    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    // Alle workouts ophalen
    @GetMapping
    public ResponseEntity<ApiResponse<List<WorkoutResponseDto>>> getWorkouts() {
        List<WorkoutResponseDto> workouts = workoutService.getAllWorkouts();
        return ResponseEntity.ok(new ApiResponse<>(workouts, "Workouts retrieved from database"));
    }

    // workout obv id ophalen
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<WorkoutResponseDto>> getWorkoutById(@PathVariable Long id) {
        WorkoutResponseDto workout = workoutService.getWorkoutById(id);

        return ResponseEntity.ok(new ApiResponse<>(workout, "Workout found"));
    }

    // nieuwe workout posten
    @PostMapping
    public ResponseEntity<ApiResponse<WorkoutResponseDto>> createWorkout(
            @RequestBody
            @Valid
            CreateWorkoutRequestDto request) {

        WorkoutResponseDto createdWorkout = workoutService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(createdWorkout, "Workout created successfully."));
    }

    // een workout updaten
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<WorkoutResponseDto>> updateWorkout(
            @PathVariable
            Long id,
            @RequestBody
            @Valid
            CreateWorkoutRequestDto request) {
        WorkoutResponseDto updated = workoutService.update(id, request);
        return ResponseEntity.ok(
                new ApiResponse<>(updated, "Workout with id " + id + " updated successfully!"));

    }

    // workout verwijderen
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<WorkoutResponseDto>> deleteWorkout(
            @PathVariable
            Long id
    ) {
        WorkoutResponseDto deleted = workoutService.delete(id);
        return ResponseEntity.ok(new ApiResponse<>(deleted, "Workout with id " + id + " deleted successfully"));
    }
}
