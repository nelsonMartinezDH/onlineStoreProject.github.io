package com.pweb.tiendaonline.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pweb.tiendaonline.dtos.DetalleEnvio.DetalleEnvioDto;
import com.pweb.tiendaonline.dtos.DetalleEnvio.DetalleEnvioToSaveDto;
import com.pweb.tiendaonline.dtos.DetalleEnvio.DetalleEnvioToShowDto;
import com.pweb.tiendaonline.dtos.pedido.PedidoDto;
import com.pweb.tiendaonline.dtos.pedido.PedidoToShowDto;
import com.pweb.tiendaonline.entities.DetalleEnvio;
import com.pweb.tiendaonline.entities.EstadoPedido;
import com.pweb.tiendaonline.entities.Pedido;
import com.pweb.tiendaonline.mappers.DetalleEnvioMapper;
import com.pweb.tiendaonline.services.detalleEnvio.DetalleEnvioService;
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

@WebMvcTest(controllers = DetalleEnvioController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class DetalleEnvioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DetalleEnvioService detalleEnvioService;
    private DetalleEnvioMapper detalleEnvioMapper;

    @Autowired
    private ObjectMapper objectMapper;
    private DetalleEnvio shippingDetail;
    private DetalleEnvioDto shippingDetailDto;
    private DetalleEnvioToSaveDto shippingDetailToSaveDto;
    private DetalleEnvioToShowDto shippingDetailToShowDto;
    private Pedido order;
    private PedidoDto orderDto;
    private PedidoToShowDto orderToShowDto;

    @BeforeEach
    void setUp(){
        shippingDetail = DetalleEnvio.builder()
                .id(1l)
                .direccion("Calle2i#34-I0")
                .numeroGuia("SEV9876543210")
                .transportadora("Servientrega")
                .pedido(order)
                .build();
        shippingDetailDto = new DetalleEnvioDto(
                shippingDetail.getId(),
                shippingDetail.getDireccion(),
                shippingDetail.getTransportadora(),
                shippingDetail.getNumeroGuia(),
                orderDto
        );
        shippingDetailToSaveDto = new DetalleEnvioToSaveDto(
                shippingDetail.getDireccion(),
                shippingDetail.getTransportadora(),
                shippingDetail.getNumeroGuia(),
                orderToShowDto
        );
        shippingDetailToShowDto = new DetalleEnvioToShowDto(
                shippingDetail.getId(),
                shippingDetail.getDireccion(),
                shippingDetail.getTransportadora(),
                shippingDetail.getNumeroGuia(),
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
    public void ShippingDetailController_getDetalleEnvioById_ReturnResponse() throws Exception{
        Long shippingDetailId = 1l;
        when(detalleEnvioService.getDetalleEnvioById(shippingDetailId)).thenReturn(shippingDetailToShowDto);

        ResultActions response = mockMvc.perform(get("/api/v1/shipping")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(shippingDetailDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(shippingDetailDto.id())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numeroGuia", CoreMatchers.is(shippingDetailDto.numeroGuia())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.transportadora", CoreMatchers.is(shippingDetailDto.transportadora())));
    }

    @Test
    public void ShippingDetailController_getAllDetallesEnvio_ReturnResponse() throws Exception{
        DetalleEnvioDto firstShippingDetail = new DetalleEnvioDto(
                2l,
                "Calle2i#34-I0",
                "Inter Rapidísimo",
                "IRV1234567890",
                orderDto
        );
        List<DetalleEnvioToShowDto> shippingDetailList = Collections.singletonList(shippingDetailToShowDto);

        when(detalleEnvioService.getAllDetalleEnvios()).thenReturn(shippingDetailList);

        when(detalleEnvioService.getAllDetalleEnvios()).thenReturn(shippingDetailList);

        ResultActions response = mockMvc.perform(get("/api/v1/shipping")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(shippingDetailDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(Arrays.asList(firstShippingDetail, shippingDetailDto).size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", is(2l)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].transportadora", is("Inter Rapidísimo")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].numeroGuia", is("SEV9876543210")));
    }

    @Test
    public void ShippingDetailController_getDetallesEnvioByOrderId_ReturnResponse() throws Exception{
        Long shippingDetail = 1l;
        when(detalleEnvioService.getDetalleEnvioById(shippingDetail)).thenReturn(shippingDetailToShowDto);

        ResultActions response = mockMvc.perform(get("/api/v1/shipping")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(shippingDetailDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(shippingDetailDto.id())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numeroGuia", CoreMatchers.is(shippingDetailDto.numeroGuia())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.transportadora", CoreMatchers.is(shippingDetailDto.transportadora())));
    }

    @Test
    public void ShippingDetailController_saveDetalleEnvio_ReturnResponse() throws Exception{
        given(detalleEnvioMapper.detalleEnvioToSaveDtoToDetalleEnvioEntity(any())).willReturn(shippingDetail);
        given(detalleEnvioService.saveDetalleEnvio(shippingDetailToSaveDto)).willReturn(shippingDetailToShowDto);

        ResultActions response = mockMvc.perform(get("/api/v1/shipping")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(shippingDetailDto.id())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numeroGuia", CoreMatchers.is(shippingDetailDto.numeroGuia())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.transportadora", CoreMatchers.is(shippingDetailDto.transportadora())));
    }

    @Test
    public void ShippingDetailController_updateDetalleEnvio_ReturnResponse() throws Exception{
        Long shippingDetailId = 1l;
        when(detalleEnvioService.updateDetalleEnvioById(shippingDetailId, shippingDetailToSaveDto)).thenReturn(shippingDetailToShowDto);

        ResultActions response = mockMvc.perform(put("/api/v1/shipping")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(shippingDetailDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(shippingDetailDto.id())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numeroGuia", CoreMatchers.is(shippingDetailDto.numeroGuia())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.transportadora", CoreMatchers.is(shippingDetailDto.transportadora())));
    }

    @Test
    public void ShippingDetailController_deleteDetalleEnvio_ReturnResponse() throws Exception{
        doNothing().when(detalleEnvioService).deleteDetalleEnvioById(1l);

        ResultActions response = mockMvc.perform(delete("/api/v1/shipping")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
}

