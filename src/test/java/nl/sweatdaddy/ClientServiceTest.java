package nl.sweatdaddy;

import nl.sweatdaddy.client.dto.CreateClientRequestDto;
import nl.sweatdaddy.client.entity.Client;
import nl.sweatdaddy.client.repository.ClientRepository;
import nl.sweatdaddy.client.service.ClientService;
import nl.sweatdaddy.common.exception.ConflictException;
import nl.sweatdaddy.common.exception.NotFoundException;
import nl.sweatdaddy.exercise.entity.Exercise;
import nl.sweatdaddy.exercise.repository.ExerciseRepository;
import nl.sweatdaddy.fileUpload.repository.FileUploadRepository;
import nl.sweatdaddy.fileUpload.service.FileService;
import nl.sweatdaddy.workout.repository.WorkoutRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {
    @Mock
    ClientRepository clientRepository;

    @InjectMocks
    ClientService clientService;

    @Test
    @DisplayName("Get Clients")
    void getAllClients() {
        // arrange
        Client client = new Client("Hank", "the Tank", "hankthetank@mail.com",
                                   null,
                                   List.of(), null);

        when(clientRepository.findAll()).thenReturn(List.of(client));

        // act
        var result = clientService.getAllClients();

        // assert
        assertEquals(1, result.size());
        assertEquals("Hank", result.get(0).getFirstName());
        assertEquals("hankthetank@mail.com", result.get(0).getEmail());
        verify(clientRepository).findAll();
        verifyNoMoreInteractions(clientRepository);

    }

    @Test
    @DisplayName("Create client throws ConflictException when email already exists")
    void create_whenEmailExistsAlready() {
        var request = new CreateClientRequestDto();
        request.setEmail("hankthetank@mail.com");
        request.setFirstName("Hank");
        request.setLastName("the Tank");

        when(clientRepository.existsByEmailIgnoreCase("hankthetank@mail.com")).thenReturn(true);

        assertThrows(ConflictException.class, () -> clientService.create(request));

        verify(clientRepository).existsByEmailIgnoreCase("hankthetank@mail.com");
        verifyNoMoreInteractions(clientRepository);
    }

    @Test
    @DisplayName("getById returns client when found")
    void getById() {

        Long id = 1L;

        Client client = new Client("Hank", "the Tank", "hankthetank@mail.com",
                                   null,
                                   List.of(), null);

        client.setId(id);

        when(clientRepository.findById(id)).thenReturn(Optional.of(client)); // moet een Long zijn

        var dto = clientService.getClientById(id); // moet long zijn

        assertEquals(1, dto.getId());
        assertEquals("Hank", dto.getFirstName());

        verify(clientRepository).findById(id);
        verifyNoMoreInteractions(clientRepository);

    }

    @Test
    @DisplayName("Create new client and save called")
    void create_emailDoesntExist() {
        var request = new CreateClientRequestDto();
        request.setEmail("testemail@mail.com");
        request.setFirstName("James");
        request.setLastName("LaBrie");

        when(clientRepository.existsByEmailIgnoreCase("testemail@mail.com")).thenReturn(false);

        Long id = 1L;

        Client saved = new Client("James", "LaBrie", "testemail@mail.com", null, List.of(), null);

        saved.setId(id);

        when(clientRepository.save(any(Client.class))).thenReturn(saved);

        var dto = clientService.create(request);

        assertEquals("James", dto.getFirstName());
        assertEquals("testemail@mail.com", dto.getEmail());
        assertEquals(id, dto.getId());

        verify(clientRepository).existsByEmailIgnoreCase("testemail@mail.com");
        verify(clientRepository).save(any(Client.class));
        verifyNoMoreInteractions(clientRepository);
    }

    @Test
    @DisplayName("getById with NotFoundException")
    void getById_notFound() {

        Long id = 999L;

        when(clientRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> clientService.getClientById(id));

        verify(clientRepository).findById(id);
        verifyNoMoreInteractions(clientRepository);
    }

    @Test
    @DisplayName("Delete client succesfully")
    void delecteClientById_success() {

        Long id = 1L;

        Client client = new Client("John", "Petrucci", "john@mail.com", null, List.of(), null);

        client.setId(id);

        when(clientRepository.findById(id)).thenReturn(Optional.of(client));

        var dto = clientService.delete(id);

        assertEquals(id, dto.getId());
        assertEquals("John", dto.getFirstName());
        assertEquals("Petrucci", dto.getLastName());
        assertEquals("john@mail.com", dto.getEmail());

        verify(clientRepository).findById(id);
        verify(clientRepository).delete(client);
        verifyNoMoreInteractions(clientRepository);

    }

}
