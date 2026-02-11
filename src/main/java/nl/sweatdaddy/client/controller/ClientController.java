package nl.sweatdaddy.client.controller;

import nl.sweatdaddy.client.dto.ClientResponseDto;
import nl.sweatdaddy.client.service.ClientService;
import nl.sweatdaddy.common.ApiResponse;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    // Declareer de variabele clientService zodat we hier gebruik van kunnen maken in de constructor. Dit is dependency injection
    // de reden dat deze private is, is omdat andere classes deze niet hoeven en mogen gebruiken. De reden van final omdat deze niet overschreven mag worden.
    private final ClientService clientService;

    // we maken de clientController constructor die als parameter de clientService krijgt. Zodat we de clientService kunnen aanroepen in de controller.
    public ClientController (ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ClientResponseDto>>> getClients() {
        List<ClientResponseDto> clients = clientService.getAllClients();
        return ResponseEntity.ok(new ApiResponse<>(clients, "All clients retrieved from database"));
    }

}
