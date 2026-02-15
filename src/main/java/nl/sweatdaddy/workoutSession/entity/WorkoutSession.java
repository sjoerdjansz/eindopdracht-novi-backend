package nl.sweatdaddy.workoutSession.entity;

import jakarta.persistence.*;
import nl.sweatdaddy.client.entity.Client;
import nl.sweatdaddy.workout.entity.Workout;

import java.time.LocalDateTime;

// Workout session is in feite een concrete uitvoering van een workout op een moment in de tijd.
// Een laag dieper hebben we nog WorkoutSessionResults of ExerciseResults nodig, gekoppeld aan de
// WorkoutSession
@Entity
@Table(name = "workout_sessions")
public class WorkoutSession {

    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

    // Veel workout_sessions kunnen bij één client horen, maar elke session heeft maar één cliënt
    // Door lazy fetching te doen halen we hier alleen de Client op als we daar expliciet om vragen
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id")
    private Client client;

    // Veel workout_sessions kunnen bij één workout horen, maar elke session heeft maar één workout
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "workout_id")
    private Workout workout;

    @Column(name = "session_date", nullable = false)
    private LocalDateTime sessionDate;

    @Column(nullable = false)
    private boolean completed;

    @Column
    private String notes;

    private Integer durationInMinutes;

    protected WorkoutSession() {

    }

    public WorkoutSession(Long id, Client client, Workout workout, LocalDateTime sessionDate,
                          boolean completed, String notes, Integer durationInMinutes) {
        this.id = id;
        this.client = client;
        this.workout = workout;
        this.sessionDate = sessionDate;
        this.completed = completed;
        this.notes = notes;
        this.durationInMinutes = durationInMinutes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    public LocalDateTime getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(LocalDateTime sessionDate) {
        this.sessionDate = sessionDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(Integer durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }
}
