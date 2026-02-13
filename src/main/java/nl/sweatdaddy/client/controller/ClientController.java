package nl.sweatdaddy.client.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import nl.sweatdaddy.client.dto.ClientResponseDto;
import nl.sweatdaddy.client.dto.CreateClientRequestDto;
import nl.sweatdaddy.client.repository.ClientRepository;
import nl.sweatdaddy.client.service.ClientService;
import nl.sweatdaddy.common.ApiResponse;
import nl.sweatdaddy.fileUpload.service.FileService;
import nl.sweatdaddy.workout.dto.WorkoutResponseDto;
import nl.sweatdaddy.workout.service.WorkoutService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/clients")
public class ClientController {

    // Declareer de variabele clientService zodat we hier gebruik van kunnen maken in de constructor. Dit
    // is dependency injection
    // de reden dat deze private is, is omdat andere classes deze niet hoeven en mogen gebruiken. De reden
    // van final omdat deze niet overschreven mag worden.
    private final ClientService clientService;
    private final WorkoutService workoutService;
    private final FileService fileService;
    private final ClientRepository clientRepository;

    // we maken de clientController constructor die als parameter de clientService krijgt. Zodat we de
    // clientService kunnen aanroepen in de controller.
    public ClientController(ClientService clientService, WorkoutService workoutService,
                            FileService fileService,
                            ClientRepository clientRepository) {
        this.clientService = clientService;
        this.workoutService = workoutService;
        this.fileService = fileService;
        this.clientRepository = clientRepository;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ClientResponseDto>>> getClients(
            @RequestParam(name = "firstName", required = false)
            String firstName,
            @RequestParam(name = "email", required = false)
            String email
    ) {

        List<ClientResponseDto> clients = clientService.getClientsBasedOnFilters(firstName, email);

        return ResponseEntity.ok(new ApiResponse<>(clients, "Clients retrieved rom database"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ClientResponseDto>> getClientById(
            @PathVariable("id")
            Long id) {

        ClientResponseDto dto = clientService.getClientById(id);
        return ResponseEntity.ok(new ApiResponse<>(dto, "Client found"));
    }

    @GetMapping("/{id}/workouts")
    public ResponseEntity<ApiResponse<List<WorkoutResponseDto>>> getClientWorkouts(
            @PathVariable
            Long id) {
        List<WorkoutResponseDto> clientWorkouts = clientService.getAllWorkoutsFromClient(id);

        return ResponseEntity.ok(new ApiResponse<>(clientWorkouts, "All assigned workouts retrieved"));
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

    @PostMapping("/{id}/profile-picture")
    public ResponseEntity<ClientResponseDto> addPictureToClient(
            @PathVariable
            Long id,
            @RequestParam("file")
            MultipartFile file) throws IOException {

        String fileName = fileService.storeFile(file);

        ClientResponseDto clientResponseDto = clientService.addProfilePictureToClient(fileName, id);

        String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/clients/").path(
                Objects.requireNonNull(id.toString())).path("profile-picture").toUriString();

        return ResponseEntity.created(URI.create(url)).body(clientResponseDto);
    }

    // profile picture downloaden
    @GetMapping("/{id}/profile-picture/download")
    public ResponseEntity<Resource> downloadProfilePicture(
            @PathVariable
            Long id, HttpServletRequest request) {
        Resource resource = clientService.getProfilePictureFromClient(id);

        String mimeType;

        try {
            mimeType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(mimeType)).header(
                HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + resource.getFilename()).body(resource);
    }

    // niet mogelijk om via deze endpoint de file of gekoppelde workouts te veranderen
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

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<ClientResponseDto>> getMyProfile(Authentication authentication) {
        String email = null;
        // dit is nodig om de email uit de access token te halen en te gebruiken in de endpoint
        // zodat de gegevens van de client opgehaald worden en alleen die specifieke en 'ingelogde' client
        // dit kan zien
        if (authentication instanceof JwtAuthenticationToken jwtToken) {
            email = jwtToken.getToken().getClaimAsString("email");
        }

        ClientResponseDto dto = clientService.getByEmail(email);
        return ResponseEntity.ok(new ApiResponse<>(dto, "Profiel gevonden"));
    }

}
