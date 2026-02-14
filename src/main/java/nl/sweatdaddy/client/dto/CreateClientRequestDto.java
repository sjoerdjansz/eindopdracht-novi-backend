package nl.sweatdaddy.client.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

public class CreateClientRequestDto {

    @NotBlank(message = "Firstname is required")
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
    private String firstName;

    @NotBlank(message = "Lastname name is required")
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
    private String lastName;

    @NotBlank(message = "Email can't be empty")
    @Email(message = "Email must be valid")
    private String email;

    @NotNull(message = "Invalid birthday")
    private LocalDate birthday;

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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
