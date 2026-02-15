package nl.sweatdaddy.workoutSession.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;


import java.time.LocalDateTime;

public class CreateWorkoutSessionDto {

    @NotNull(message = "Client ID is required")
    private Long clientId;
    @NotNull(message = "Workout ID is required")
    private Long workoutId;
    @NotNull(message = "Session date is required")
    private LocalDateTime sessionDate;

    private boolean completed;
    @Size(min = 3, max = 255, message = "Notes must be between 3 and 255 characters")
    private String notes;
    @Positive
    @Max(value = 999, message = "Workout exceeds maximum of 999 minutes")
    private Integer durationInMinutes;

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
