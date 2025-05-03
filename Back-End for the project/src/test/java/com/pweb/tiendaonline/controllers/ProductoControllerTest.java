package com.pweb.tiendaonline.controllers;

import com.pweb.tiendaonline.dtos.producto.ProductoDto;
import com.pweb.tiendaonline.dtos.producto.ProductoToSaveDto;
import com.pweb.tiendaonline.dtos.producto.ProductoToShowDto;
import com.pweb.tiendaonline.entities.Producto;
import com.pweb.tiendaonline.mappers.ProductoMapper;
import com.pweb.tiendaonline.services.producto.ProductoService;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = ProductoController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductoService productoService;

    @Autowired
    private ObjectMapper objectMapper;
    private ProductoMapper productoMapper;
    private Producto product;
    private ProductoDto productDto;
    private ProductoToShowDto productToShowDto;
    private ProductoToSaveDto productToSaveDto;
    @BeforeEach
    void setUp(){
        product = Producto.builder()
                .id(1l)
                .nombre("Keyboard")
                .price(50.000)
                .stock(12)
                .build();
        productDto = new ProductoDto(
                product.getId(),
                product.getNombre(),
                product.getPrice(),
                product.getStock(),
                Collections.emptyList()
        );
        productToSaveDto = new ProductoToSaveDto(
                product.getNombre(),
                product.getPrice(),
                product.getStock()
        );
    }

    @Test
    public void productControllerTest_getProductoById_ReturnResponse() throws Exception{
        Long productID = product.getId();
        when(productoService.findProductoById(productID)).thenReturn(productToShowDto);

        ResultActions response = mockMvc.perform(get("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(productDto.id())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.Nombre", CoreMatchers.is(productDto.nombre())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.Price", CoreMatchers.is(productDto.price())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.Stock", CoreMatchers.is(productDto.stock())));
    }

    @Test
    public void productControllerTest_getAllProductos_ReturnResponse() throws Exception{
        ProductoDto firstProduct = new ProductoDto(
                2l,
                "Mouse",
                10.000,
                12,
                Collections.emptyList()
        );

        List<ProductoToShowDto> productList = Collections.singletonList(productToShowDto);

        when(productoService.findAllProductos()).thenReturn(productList);

        ResultActions response = mockMvc.perform(get("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(Arrays.asList(firstProduct, productDto).size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", is(2l)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].Nombre", is("Mouse")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].Stock", is(12)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].Price", is(50.000)));
    }

    @Test
    public void productControllerTest_getProductoByNombre_ReturnResponse() throws Exception{
        String productNAME = product.getNombre();
        when(productoService.findProductoByNombre(productNAME)).thenReturn(Arrays.asList(productToShowDto));

        ResultActions response = mockMvc.perform(get("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(productDto.id())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.Nombre", CoreMatchers.is(productDto.nombre())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.Price", CoreMatchers.is(productDto.price())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.Stock", CoreMatchers.is(productDto.stock())));
    }

    @Test
    public void productControllerTest_getProductosInStock_ReturnResponse() throws Exception{
        when(productoService.findProductoInStock()).thenReturn(Arrays.asList(productToShowDto));

        ResultActions response = mockMvc.perform(get("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(Arrays.asList(productDto).size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", CoreMatchers.is(1l)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].Nombre", CoreMatchers.is("Keyboard")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].Price", CoreMatchers.is(50.000)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].Stock", CoreMatchers.is(12)));
    }

    @Test
    public void productControllerTest_saveProducto_ReturnResponse() throws Exception{
        given(productoMapper.productoToSaveDtoToProductoEntity(any())).willReturn(product);
        given(productoService.saveProducto(productToSaveDto)).willReturn(productToShowDto);

        ResultActions response = mockMvc.perform(get("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(productDto.id())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.Precio", CoreMatchers.is(productDto.price())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.Nombre", CoreMatchers.is(productDto.nombre())));
    }

    @Test
    public void productControllerTest_updateProducto_ReturnResponse() throws Exception{
        Long productID = product.getId();
        when(productoService.updateProductoById(productID, productToSaveDto)).thenReturn(productToShowDto);

        ResultActions response = mockMvc.perform(put("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(productDto.id())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.Stock", CoreMatchers.is(productDto.stock())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.Price", CoreMatchers.is(productDto.price())));
    }

    @Test
    public void productControllerTest_deleteProducto_ReturnResponse() throws Exception{
        doNothing().when(productoService).deleteProductoById(product.getId());

        ResultActions response = mockMvc.perform(delete("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
