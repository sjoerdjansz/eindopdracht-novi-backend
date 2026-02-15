package nl.sweatdaddy.workoutSession.repository;

import nl.sweatdaddy.client.entity.Client;
import nl.sweatdaddy.workout.entity.Workout;
import nl.sweatdaddy.workoutSession.entity.WorkoutSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface WorkoutSessionRepository extends JpaRepository<WorkoutSession, Long> {

    List<WorkoutSession> findByClient(Client client);

    List<WorkoutSession> findByWorkout(Workout workout);

    List<WorkoutSession> findByCompleted(boolean completed);

    List<WorkoutSession> findByDurationInMinutesBetween(Integer min, Integer max);

    boolean existsByClient_IdAndSessionDate(Long clientId, LocalDateTime sessionDate);

    Optional<WorkoutSession> deleteWorkoutSessionById(Long id);

}
