package nl.sweatdaddy.workout.dto;

import java.time.LocalDateTime;
import java.util.List;
import nl.sweatdaddy.exercise.entity.Exercise;

public class WorkoutResponseDto {

  private final String name;
  private final LocalDateTime createdAt;
  private final String createdBy;
  private final List<Exercise> exerciseList;
  private final String notes;

  public WorkoutResponseDto(String name, LocalDateTime createdAt, String createdBy,
      List<Exercise> exerciseList, String notes) {
    this.name = name;
    this.createdAt = createdAt;
    this.createdBy = createdBy;
    this.exerciseList = exerciseList;
    this.notes = notes;
  }

  public String getName() {
    return name;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public List<Exercise> getExerciseList() {
    return exerciseList;
  }


  public String getNotes() {
    return notes;
  }
}
