package com.pweb.tiendaonline.repositories;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pweb.tiendaonline.AbstractIntegrationDBTest;
import com.pweb.tiendaonline.entities.Cliente;

import static org.assertj.core.api.Assertions.assertThat;

public class ClienteRepositoryTest extends AbstractIntegrationDBTest {

    private ClienteRepository clienteRepository;

    @Autowired
    public ClienteRepositoryTest(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    private Cliente firstClient;
    private Cliente secondClient;
    private Cliente thirdClient;
    void initMockClients(){
        firstClient = Cliente.builder()
                .nombre("Nelson Martinez")
                .email("nelsonmartinezdh@gmail.com")
                .direccion("Calle29i#21-D1")
                .build();
        secondClient = Cliente.builder()
                .nombre("Javier Figueroa")
                .email("javierfigueroat@gmail.com")
                .direccion("Calle29h#15-G4")
                .build();
        thirdClient = Cliente.builder()
                .nombre("Julian Pizarro")
                .email("jpizarro@unimagdalena.edu.co")
                .direccion("Calle2i#34-I0")
                .build();

        clienteRepository.save(firstClient);
        clienteRepository.save(firstClient);
        clienteRepository.save(firstClient);

        clienteRepository.flush();
    }

    @BeforeEach
    void setUp(){
        clienteRepository.deleteAll();
    }

    @SuppressWarnings("null")
    @Test
    public void ClienteRepository_SaveClient_ReturnSavedClient(){
        Cliente cliente = Cliente.builder()
                .nombre("Javier Nuñes")
                .email("javierN@gmail.com")
                .direccion("Calle29h#15-G4")
                .build();

        Cliente savedClient = clienteRepository.save(cliente);

        Assertions.assertThat(savedClient).isNotNull();
        Assertions.assertThat(savedClient.getId()).isGreaterThan(0);
        Assertions.assertThat(savedClient.getNombre()).isEqualTo("Javier Nuñes");
    }

    @SuppressWarnings("null")
    @Test
    public void ClienteRepository_SaveAll_ReturnMoreThanOneUsser(){

        List<Cliente> clientList = clienteRepository.findAll();
        clienteRepository.saveAll(clientList);

        Assertions.assertThat(clientList).isNotNull();
        Assertions.assertThat(clientList.size()).isEqualTo(0);
        Assertions.assertThat(clientList).hasSize(0);
    }

    @SuppressWarnings("null")
    @Test
    public void ClienteRepository_FindById_ReturnIfItIsNotNull(){

        Long firstClientId = firstClient.getId();
        Cliente client = clienteRepository.findById(firstClientId).get();

        Assertions.assertThat(client).isNotNull();
        Assertions.assertThat(client.getEmail()).isEqualTo("nelsonmartinezdh@gmail.com");
    }

    @SuppressWarnings("null")
    @Test
    public void ClienteRepository_UpdateClient_ReturnClientNotNull(){

        Long secondClientId = secondClient.getId();
        Optional<Cliente> currentClient = clienteRepository.findById(secondClientId);

        Assertions.assertThat(currentClient).isPresent();

        Cliente updatedClient = currentClient.get();
        updatedClient.setEmail("javierft@gmail.com");
        updatedClient.setDireccion("Calle29i#21-D1");

        Cliente savedClient = clienteRepository.save(updatedClient);

        Assertions.assertThat(savedClient.getNombre()).isNotNull();
        Assertions.assertThat(savedClient.getEmail()).isNotNull();
        Assertions.assertThat(savedClient.getEmail()).isEqualTo("javierft@gmail.com");
        Assertions.assertThat(savedClient.getDireccion()).isEqualTo("Calle29i#21-D1");
    }

    @SuppressWarnings("null")
    @Test
    public void ClienteRepository_DeleteClient_ReturnClientIsEmpty(){

        Long thirdClientId = thirdClient.getId();

        clienteRepository.deleteById(thirdClientId);

        Optional<Cliente> returnedClient = clienteRepository.findById(thirdClientId);
        Assertions.assertThat(returnedClient).isEmpty();
        Assertions.assertThat(returnedClient).isNotPresent();
    }

    @Test
    public void ClienteRepository_findClientByEmail_ReturnIsNotEmpty(){

        Optional<Cliente> foundClient =  clienteRepository.findByEmail("jpizarro@unimagdalena.edu.co");

        assertThat(foundClient).isPresent();

        Long id_clienteBuscado = foundClient.get().getId();

        assertThat(id_clienteBuscado).isEqualTo(firstClient.getId());
    }

    @Test
    public void ClienteRepository_findClientByDirection_ReturnIsNotNull(){

        List<Cliente> clientList = clienteRepository.findByDireccionContainingIgnoreCase("Calle29i#21-D1");

        Long idFirstClient = clientList.get(0).getId();

        Assertions.assertThat(clientList).isNotEmpty();
        Assertions.assertThat(idFirstClient).isEqualTo(firstClient.getId());
    }

    @Test
    public void ClienteRepository_findClientByNombreIfStartsWith_severalAssertions(){

        List<Cliente> clientList = clienteRepository.findByNombreStartingWithIgnoreCase("Javier");

        Assertions.assertThat(clientList.get(0).getId()).isEqualTo(secondClient.getId());
        Assertions.assertThat(clientList).isNotEmpty();
        Assertions.assertThat(clientList.size()).isEqualTo(1);
        Assertions.assertThat(clientList).first().hasFieldOrPropertyWithValue("nombre", "Javier");
    }

}
