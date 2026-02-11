package nl.sweatdaddy.client.dto;

import nl.sweatdaddy.workout.dto.WorkoutResponseDto;
import nl.sweatdaddy.workout.entity.Workout;

import java.time.LocalDate;
import java.util.List;

public class ClientResponseDto {
    private final Long id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final LocalDate birthday;
    private final List<WorkoutResponseDto> workoutList;

    public ClientResponseDto(Long id, String firstName, String lastName, String email, LocalDate birthday,
                             List<WorkoutResponseDto> workoutList) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthday = birthday;
        this.workoutList = workoutList;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public List<WorkoutResponseDto> getWorkoutList() {
        return workoutList;
    }
}
