package nl.sweatdaddy.client.service;

import jakarta.transaction.Transactional;
import nl.sweatdaddy.client.dto.ClientResponseDto;
import nl.sweatdaddy.client.dto.CreateClientRequestDto;
import nl.sweatdaddy.client.entity.Client;
import nl.sweatdaddy.client.repository.ClientRepository;
import nl.sweatdaddy.common.exception.ConflictException;
import nl.sweatdaddy.common.exception.NotFoundException;
import nl.sweatdaddy.exercise.dto.ExerciseResponseDto;
import nl.sweatdaddy.exercise.repository.ExerciseRepository;
import nl.sweatdaddy.fileUpload.entity.File;
import nl.sweatdaddy.fileUpload.repository.FileUploadRepository;
import nl.sweatdaddy.workout.dto.WorkoutResponseDto;
import nl.sweatdaddy.workout.entity.Workout;
import nl.sweatdaddy.workout.repository.WorkoutRepository;
import org.aspectj.weaver.ast.Not;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final WorkoutRepository workoutRepository;
    private final ExerciseRepository exerciseRepository;
    private final FileUploadRepository fileUploadRepository;


    public ClientService(ClientRepository clientRepository, WorkoutRepository workoutRepository,
                         ExerciseRepository exerciseRepository, FileUploadRepository fileUploadRepository) {
        this.clientRepository = clientRepository;
        this.workoutRepository = workoutRepository;
        this.exerciseRepository = exerciseRepository;
        this.fileUploadRepository = fileUploadRepository;
    }

    // alle clienten ophalen
    public List<ClientResponseDto> getAllClients() {
        return clientRepository.findAll().stream().map(this::toDto).toList();
    }

    // client(en) zoeken op basis van (first)name
    public List<ClientResponseDto> getByFirstName(String firstName) {

        return clientRepository.findAllByFirstNameContainingIgnoreCase(
                firstName).stream().map(this::toDto).toList();
    }

    // client zoeken op email
    public List<ClientResponseDto> getByEmail(String email) {

        return clientRepository.findByEmailIgnoreCase(
                email).stream().map(this::toDto).toList();
    }

    // client zoeken op id
    public ClientResponseDto getClientById(Long id) {

        Client client = clientRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Client not found"));

        return toDto(client);
    }

    // client toevoegen
    @Transactional
    public ClientResponseDto create(CreateClientRequestDto request) {
        if (clientRepository.existsByEmailIgnoreCase(request.getEmail().trim())) {
            throw new ConflictException("An account with " + request.getEmail() + " already exists");
        }

        Client entity = new Client(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getBirthday(),
                null,
                null
        );

        Client saved = clientRepository.save(entity);
        return new ClientResponseDto(saved.getId(), saved.getFirstName(), saved.getLastName(),
                                     saved.getEmail(), saved.getBirthday(), null, null);
    }

    // client updaten
    @Transactional
    public ClientResponseDto update(Long id, CreateClientRequestDto request) {
        Client client = clientRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Client with id: " + id + " doesn't exist"));

        String newEmail = request.getEmail().trim();

        if (clientRepository.existsByEmailIgnoreCaseAndIdNot(newEmail, id)) {
            throw new ConflictException("Email already in use");
        }

        client.setFirstName(request.getFirstName());
        client.setLastName(request.getLastName());
        client.setEmail(newEmail);
        client.setBirthday(request.getBirthday());

        return toDto(client);
    }

    // client verwijderen
    @Transactional
    public ClientResponseDto delete(Long id) {
        Client client = clientRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Client not found"));

        clientRepository.delete(client);

        return toDto(client);
    }

    // workout toevoegen aan client
    @Transactional
    public ClientResponseDto addWorkoutToClient(Long id, Long workoutId) {
        Client client = clientRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Client not found"));
        Workout foundWorkout = workoutRepository.findById(workoutId).orElseThrow(
                () -> new NotFoundException("Workout not found"));

        boolean alreadyAssigned = client.getWorkoutList().stream().anyMatch(
                workout -> workout.getId().equals(workoutId));

        if (alreadyAssigned) {
            throw new ConflictException("Workout is already assigned to client");
        }

        client.getWorkoutList().add(foundWorkout);

        return toDto(client);
    }

    // alle workouts van en specifieke cliënt ophalen
    @Transactional
    public List<WorkoutResponseDto> getAllWorkoutsFromClient(Long id) {
        Client client = clientRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Client not found"));

        List<Workout> workoutList = client.getWorkoutList();

        return workoutList.stream().map(workout -> {
            List<ExerciseResponseDto> exerciseDtos = workout.getExerciseList().stream().map(
                    exercise -> new ExerciseResponseDto(exercise.getId(), exercise.getName(), exercise.getMuscles(),
                                                        exercise.getMovement())).toList();

            return new WorkoutResponseDto(workout.getId(), workout.getName(), workout.getCreatedAt(),
                                          workout.getCreatedBy(), exerciseDtos, workout.getNotes());
        }).toList();
    }

    // workout van client verwijderen
    @Transactional
    public ClientResponseDto deleteWorkoutFromClient(Long id, Long workoutId) {
        Client client = clientRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Client not found"));
        Workout foundWorkout = workoutRepository.findById(workoutId).orElseThrow(
                () -> new NotFoundException("Workout not found"));

        boolean removedWorkout = client.getWorkoutList().remove(foundWorkout);

        if (!removedWorkout) {
            throw new NotFoundException("No such workout to be found");
        }

        return toDto(client);
    }

    @Transactional
    public ClientResponseDto addProfilePictureToClient(String fileName, Long clientId) {
        Client clientFound = clientRepository.findById(clientId).orElseThrow(
                () -> new NotFoundException("Client not found"));
        nl.sweatdaddy.fileUpload.entity.File fileFound = fileUploadRepository.findByFileName(
                fileName).orElseThrow(() -> new NotFoundException("File not found"));

        clientFound.setProfilePicture(fileFound);
        Client savedClient = clientRepository.save(clientFound);

        return toDto(clientFound);
    }

    // mapper
    private ClientResponseDto toDto(Client client) {

        List<WorkoutResponseDto> workoutDtos = client.getWorkoutList().stream().map(
                workout -> {
                    List<ExerciseResponseDto> exerciseDtos = workout.getExerciseList().stream().map(
                            exercise -> new ExerciseResponseDto(exercise.getId(), exercise.getName(), exercise.getMuscles(),
                                                                exercise.getMovement())).toList();

                    return new WorkoutResponseDto(workout.getId(), workout.getName(), workout.getCreatedAt(),
                                                  workout.getCreatedBy(), exerciseDtos, workout.getNotes());
                }
        ).toList();

        String fileName = null;
        if (client.getProfilePictureLocation() != null) {
            fileName = client.getProfilePictureLocation().getFileName();
        }

        return new ClientResponseDto(client.getId(), client.getFirstName(), client.getLastName(),
                                     client.getEmail(), client.getBirthday(), workoutDtos, fileName);
    }

}
