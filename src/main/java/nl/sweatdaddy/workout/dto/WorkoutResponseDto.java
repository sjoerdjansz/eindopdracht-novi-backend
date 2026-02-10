package nl.sweatdaddy.workout.dto;

import java.time.LocalDateTime;
import java.util.List;

import nl.sweatdaddy.exercise.dto.ExerciseResponseDto;
import nl.sweatdaddy.exercise.entity.Exercise;

public class WorkoutResponseDto {

    private final Long id;
    private final String name;
    private final LocalDateTime createdAt;
    private final String createdBy;
    private final List<ExerciseResponseDto> exerciseList;
    private final String notes;

    public WorkoutResponseDto(Long id, String name, LocalDateTime createdAt, String createdBy,
                              List<ExerciseResponseDto> exerciseList, String notes) {
        this.id = id;
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

    public List<ExerciseResponseDto> getExerciseList() {
        return exerciseList;
    }

    public Long getId() {
        return id;
    }

    public String getNotes() {
        return notes;
    }
}
