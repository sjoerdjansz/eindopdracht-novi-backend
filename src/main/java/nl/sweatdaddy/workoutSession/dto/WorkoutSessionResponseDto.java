package nl.sweatdaddy.workoutSession.dto;

import nl.sweatdaddy.client.entity.Client;
import nl.sweatdaddy.workout.entity.Workout;

import java.time.LocalDateTime;

public class WorkoutSessionResponseDto {

    private final Long id;
    private final Long clientId;
    private final Long workoutId;
    private final LocalDateTime sessionDate;
    private final boolean completed;
    private final String notes;
    private final Integer durationInMinutes;


    public WorkoutSessionResponseDto(Long id, Long clientId, Long workoutId, LocalDateTime sessionDate,
                                     boolean completed, String notes, Integer durationInMinutes) {

        this.id = id;
        this.clientId = clientId;
        this.workoutId = workoutId;
        this.sessionDate = sessionDate;
        this.completed = completed;
        this.notes = notes;
        this.durationInMinutes = durationInMinutes;

    }

    public Long getId() {
        return id;
    }

    public Long getClientId() {
        return clientId;
    }

    public Long getWorkoutId() {
        return workoutId;
    }

    public LocalDateTime getSessionDate() {
        return sessionDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public String getNotes() {
        return notes;
    }

    public Integer getDurationInMinutes() {
        return durationInMinutes;
    }
}
