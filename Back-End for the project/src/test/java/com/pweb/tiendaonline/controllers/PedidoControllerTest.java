package com.pweb.tiendaonline.controllers;

import com.pweb.tiendaonline.dtos.DetalleEnvio.DetalleEnvioDto;
import com.pweb.tiendaonline.dtos.DetalleEnvio.DetalleEnvioToShowDto;
import com.pweb.tiendaonline.dtos.cliente.ClienteDto;
import com.pweb.tiendaonline.dtos.cliente.ClienteToShowDto;
import com.pweb.tiendaonline.dtos.pago.PagoDto;
import com.pweb.tiendaonline.dtos.pago.PagoToShowDto;
import com.pweb.tiendaonline.dtos.pedido.PedidoDto;
import com.pweb.tiendaonline.dtos.pedido.PedidoToSaveDto;
import com.pweb.tiendaonline.dtos.pedido.PedidoToShowDto;
import com.pweb.tiendaonline.entities.*;
import com.pweb.tiendaonline.mappers.PedidoMapper;
import com.pweb.tiendaonline.services.pedido.PedidoService;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = PedidoController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PedidoService pedidoService;

    @Autowired
    private ObjectMapper objectMapper;
    private PedidoMapper pedidoMapper;
    private Pedido order;
    private PedidoDto orderDto;
    private PedidoToShowDto orderToShowDto;
    private PedidoToSaveDto orderToSaveDto;
    private Cliente client;
    private ClienteDto clientDto;
    private ClienteToShowDto clientToShowDto;
    private Pago pago;
    private PagoDto pagoDto;
    private PagoToShowDto pagoToShowDto;
    private DetalleEnvio detalleEnvio;
    private DetalleEnvioDto detalleEnvioDto;
    private DetalleEnvioToShowDto detalleEnvioToShowDto;

    @BeforeEach
    void setUp(){
        order = Pedido.builder()
                .id(1l)
                .status(EstadoPedido.ENTREGADO.ENVIADO)
                .fechaPedido(LocalDateTime.of(2023, 12, 24, 13, 30, 0))
                .cliente(client)
                .build();
        orderDto = new PedidoDto(
                order.getId(),
                order.getFechaPedido(),
                order.getStatus(),
                pagoDto,
                detalleEnvioDto,
                clientDto,
                Collections.emptyList()
        );
        orderToSaveDto = new PedidoToSaveDto(
                order.getFechaPedido(),
                order.getStatus(),
                clientToShowDto
        );

        client = Cliente.builder()
                .id(1l)
                .nombre("Julian Pizarro")
                .direccion("Calle2i#34-I0")
                .email("jpizarro@unimagdalena.edu.co")
                .build();
        clientDto = new ClienteDto(
                client.getId(),
                client.getNombre(),
                client.getEmail(),
                client.getDireccion(),
                Collections.emptyList()
        );
    }

    @Test
    public void orderControllerTest_getPedidoById_ReturnResponse() throws Exception{
        Long orderID = order.getId();
        when(pedidoService.findPedidoById(orderID)).thenReturn(orderToShowDto);

        ResultActions response = mockMvc.perform(get("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(orderDto.id())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.estado", CoreMatchers.is(orderDto.status())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fechaPedido", CoreMatchers.is(orderDto.fechaPedido())));
    }

    @Test
    public void orderControllerTest_getAllPedidos_ReturnResponse() throws Exception{
        PedidoDto firstOrder = new PedidoDto(
                2l,
                LocalDateTime.of(2023, 12, 24, 13, 30, 0),
                EstadoPedido.ENVIADO,
                pagoDto,
                detalleEnvioDto,
                clientDto,
                Collections.emptyList()
        );

        List<PedidoToShowDto> orderList = Collections.singletonList(orderToShowDto);

        when(pedidoService.findAllPedidos()).thenReturn(orderList);

        ResultActions response = mockMvc.perform(get("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(Arrays.asList(firstOrder, orderDto).size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", is(2l)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].Estado", is(EstadoPedido.ENVIADO)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].Estado", is(EstadoPedido.ENVIADO)));
    }

    @Test
    public void orderControllerTest_getPedidosByDateRange_ReturnResponse() throws Exception{
        LocalDateTime fechaInicial = LocalDateTime.of(2020, 10, 10, 10, 10);
        LocalDateTime fechaFinal = LocalDateTime.now();
        when(pedidoService.findPedidosByFechaPedidoBetween(fechaInicial, fechaFinal)).thenReturn(Arrays.asList(orderToShowDto));

        ResultActions response = mockMvc.perform(get("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(orderDto.id())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.estado", CoreMatchers.is(orderDto.status())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fechaPedido", CoreMatchers.is(orderDto.fechaPedido())));
    }

    @Test
    public void orderControllerTest_savePedido_ReturnResponse() throws Exception{
        given(pedidoMapper.pedidoToSaveDtoToPedido(any())).willReturn(order);
        given(pedidoService.savePedido(orderToSaveDto)).willReturn(orderToShowDto);

        ResultActions response = mockMvc.perform(get("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(orderDto.id())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.estado", CoreMatchers.is(orderDto.status())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fechaPedido", CoreMatchers.is(orderDto.fechaPedido())));
    }

    @Test
    public void orderControllerTest_updatePedido_ReturnResponse() throws Exception{
        Long orderID = order.getId();
        when(pedidoService.updatePedidoById(orderID, orderToSaveDto)).thenReturn(orderToShowDto);

        ResultActions response = mockMvc.perform(put("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(orderDto.id())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.estado", CoreMatchers.is(orderDto.status())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fechaPedido", CoreMatchers.is(orderDto.fechaPedido())));
    }

    @Test
    public void orderControllerTest_deletePedido_ReturnResponse() throws Exception{
        doNothing().when(pedidoService).deletePedidoById(1l);

        ResultActions response = mockMvc.perform(delete("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
        response.andExpect(MockMvcResultMatchers.status().isNotFound());

    }
}
