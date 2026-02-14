package nl.sweatdaddy.client.entity;

import jakarta.persistence.*;
import nl.sweatdaddy.fileUpload.entity.File;
import nl.sweatdaddy.workout.entity.Workout;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clients")
public class Client {

    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    @Column(nullable = false, length = 100)
    private String firstName;
    @Column(nullable = false, length = 100)
    private String lastName;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private LocalDate birthday;
    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToMany
    // join table is nu simpel en feitelijk anoniem. Voor in de toekomst is een eigen join entity eventueel
    // handig zodat metadata ook gebruikt kan worden en ik invloed kan uitoefenen op meer data en informatie.
    @JoinTable(
            name = "client_workouts",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "workout_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"client_id", "workout_id"})
    )

    private List<Workout> workoutList = new ArrayList<>();


//        Optie 1
        @OneToOne
        File clientProfilePicture;

//    private String profilePictureLocation;

    // Optie 2
    protected Client() {
    }

    public Client(String firstName, String lastName, String email, LocalDate birthday,
                  List<Workout> workoutList, File clientProfilePicture) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthday = birthday;
        this.workoutList = workoutList;
        this.clientProfilePicture = clientProfilePicture;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<Workout> getWorkoutList() {
        return workoutList;
    }

    public File getProfilePictureLocation() {
        return clientProfilePicture;
    }

    public void setProfilePicture(File clientProfilePicture) {
        this.clientProfilePicture = clientProfilePicture;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
