package nl.sweatdaddy.client.controller;

import jakarta.validation.Valid;
import nl.sweatdaddy.client.dto.ClientResponseDto;
import nl.sweatdaddy.client.dto.CreateClientRequestDto;
import nl.sweatdaddy.client.service.ClientService;
import nl.sweatdaddy.common.ApiResponse;
import nl.sweatdaddy.fileUpload.service.FileUploadService;
import nl.sweatdaddy.workout.service.WorkoutService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    private final FileUploadService fileUploadService;

    // we maken de clientController constructor die als parameter de clientService krijgt. Zodat we de
    // clientService kunnen aanroepen in de controller.
    public ClientController(ClientService clientService, WorkoutService workoutService,
                            FileUploadService fileUploadService
    ) {
        this.clientService = clientService;
        this.workoutService = workoutService;
        this.fileUploadService = fileUploadService;
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

        ClientResponseDto updatedClientWorkouts = clientService.addWorkoutToClient(id, workoutId);

        return ResponseEntity.ok(
                new ApiResponse<>(updatedClientWorkouts, "Workout with id " + workoutId + " added."));
    }

    // TODO: endpoint afmaken voor file upload
//    @PostMapping("/{id}/profile-picture")
//    public ResponseEntity<ApiResponse<String>> fileUpload(
//            @PathVariable
//            Long id,
//            @RequestParam("file")
//            MultipartFile file) {
//        String fileName = fileUploadService.storeFile(file); // methode maken in service
//        return ResponseEntity.ok(new ApiResponse<>(fileName, "Profile image uploaded succesfully"));
//    }


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

        return ResponseEntity.noContent().build();
    }

    // workout van client verwijderen
    @DeleteMapping("/{id}/workouts/{workoutId}")
    ResponseEntity<ApiResponse<ClientResponseDto>> deleteWorkoutFromClient(
            @PathVariable
            Long id,
            @PathVariable
            Long workoutId) {
        ClientResponseDto deletedWorkout = clientService.deleteWorkoutFromClient(id, workoutId);

        return ResponseEntity.ok(new ApiResponse<>(deletedWorkout, "Workout successfully deleted"));
    }


}
