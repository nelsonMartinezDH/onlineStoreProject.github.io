package com.pweb.tiendaonline.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pweb.tiendaonline.dtos.cliente.ClienteDto;
import com.pweb.tiendaonline.dtos.cliente.ClienteToSaveDto;
import com.pweb.tiendaonline.dtos.cliente.ClienteToShowDto;
import com.pweb.tiendaonline.entities.Cliente;
import com.pweb.tiendaonline.mappers.ClienteMapper;
import com.pweb.tiendaonline.services.cliente.ClienteService;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = ClienteController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @Autowired
    private ObjectMapper objectMapper;
    private ClienteMapper clienteMapper;
    private Cliente client;
    private ClienteDto clientDto;
    private ClienteToSaveDto clientToSaveDto;
    private ClienteToShowDto clientToShowDto;


    @BeforeEach
    void setUp(){
        client = Cliente.builder()
                .id(1l)
                .nombre("Julian Pizarro")
                .email("jpizarro@unimagdalena.edu.co")
                .direccion("Calle2i#34-I0")
                .build();
        clientDto = new ClienteDto(
                client.getId(),
                client.getNombre(),
                client.getEmail(),
                client.getDireccion(),
                Collections.emptyList()
        );
        clientToSaveDto = new ClienteToSaveDto(
                client.getNombre(),
                client.getEmail(),
                client.getDireccion()
        );
    }

    @Test
    public void ClientController_getClienteById_ReturnResponseDto() throws Exception{
        Long clientId = 1l;
        when(clienteService.findClienteById(clientId)).thenReturn(clientToShowDto);

        ResultActions response = mockMvc.perform(get("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre", CoreMatchers.is(clientDto.nombre())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(clientDto.email())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.direccion", CoreMatchers.is(clientDto.direccion())));
    }

    @Test
    public void ClientController_getAllClientes_ReturnReponse() throws Exception{
        ClienteDto firstClient = new ClienteDto(
                2l,
                "Nelson",
                "nelsonmartinezdh@gmail.com",
                "Calle2i#34-I0",
                Collections.emptyList()
        );
        List<ClienteToShowDto> clientList = List.of(clientToShowDto);

        when(clienteService.findAllClientes()).thenReturn( clientList);

        ResultActions response = mockMvc.perform(get("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(Arrays.asList(firstClient, clientDto).size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", is(2l)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nombre", is("Nelson")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].email", is("jpizarro@unimagdalena.edu.co")));
    }

    @Test
    public void ClientController_getClienteByEmail_ReturnResponseDto() throws Exception{
        String clientEmail = "jpizarro@unimagdalena.edu.co";
        when(clienteService.findClienteByEmail(clientEmail)).thenReturn(clientToShowDto);

        ResultActions response = mockMvc.perform(get("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre", CoreMatchers.is(clientDto.nombre())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(clientDto.email())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.direccion", CoreMatchers.is(clientDto.direccion())));
    }

    @Test
    public void ClientController_getClientesByCity_ReturnResponseDto() throws Exception{
        ClienteDto firstClient = new ClienteDto(
                2l,
                "Nelson",
                "nelsonmartinezdh@gmail.com",
                "Calle2i#34-I0",
                Collections.emptyList()
        );
        List<ClienteToShowDto> clientList = List.of(clientToShowDto);
        String clientDirection = "Calle2i#34-I0";
        when(clienteService.findClienteByDireccionContainingIgnoreCase(clientDirection)).thenReturn(clientList);

        ResultActions response = mockMvc.perform(get("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(Arrays.asList(firstClient, clientDto).size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", is(2l)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nombre", is("Nelson")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].email", is("jpizarro@unimagdalena.edu.co")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].direccion", is("Calle2i#34-I0")));
    }

    @Test
    public void ClientController_saveCliente_ReturnResponseDto() throws Exception{
        given(clienteMapper.clienteToSaveDtoToClienteEntity(any())).willReturn(client);
        given(clienteService.saveCliente(clientToSaveDto)).willReturn(clientToShowDto);

        ResultActions response = mockMvc.perform(get("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre", CoreMatchers.is(clientDto.nombre())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.direccion", CoreMatchers.is(clientDto.direccion())));
    }

    @Test
    public void ClientController_updateCliente_ReturnResponseDto() throws Exception{
        Long clientId = 1l;
        when(clienteService.updateClienteById(clientId, clientToSaveDto)).thenReturn(clientToShowDto);

        ResultActions response = mockMvc.perform(put("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre", CoreMatchers.is(clientDto.nombre())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(clientDto.email())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.direccion", CoreMatchers.is(clientDto.direccion())));
    }

    @Test
    public void ClientController_deleteCliente_ReturnResponseDto() throws Exception{
        doNothing().when(clienteService).deleteClienteById(1l);

        ResultActions response = mockMvc.perform(delete("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
}
