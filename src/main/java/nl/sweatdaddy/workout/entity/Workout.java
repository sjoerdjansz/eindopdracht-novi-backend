package nl.sweatdaddy.workout.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import nl.sweatdaddy.client.entity.Client;
import nl.sweatdaddy.exercise.entity.Exercise;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "workouts")
public class Workout {

    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

    @Column(nullable = false, length = 100)
    private String name;
    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @Column(nullable = false, length = 100)
    private String createdBy;

    @ManyToMany(mappedBy = "workoutList")
    private List<Client> clients = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "workout_exercises",
            joinColumns = @JoinColumn(name = "workout_id"),
            inverseJoinColumns = @JoinColumn(name = "exercise_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"workout_id", "exercise_id"})
    )
    private List<Exercise> exerciseList = new ArrayList<>();
    private String notes;

    protected Workout() {
    }

    public Workout(Long id, String name, LocalDateTime createdAt, LocalDateTime updatedAt, String createdBy,
                   List<Exercise> exerciseList, String notes) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.exerciseList = exerciseList;
        this.notes = notes;
    }

    public List<Exercise> getExerciseList() {
        return exerciseList;
    }

    public void setExerciseList(List<Exercise> exerciseList) {
        this.exerciseList = exerciseList;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Client> getClients() {
        return clients;
    }
}
