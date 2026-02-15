package nl.sweatdaddy;

import nl.sweatdaddy.client.entity.Client;
import nl.sweatdaddy.client.repository.ClientRepository;
import nl.sweatdaddy.common.exception.ConflictException;
import nl.sweatdaddy.common.exception.NotFoundException;
import nl.sweatdaddy.workout.entity.Workout;
import nl.sweatdaddy.workout.repository.WorkoutRepository;
import nl.sweatdaddy.workoutSession.dto.CreateWorkoutSessionDto;
import nl.sweatdaddy.workoutSession.entity.WorkoutSession;
import nl.sweatdaddy.workoutSession.repository.WorkoutSessionRepository;
import nl.sweatdaddy.workoutSession.service.WorkoutSessionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WorkoutSessionServiceTest {

    @Mock
    WorkoutRepository workoutRepository;

    @Mock
    ClientRepository clientRepository;

    @Mock
    WorkoutSessionRepository workoutSessionRepository;

    @InjectMocks
    WorkoutSessionService workoutSessionService;

    @Test
    void getAllWorkoutSessionsByClient_happyFlow() {
        // arrange
        Long clientId = 1L;

        Client client = mock(Client.class);
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

        WorkoutSession session = simpleSession(10L, 1L, 2L, LocalDateTime.of(2026, 2, 1, 10, 0), true);
        when(workoutSessionRepository.findByClient(client)).thenReturn(List.of(session));

        // act
        var dtoList = workoutSessionService.getAllWorkoutSessionsByClient(clientId);

        // assert
        assertEquals(1, dtoList.size());
        assertEquals(10L, dtoList.get(0).getId());

        verify(clientRepository).findById(clientId);
        verify(workoutSessionRepository).findByClient(client);
        verifyNoMoreInteractions(clientRepository, workoutSessionRepository, workoutRepository);
    }

    @Test
    void getAllWorkoutSessionsByClient_notFound() {
        // arrange
        Long clientId = 1L;
        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        // act + assert
        assertThrows(NotFoundException.class,
                     () -> workoutSessionService.getAllWorkoutSessionsByClient(clientId));

        verify(clientRepository).findById(clientId);
        verifyNoMoreInteractions(clientRepository, workoutSessionRepository, workoutRepository);
    }

    @Test
    void getAllWorkoutSessionsByWorkout_happyFlow() {
        // arrange
        Long workoutId = 2L;

        Workout workout = mock(Workout.class);
        when(workoutRepository.findById(workoutId)).thenReturn(Optional.of(workout));

        WorkoutSession session = simpleSession(11L, 1L, 2L, LocalDateTime.of(2026, 2, 2, 10, 0), false);
        when(workoutSessionRepository.findByWorkout(workout)).thenReturn(List.of(session));

        // act
        var dtoList = workoutSessionService.getAllWorkoutSessionsByWorkout(workoutId);

        // assert
        assertEquals(1, dtoList.size());
        assertEquals(11L, dtoList.get(0).getId());

        verify(workoutRepository).findById(workoutId);
        verify(workoutSessionRepository).findByWorkout(workout);
        verifyNoMoreInteractions(clientRepository, workoutSessionRepository, workoutRepository);
    }

    @Test
    void getAllWorkoutSessionsByWorkout_notFound() {
        // arrange
        Long workoutId = 2L;
        when(workoutRepository.findById(workoutId)).thenReturn(Optional.empty());

        // act + assert
        assertThrows(NotFoundException.class,
                     () -> workoutSessionService.getAllWorkoutSessionsByWorkout(workoutId));

        verify(workoutRepository).findById(workoutId);
        verifyNoMoreInteractions(clientRepository, workoutSessionRepository, workoutRepository);
    }

    @Test
    void getCompletedWorkoutSession_happyFlow() {
        // arrange
        boolean completed = true;

        WorkoutSession session = simpleSession(12L, 1L, 2L, LocalDateTime.of(2026, 2, 3, 10, 0), true);
        when(workoutSessionRepository.findByCompleted(completed)).thenReturn(List.of(session));

        // act
        var dtoList = workoutSessionService.getCompletedWorkoutSession(completed);

        // assert
        assertEquals(1, dtoList.size());

        verify(workoutSessionRepository).findByCompleted(completed);
        verifyNoMoreInteractions(clientRepository, workoutSessionRepository, workoutRepository);
    }

    @Test
    void getWorkoutSessionsDurationBetween_happyFlow() {
        // arrange
        int min = 10;
        int max = 60;

        WorkoutSession session = simpleSession(13L, 1L, 2L, LocalDateTime.of(2026, 2, 4, 10, 0), false);
        when(workoutSessionRepository.findByDurationInMinutesBetween(min, max)).thenReturn(List.of(session));

        // act
        var dtoList = workoutSessionService.getWorkoutSessionsDurationBetween(min, max);

        // assert
        assertEquals(1, dtoList.size());

        verify(workoutSessionRepository).findByDurationInMinutesBetween(min, max);
        verifyNoMoreInteractions(clientRepository, workoutSessionRepository, workoutRepository);
    }

    @Test
    void createNewWorkoutSession_happyFlow() {
        // arrange
        Long clientId = 1L;
        Long workoutId = 2L;
        LocalDateTime dateTime = LocalDateTime.of(2026, 2, 10, 9, 0);

        Client client = mock(Client.class);
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

        Workout workout = mock(Workout.class);
        when(workoutRepository.findById(workoutId)).thenReturn(Optional.of(workout));

        CreateWorkoutSessionDto request = mock(CreateWorkoutSessionDto.class);
        when(request.getClientId()).thenReturn(clientId);
        when(request.getWorkoutId()).thenReturn(workoutId);
        when(request.getSessionDate()).thenReturn(dateTime);
        when(request.isCompleted()).thenReturn(false);
        when(request.getNotes()).thenReturn("notes");
        when(request.getDurationInMinutes()).thenReturn(30);

        when(workoutSessionRepository.existsByClient_IdAndSessionDate(clientId, dateTime)).thenReturn(false);

        WorkoutSession saved = simpleSession(100L, clientId, workoutId, dateTime, false);
        when(workoutSessionRepository.save(any(WorkoutSession.class))).thenReturn(saved);

        // act
        var dto = workoutSessionService.createNewWorkoutSession(request);

        // assert
        assertEquals(100L, dto.getId());

        verify(clientRepository).findById(clientId);
        verify(workoutRepository).findById(workoutId);
        verify(workoutSessionRepository).existsByClient_IdAndSessionDate(clientId, dateTime);
        verify(workoutSessionRepository).save(any(WorkoutSession.class));
        verifyNoMoreInteractions(clientRepository, workoutSessionRepository, workoutRepository);
    }

    @Test
    void createNewWorkoutSession_clientNotFound() {
        // arrange
        Long clientId = 1L;

        CreateWorkoutSessionDto request = mock(CreateWorkoutSessionDto.class);
        when(request.getClientId()).thenReturn(clientId);

        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        // act + assert
        assertThrows(NotFoundException.class,
                     () -> workoutSessionService.createNewWorkoutSession(request));

        verify(clientRepository).findById(clientId);
        verifyNoMoreInteractions(clientRepository, workoutSessionRepository, workoutRepository);
    }

    @Test
    void createNewWorkoutSession_workoutNotFound() {
        // arrange
        Long clientId = 1L;
        Long workoutId = 2L;

        Client client = mock(Client.class);
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

        CreateWorkoutSessionDto request = mock(CreateWorkoutSessionDto.class);
        when(request.getClientId()).thenReturn(clientId);
        when(request.getWorkoutId()).thenReturn(workoutId);

        when(workoutRepository.findById(workoutId)).thenReturn(Optional.empty());

        // act + assert
        assertThrows(NotFoundException.class,
                     () -> workoutSessionService.createNewWorkoutSession(request));

        verify(clientRepository).findById(clientId);
        verify(workoutRepository).findById(workoutId);
        verifyNoMoreInteractions(clientRepository, workoutSessionRepository, workoutRepository);
    }

    @Test
    void createNewWorkoutSession_conflict() {
        // arrange
        Long clientId = 1L;
        Long workoutId = 2L;
        LocalDateTime dateTime = LocalDateTime.of(2026, 2, 10, 9, 0);

        Client client = mock(Client.class);
        when(client.getFirstName()).thenReturn("John");
        when(client.getLastName()).thenReturn("Doe");
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

        Workout workout = mock(Workout.class);
        when(workoutRepository.findById(workoutId)).thenReturn(Optional.of(workout));

        CreateWorkoutSessionDto request = mock(CreateWorkoutSessionDto.class);
        when(request.getClientId()).thenReturn(clientId);
        when(request.getWorkoutId()).thenReturn(workoutId);
        when(request.getSessionDate()).thenReturn(dateTime);

        when(workoutSessionRepository.existsByClient_IdAndSessionDate(clientId, dateTime)).thenReturn(true);

        // act + assert
        assertThrows(ConflictException.class,
                     () -> workoutSessionService.createNewWorkoutSession(request));

        verify(clientRepository).findById(clientId);
        verify(workoutRepository).findById(workoutId);
        verify(workoutSessionRepository).existsByClient_IdAndSessionDate(clientId, dateTime);
        verifyNoMoreInteractions(clientRepository, workoutSessionRepository, workoutRepository);
    }

    @Test
    void deleteWorkoutSession_happyFlow() {
        // arrange
        Long id = 10L;

        WorkoutSession session = mock(WorkoutSession.class);
        when(session.getId()).thenReturn(id);
        when(workoutSessionRepository.findById(id)).thenReturn(Optional.of(session));

        // act
        workoutSessionService.deleteWorkoutSession(id);

        // assert
        verify(workoutSessionRepository).findById(id);
        verify(workoutSessionRepository).deleteWorkoutSessionById(id);
        verifyNoMoreInteractions(clientRepository, workoutSessionRepository, workoutRepository);
    }

    @Test
    void deleteWorkoutSession_notFound() {
        // arrange
        Long id = 10L;
        when(workoutSessionRepository.findById(id)).thenReturn(Optional.empty());

        // act + assert
        assertThrows(NotFoundException.class,
                     () -> workoutSessionService.deleteWorkoutSession(id));

        verify(workoutSessionRepository).findById(id);
        verifyNoMoreInteractions(clientRepository, workoutSessionRepository, workoutRepository);
    }

    private WorkoutSession simpleSession(Long sessionId,
                                         Long clientId,
                                         Long workoutId,
                                         LocalDateTime dateTime,
                                         boolean completed) {
        WorkoutSession session = mock(WorkoutSession.class);
        Client client = mock(Client.class);
        Workout workout = mock(Workout.class);

        when(session.getId()).thenReturn(sessionId);
        when(session.getClient()).thenReturn(client);
        when(session.getWorkout()).thenReturn(workout);
        when(client.getId()).thenReturn(clientId);
        when(workout.getId()).thenReturn(workoutId);
        when(session.getSessionDate()).thenReturn(dateTime);
        when(session.isCompleted()).thenReturn(completed);

        when(session.getNotes()).thenReturn(null);
        when(session.getDurationInMinutes()).thenReturn(30);

        return session;
    }
}
