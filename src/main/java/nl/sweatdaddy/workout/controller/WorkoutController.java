package nl.sweatdaddy.workout.controller;

import jakarta.validation.Valid;

import java.util.List;

import nl.sweatdaddy.common.ApiResponse;
import nl.sweatdaddy.workout.dto.CreateWorkoutRequestDto;
import nl.sweatdaddy.workout.dto.WorkoutResponseDto;
import nl.sweatdaddy.workout.service.WorkoutService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workouts")
public class WorkoutController {

    // TODO: Logger nog verwijderen
    private static final Logger log =
            LoggerFactory.getLogger(WorkoutController.class);


    private final WorkoutService workoutService;

    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<WorkoutResponseDto>>> getWorkouts() {
        List<WorkoutResponseDto> workouts = workoutService.getAllWorkouts();
        return ResponseEntity.ok(new ApiResponse<>(workouts, "Workouts retrieved from database"));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<WorkoutResponseDto>> createWorkout(
            @RequestBody
            @Valid
            CreateWorkoutRequestDto request) {

        WorkoutResponseDto createdWorkout = workoutService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(createdWorkout, "Workout created successfully."));
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<WorkoutResponseDto>> deleteWorkout(
            @PathVariable
            Long id
    ) {
        WorkoutResponseDto deleted = workoutService.delete(id);
        return ResponseEntity.ok(new ApiResponse<>(deleted, "Workout with id " + id + " deleted successfully"));
    }
}
