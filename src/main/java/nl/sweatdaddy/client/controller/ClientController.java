package nl.sweatdaddy.client.controller;

import jakarta.validation.Valid;
import nl.sweatdaddy.client.dto.ClientResponseDto;
import nl.sweatdaddy.client.dto.CreateClientRequestDto;
import nl.sweatdaddy.client.entity.Client;
import nl.sweatdaddy.client.service.ClientService;
import nl.sweatdaddy.common.ApiResponse;
import nl.sweatdaddy.workout.service.WorkoutService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    // Declareer de variabele clientService zodat we hier gebruik van kunnen maken in de constructor. Dit
    // is dependency injection
    // de reden dat deze private is, is omdat andere classes deze niet hoeven en mogen gebruiken. De reden
    // van final omdat deze niet overschreven mag worden.
    private final ClientService clientService;
    private final WorkoutService workoutService;

    // we maken de clientController constructor die als parameter de clientService krijgt. Zodat we de
    // clientService kunnen aanroepen in de controller.
    public ClientController(ClientService clientService, WorkoutService workoutService) {
        this.clientService = clientService;
        this.workoutService = workoutService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ClientResponseDto>>> getClients(
            @RequestParam(name = "firstName", required = false)
            String firstName,
            @RequestParam(name = "email", required = false)
            String email
    ) {

        List<ClientResponseDto> clients;

        if (firstName != null && !firstName.isBlank()) {
            clients = clientService.getByFirstName(firstName.trim());
        } else if (email != null && !email.isBlank()) {
            clients = clientService.getByEmail(email.trim());
        } else {
            clients = clientService.getAllClients();
        }

        return ResponseEntity.ok(new ApiResponse<>(clients, "All clients retrieved from database"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ClientResponseDto>> getClientById(
            @PathVariable("id")
            Long id) {

        ClientResponseDto dto = clientService.getClientById(id);
        return ResponseEntity.ok(new ApiResponse<>(dto, "Client found"));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ClientResponseDto>> createClient(
            @RequestBody
            @Valid
            CreateClientRequestDto request) {

        ClientResponseDto created = clientService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(created, "Client " +
                                                                                         request.getFirstName() +
                                                                                         " " +
                                                                                         request.getLastName() +
                                                                                         " created"));
    }

    // workout koppelen aan client
    @PostMapping("/{id}/workouts/{workoutId}")
    public ResponseEntity<ApiResponse<ClientResponseDto>> addWorkoutToClient(
            @PathVariable
            Long id,
            @PathVariable
            Long workoutId
    ) {

        ClientResponseDto assignedWorkout = clientService.addWorkoutToClient(id, workoutId);
        return ResponseEntity.ok(
                new ApiResponse<>(null, "Workout with id " + workoutId + " added."));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ClientResponseDto>> updateClient(
            @PathVariable
            Long id,
            @RequestBody
            @Valid
            CreateClientRequestDto request
    ) {
        ClientResponseDto updated = clientService.update(id, request);

        return ResponseEntity.ok(new ApiResponse<>(updated, "Client updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<ClientResponseDto>> deleteClient(
            @PathVariable
            Long id
    ) {
        ClientResponseDto deleted = clientService.delete(id);

        return ResponseEntity.ok(
                new ApiResponse<>(null, "Client " + deleted.getFirstName() + " " + deleted.getLastName() +
                                        " deleted"));
    }

}
