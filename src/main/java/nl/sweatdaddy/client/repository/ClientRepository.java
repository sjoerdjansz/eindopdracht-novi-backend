package nl.sweatdaddy.client.repository;

import nl.sweatdaddy.client.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findByFirstNameIgnoreCase(String firstName);

    Optional<Client> findByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCase(String email);
}
