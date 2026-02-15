package nl.sweatdaddy.workoutSession.service;

import jakarta.transaction.Transactional;
import nl.sweatdaddy.client.entity.Client;
import nl.sweatdaddy.client.repository.ClientRepository;
import nl.sweatdaddy.common.exception.ConflictException;
import nl.sweatdaddy.common.exception.NotFoundException;
import nl.sweatdaddy.workout.entity.Workout;
import nl.sweatdaddy.workout.repository.WorkoutRepository;
import nl.sweatdaddy.workoutSession.controller.WorkoutSessionController;
import nl.sweatdaddy.workoutSession.dto.CreateWorkoutSessionDto;
import nl.sweatdaddy.workoutSession.dto.WorkoutSessionResponseDto;
import nl.sweatdaddy.workoutSession.entity.WorkoutSession;
import nl.sweatdaddy.workoutSession.repository.WorkoutSessionRepository;
import org.apache.coyote.Response;
import org.hibernate.jdbc.Work;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkoutSessionService {

    private final WorkoutRepository workoutRepository;
    private final ClientRepository clientRepository;
    private final WorkoutSessionRepository workoutSessionRepository;

    public WorkoutSessionService(WorkoutRepository workoutRepository, ClientRepository clientRepository,
                                 WorkoutSessionRepository workoutSessionRepository) {
        this.workoutRepository = workoutRepository;
        this.clientRepository = clientRepository;
        this.workoutSessionRepository = workoutSessionRepository;
    }

    // Alle workoutSessions ophalen van een client
    public List<WorkoutSessionResponseDto> getAllWorkoutSessionsByClient(Long id) {
        Client client = clientRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Client not found"));

        List<WorkoutSessionResponseDto> workoutSessions = workoutSessionRepository.findByClient(
                client).stream().map(this::toDto).toList();

        return workoutSessions;
    }

    // Alle workoutSessions ophalen via workout
    public List<WorkoutSessionResponseDto> getAllWorkoutSessionsByWorkout(Long id) {
        Workout workout = workoutRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Workout not found"));

        List<WorkoutSessionResponseDto> workoutSession = workoutSessionRepository.findByWorkout(
                workout).stream().map(this::toDto).toList();

        return workoutSession;
    }

    // Alle voltooide of onvoltooide workoutSessions ophalen
    public List<WorkoutSessionResponseDto> getCompletedWorkoutSession(boolean completed) {
        return workoutSessionRepository.findByCompleted(completed).stream().map(this::toDto).toList();
    }

    // Workouts ophalen op basis van een min max duration range
    public List<WorkoutSessionResponseDto> getWorkoutSessionsDurationBetween(Integer min, Integer max) {
        return workoutSessionRepository.findByDurationInMinutesBetween(min, max).stream().map(
                this::toDto).toList();
    }


    // WorkoutSession aanmaken en checken of er niet al een op dezelfde datum staat
    @Transactional
    public WorkoutSessionResponseDto createNewWorkoutSession(CreateWorkoutSessionDto request) {
        Client client = clientRepository.findById(request.getClientId()).orElseThrow(
                () -> new NotFoundException("Client not found"));

        Workout workout = workoutRepository.findById(request.getWorkoutId()).orElseThrow(
                () -> new NotFoundException("Workout not found"));

        boolean alreadyExists = workoutSessionRepository.existsByClient_IdAndSessionDate(
                request.getClientId(),
                request.getSessionDate());

        if (alreadyExists) {
            throw new ConflictException(
                    "There's already a workout planned on this date for client " + client.getFirstName() +
                    " " + client.getLastName());
        }

        WorkoutSession session = new WorkoutSession(
                null,
                client,
                workout,
                request.getSessionDate(),
                request.isCompleted(),
                request.getNotes(),
                request.getDurationInMinutes()
        );

        WorkoutSession saved = workoutSessionRepository.save(session);

        return toDto(saved);
    }

    // Delete workout session
    @Transactional
    public void deleteWorkoutSession(Long id) {
        WorkoutSession session = workoutSessionRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Workout with id " + id + " not found"));

        workoutSessionRepository.deleteWorkoutSessionById(session.getId());
    }

    // dto mapper
    private WorkoutSessionResponseDto toDto(WorkoutSession session) {
        return new WorkoutSessionResponseDto(
                session.getId(),
                session.getClient().getId(),
                session.getWorkout().getId(),
                session.getSessionDate(),
                session.isCompleted(),
                session.getNotes(),
                session.getDurationInMinutes()
        );
    }
}
