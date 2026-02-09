package nl.sweatdaddy.workout.controller;

import java.util.List;
import nl.sweatdaddy.common.ApiResponse;
import nl.sweatdaddy.workout.dto.WorkoutResponseDto;
import nl.sweatdaddy.workout.service.WorkoutService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/workouts")
public class WorkoutController {

  private final WorkoutService workoutService;

  public WorkoutController(WorkoutService workoutService) {
    this.workoutService = workoutService;
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<WorkoutResponseDto>>> getWorkouts() {
    List<WorkoutResponseDto> workouts = workoutService.getAllWorkouts();
    return ResponseEntity.ok(new ApiResponse<>(workouts, "Workouts retrieved from database"));
  }

}
