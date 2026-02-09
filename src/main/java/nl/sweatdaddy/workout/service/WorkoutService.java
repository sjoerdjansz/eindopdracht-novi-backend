package nl.sweatdaddy.workout.service;

import java.util.List;
import nl.sweatdaddy.workout.dto.WorkoutResponseDto;
import nl.sweatdaddy.workout.entity.Workout;
import nl.sweatdaddy.workout.repository.WorkoutRepository;
import org.springframework.stereotype.Service;

@Service
public class WorkoutService {

  private final WorkoutRepository workoutRepository;

  public WorkoutService(WorkoutRepository workoutRepository) {
    this.workoutRepository = workoutRepository;
  }

  public List<WorkoutResponseDto> getAllWorkouts() {
    return workoutRepository.findAll().stream().map(this::toDto).toList();
  }

  private WorkoutResponseDto toDto(Workout w) {
    return new WorkoutResponseDto(w.getName(), w.getCreatedAt(), w.getCreatedBy(),
        w.getExerciseList(), w.getNotes());
  }
}
