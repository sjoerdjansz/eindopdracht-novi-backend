package nl.sweatdaddy.client.service;

import nl.sweatdaddy.client.dto.ClientResponseDto;
import nl.sweatdaddy.client.entity.Client;
import nl.sweatdaddy.client.repository.ClientRepository;
import nl.sweatdaddy.exercise.dto.ExerciseResponseDto;
import nl.sweatdaddy.exercise.entity.Exercise;
import nl.sweatdaddy.exercise.repository.ExerciseRepository;
import nl.sweatdaddy.workout.dto.WorkoutResponseDto;
import nl.sweatdaddy.workout.entity.Workout;
import nl.sweatdaddy.workout.repository.WorkoutRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final WorkoutRepository workoutRepository;
    private final ExerciseRepository exerciseRepository;


    public ClientService(ClientRepository clientRepository, WorkoutRepository workoutRepository,
                         ExerciseRepository exerciseRepository) {
        this.clientRepository = clientRepository;
        this.workoutRepository = workoutRepository;
        this.exerciseRepository = exerciseRepository;
    }

    // alle clienten ophalen
    public List<ClientResponseDto> getAllClients() {
        return clientRepository.findAll().stream().map(this::toDto).toList();
    }

    private ClientResponseDto toDto(Client client) {

        List<WorkoutResponseDto> workoutDtos = client.getWorkoutList().stream().map(
                workout -> {
                    List<ExerciseResponseDto> exerciseDtos = workout.getExerciseList().stream().map(
                            exercise -> new ExerciseResponseDto(exercise.getName(), exercise.getMuscles(),
                                                                exercise.getMovement())).toList();

                    return new WorkoutResponseDto(workout.getId(), workout.getName(), workout.getCreatedAt(),
                                                  workout.getCreatedBy(), exerciseDtos, workout.getNotes());
                }
        ).toList();
        return new ClientResponseDto(client.getId(), client.getFirstName(), client.getLastName(),
                                     client.getEmail(), client.getBirthday(), workoutDtos);
    }

}
