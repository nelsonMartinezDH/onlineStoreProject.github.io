package com.pweb.tiendaonline.services;

import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import java.util.Optional;

import com.pweb.tiendaonline.dtos.cliente.ClienteToShowDto;
import com.pweb.tiendaonline.mappers.ClienteMapper;
import com.pweb.tiendaonline.services.cliente.ClienteServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.pweb.tiendaonline.dtos.cliente.ClienteToSaveDto;
import com.pweb.tiendaonline.entities.Cliente;
import com.pweb.tiendaonline.repositories.ClienteRepository;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteMapper clienteMapper;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    private Cliente firstClient;
    private Cliente secondClient;
    private Cliente thirdClient;
    private Cliente fourthClient;
    @BeforeEach
    void setUp(){
        firstClient = Cliente.builder()
                .id(1l)
                .nombre("Nelson Martinez")
                .email("nelsonmartinezdh@gmail.com")
                .direccion("Calle29i#21-D1")
                .build();
        secondClient = Cliente.builder()
                .id(2l)
                .nombre("Javier Figueroa")
                .email("javierfigueroat@gmail.com")
                .direccion("Calle29h#15-G4")
                .build();
        thirdClient = Cliente.builder()
                .id(3l)
                .nombre("Julian Pizarro")
                .email("jpizarro@unimagdalena.edu.co")
                .direccion("Calle2i#34-I0")
                .build();
        fourthClient = Cliente.builder()
                .id(4l)
                .nombre("Javier Nuñes")
                .direccion("Calle29h#15-G4")
                .build();

    }

    @SuppressWarnings("null")
    @Test
    void givenClient_WhenSaveClient_ThenReturnClient(){
        ClienteToSaveDto clientToSave = new ClienteToSaveDto(
                "Nelson Martinez",
                "nelsonmartinezdh@gmail.com",
                "Calle29i#21-D1");

        given(clienteMapper.clienteToSaveDtoToClienteEntity(any())).willReturn(firstClient);
        given(clienteRepository.save(any())).willReturn(firstClient);

        ClienteToShowDto dtoClient = clienteService.saveCliente(clientToSave);

        assertThat(dtoClient).isNotNull();
        assertThat(dtoClient.id()).isEqualTo(1l);
    }

    @Test
    void clientServiceImpl_findClienteById_ThenReturnsClient(){

        when(clienteRepository.findById(3l)).thenReturn(Optional.ofNullable(thirdClient));

        ClienteToShowDto savedClient = clienteService.findClienteById(3l);

        Assertions.assertThat(savedClient).isNotNull();
        Assertions.assertThat(savedClient.nombre()).isEqualTo("Julian Pizarro");
    }

    @SuppressWarnings("null")
    @Test
    void clientServiceImpl_updateClientById_ThenReturnClient(){

        ClienteToSaveDto clientToSave = new ClienteToSaveDto(
                "Javier Figueroa",
                "javierfigueroat@gmail.com",
                "Calle29i#21-D1");
        when(clienteRepository.findById(2l)).thenReturn(Optional.ofNullable(secondClient));
        when(clienteRepository.save(Mockito.any(Cliente.class))).thenReturn(secondClient);

        ClienteToShowDto savedClient = clienteService.updateClienteById(2l, clientToSave);

        Assertions.assertThat(savedClient).isNotNull();
        Assertions.assertThat(savedClient.id()).isEqualTo(2l);
    }

    @SuppressWarnings("unlikely-arg-type")
    @Test
    void clientServiceImpl_findAllClientes_ThenReturnClients(){

        when(clienteRepository.findAll()).thenReturn(List.of(firstClient, secondClient, thirdClient, fourthClient));

        List<ClienteToShowDto> foundClients = clienteService.findAllClientes();

        Assertions.assertThat(foundClients.size()).isGreaterThan(0);
        Assertions.assertThat(foundClients.contains(firstClient)).isTrue();
        Assertions.assertThat(foundClients).isEqualTo(3);
    }

    @Test
    void clientServiceImpl_DeleteClient_ThenReturnClient(){

        given(clienteRepository.findById(2l)).willReturn(Optional.ofNullable(secondClient));
        doNothing().when(clienteRepository).deleteById(2l);

        ClienteToShowDto deletedClient = clienteService.findClienteById(2l);

        assertAll(() -> clienteService.deleteClienteById(2l));
        assertThat(deletedClient).isNull();
    }

    @Test
    void clientServiceImpl_findClienteByEmail_ThenReturnClient(){

        given(clienteRepository.findByEmail("nelsonmartinezdh@gmail.com")).willReturn(Optional.ofNullable(firstClient));

        ClienteToShowDto savedClient = clienteService.findClienteByEmail("nelsonmartinezdh@gmail.com");

        Assertions.assertThat(savedClient).isNotNull();
        Assertions.assertThat(savedClient.nombre()).isEqualTo("Nelson Martinez");
    }

    @SuppressWarnings("unlikely-arg-type")
    @Test
    void clientServiceImpl_findClienteByDireccion_ThenReturnClient(){

        given(clienteRepository.findByDireccionContainingIgnoreCase("Calle29h#15-G4")).willReturn(List.of(secondClient, fourthClient));

        List<ClienteToShowDto> foundClients = clienteService.findClienteByDireccionContainingIgnoreCase("Calle29h#15-G4");

        Assertions.assertThat(foundClients).isNotEmpty();
        assertThat(foundClients.size()).isEqualTo(2);
        assertThat(foundClients.contains(fourthClient)).isTrue();
    }

    @SuppressWarnings("unlikely-arg-type")
    @Test
    void clientServiceImpl_findClienteByNombreStartsWith_ThenReturnClient(){

        given(clienteRepository.findByNombreStartingWithIgnoreCase("Javier")).willReturn(List.of(secondClient, fourthClient));

        List<ClienteToShowDto> foundClients = clienteService.findClienteByNombreStartingWithIgnoreCase("Javier");

        assertThat(foundClients.size()).isGreaterThan(0);
        assertThat(foundClients.contains(fourthClient)).isTrue();
        assertThat(foundClients.get(0).email()).isEqualTo("javierfigueroat@gmail.com");
    }
}
