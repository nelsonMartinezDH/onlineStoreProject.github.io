package com.pweb.tiendaonline.controllers;

import com.pweb.tiendaonline.dtos.cliente.ClienteDto;
import com.pweb.tiendaonline.dtos.pago.PagoDto;
import com.pweb.tiendaonline.dtos.pago.PagoToSaveDto;
import com.pweb.tiendaonline.dtos.pago.PagoToShowDto;
import com.pweb.tiendaonline.dtos.pedido.PedidoDto;
import com.pweb.tiendaonline.dtos.pedido.PedidoToShowDto;
import com.pweb.tiendaonline.entities.EstadoPedido;
import com.pweb.tiendaonline.entities.MetodoPago;
import com.pweb.tiendaonline.entities.Pago;
import com.pweb.tiendaonline.entities.Pedido;
import com.pweb.tiendaonline.mappers.PagoMapper;
import com.pweb.tiendaonline.services.pago.PagoService;
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

@WebMvcTest(controllers = PagoController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class PagoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PagoService pagoService;

    @Autowired
    private ObjectMapper objectMapper;
    private PagoMapper pagoMapper;
    private Pago payment;
    private PagoDto paymentDto;
    private PagoToSaveDto paymentToSaveDto;
    private PagoToShowDto paymentToShowDto;
    private Pedido order;
    private PedidoDto orderDto;
    private PedidoToShowDto orderToShowDto;
    @BeforeEach
    void setUp(){
        payment = Pago.builder()
                .id(1l)
                .fechaPago(LocalDateTime.of(2023, 12, 24, 13, 30, 0))
                .metodoPago(MetodoPago.EFECTIVO)
                .totalPago(500.000)
                .build();
        paymentDto = new PagoDto(
                payment.getId(),
                payment.getTotalPago(),
                payment.getFechaPago(),
                payment.getMetodoPago(),
                orderDto
        );
        paymentToSaveDto = new PagoToSaveDto(
                payment.getTotalPago(),
                payment.getFechaPago(),
                payment.getMetodoPago(),
                orderToShowDto
        );

        order = Pedido.builder()
                .id(1l)
                .status(EstadoPedido.PENDIENTE)
                .fechaPedido(LocalDateTime.of(2023, 12, 24, 13, 30, 0))
                .build();
        orderToShowDto = new PedidoToShowDto(
                order.getId(),
                order.getFechaPedido(),
                order.getStatus(),
                null
        );
    }

    @Test
    public void paymentControllerTest_getPagoById_ReturnResponse() throws Exception{
        Long paymentID = 1l;
        when(pagoService.findPagoById(paymentID)).thenReturn(paymentToShowDto);

        ResultActions response = mockMvc.perform(get("/api/v1/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(paymentDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(paymentDto.id())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.metodoDePago", CoreMatchers.is(paymentDto.metodoPago())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fechaDePago", CoreMatchers.is(paymentDto.fechaPago())));
    }

    @Test
    public void paymentControllerTest_getAllPagos_ReturnResponse() throws Exception{
        PagoDto firstPayment = new PagoDto(
                2l,
                350.000,
                LocalDateTime.of(2023, 12, 24, 13, 30, 0),
                MetodoPago.NEQUI,
                orderDto
        );
        List<PagoToShowDto> paymentList = Collections.singletonList(paymentToShowDto);

        when(pagoService.findAllPagos()).thenReturn(paymentList);

        ResultActions response = mockMvc.perform(get("/api/v1/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(paymentDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(Arrays.asList(firstPayment, paymentDto).size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", is(2l)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].metodoDePago", is(MetodoPago.NEQUI)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].metodoDePago", is(MetodoPago.EFECTIVO)));
    }

    @Test
    public void paymentControllerTest_getPagosByDateRange_ReturnResponse() throws Exception{
        LocalDateTime fechaInicial = LocalDateTime.of(2020, 10, 10, 10, 10);
        LocalDateTime fechaFinal = LocalDateTime.now();
        Long paymentID = 1l;
        when(pagoService.findPagoByFechaPagoBetween(fechaInicial, fechaFinal)).thenReturn(Arrays.asList(paymentToShowDto));

        ResultActions response = mockMvc.perform(get("/api/v1/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(paymentDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(paymentDto.id())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.metodoDePago", CoreMatchers.is(paymentDto.metodoPago())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fechaDePago", CoreMatchers.is(paymentDto.fechaPago())));
    }

    @Test
    public void paymentControllerTest_savePago_ReturnResponse() throws Exception{
        given(pagoMapper.pagoToSaveDtoToPagoEntity(any())).willReturn(payment);
        given(pagoService.savePago(paymentToSaveDto)).willReturn(paymentToShowDto);

        ResultActions response = mockMvc.perform(get("/api/v1/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(paymentDto.id())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.metodoDePago", CoreMatchers.is(paymentDto.metodoPago())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fechaDePago", CoreMatchers.is(paymentDto.fechaPago())));
    }

    @Test
    public void paymentControllerTest_updatePago_ReturnResponse() throws Exception{
        Long paymentID = 1l;
        when(pagoService.updatePagoById(paymentID, paymentToSaveDto)).thenReturn(paymentToShowDto);

        ResultActions response = mockMvc.perform(put("/api/v1/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(paymentDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(paymentDto.id())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.metodoDePago", CoreMatchers.is(paymentDto.metodoPago())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fechaDePago", CoreMatchers.is(paymentDto.fechaPago())));
    }

    @Test
    public void paymentControllerTest_deletePago_ReturnResponse() throws Exception{
        doNothing().when(pagoService).deletePagoById(1l);

        ResultActions response = mockMvc.perform(delete("/api/v1/payments")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
}
