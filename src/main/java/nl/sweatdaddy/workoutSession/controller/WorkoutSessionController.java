package nl.sweatdaddy.workoutSession.controller;

import jakarta.validation.Valid;
import nl.sweatdaddy.common.ApiResponse;
import nl.sweatdaddy.workout.dto.WorkoutResponseDto;
import nl.sweatdaddy.workoutSession.dto.CreateWorkoutSessionDto;
import nl.sweatdaddy.workoutSession.dto.WorkoutSessionResponseDto;
import nl.sweatdaddy.workoutSession.entity.WorkoutSession;
import nl.sweatdaddy.workoutSession.service.WorkoutSessionService;
import org.apache.coyote.Response;
import org.aspectj.weaver.patterns.AndPointcut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sessions")
public class WorkoutSessionController {

    private final WorkoutSessionService workoutSessionService;

    public WorkoutSessionController(WorkoutSessionService workoutSessionService) {
        this.workoutSessionService = workoutSessionService;
    }

    @GetMapping(params = "clientId")
    public ResponseEntity<ApiResponse<List<WorkoutSessionResponseDto>>> getAllWorkoutSessionsByClientId(
            @RequestParam
            Long clientId) {

        List<WorkoutSessionResponseDto> sessions = workoutSessionService.getAllWorkoutSessionsByClient(
                clientId);

        return ResponseEntity.ok(new ApiResponse<>(sessions, "All sessions collected"));
    }

    @GetMapping(params = "workoutId")
    public ResponseEntity<ApiResponse<List<WorkoutSessionResponseDto>>> getAllWorkoutSessionsByWorkoutId(
            @RequestParam
            Long workoutId) {

        List<WorkoutSessionResponseDto> sessions = workoutSessionService.getAllWorkoutSessionsByWorkout(
                workoutId);

        return ResponseEntity.ok(new ApiResponse<>(sessions, "All sessions collected"));
    }

    @GetMapping(params = "completed")
    public ResponseEntity<ApiResponse<List<WorkoutSessionResponseDto>>> getAllCompletedWorkoutSessions(
            @RequestParam
            boolean completed) {

        List<WorkoutSessionResponseDto> sessions = workoutSessionService.getCompletedWorkoutSession(
                completed);

        return ResponseEntity.ok(new ApiResponse<>(sessions, "Sessions collected"));
    }

    @GetMapping(params = {"min", "max"})
    public ResponseEntity<ApiResponse<List<WorkoutSessionResponseDto>>> getSessionsByDurationRange(
            @RequestParam
            Integer min,
            @RequestParam
            Integer max) {
        if (min > max) {
            throw new IllegalArgumentException("Minimum duration has to be less than maximum duration");
        }

        var sessions = workoutSessionService.getWorkoutSessionsDurationBetween(min, max);

        return ResponseEntity.ok(new ApiResponse<>(sessions,
                                                   "Sessions filtered based on " + min + " to " + max +
                                                   " minutes."));

    }

    @PostMapping
    public ResponseEntity<ApiResponse<CreateWorkoutSessionDto>> createWorkoutSession(
            @RequestBody
            @Valid
            CreateWorkoutSessionDto request
    ) {

        WorkoutSessionResponseDto createdWorkoutSession = workoutSessionService.createNewWorkoutSession(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(request, "Workout session has been created"));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkoutSession(@PathVariable Long id) {
        workoutSessionService.deleteWorkoutSession(id);

        return ResponseEntity.noContent().build();
    }


}
