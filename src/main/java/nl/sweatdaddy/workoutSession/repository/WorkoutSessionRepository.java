package nl.sweatdaddy.workoutSession.repository;

import nl.sweatdaddy.client.entity.Client;
import nl.sweatdaddy.workout.entity.Workout;
import nl.sweatdaddy.workoutSession.entity.WorkoutSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutSessionRepository extends JpaRepository<WorkoutSession, Long> {

    List<WorkoutSession> findByClient(Client client);

    List<WorkoutSession> findByWorkout(Workout workout);

    List<WorkoutSession> findByClientOrderBySessionDateDesc(Client client);

    List<WorkoutSession> findByClientAndCompleted(Client client, boolean completed);

}
