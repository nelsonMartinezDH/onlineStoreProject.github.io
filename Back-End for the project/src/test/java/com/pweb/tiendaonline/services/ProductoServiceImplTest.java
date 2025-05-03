package com.pweb.tiendaonline.services;

import com.pweb.tiendaonline.dtos.producto.ProductoToSaveDto;
import com.pweb.tiendaonline.dtos.producto.ProductoToShowDto;
import com.pweb.tiendaonline.entities.Producto;
import com.pweb.tiendaonline.mappers.ProductoMapper;
import com.pweb.tiendaonline.repositories.ProductoRepository;
import com.pweb.tiendaonline.services.producto.ProductoService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ProductoServiceImplTest {

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private ProductoMapper productoMapper;

    @InjectMocks
    private ProductoService productoService;

    private Producto firstproduct;
    private ProductoToShowDto firstProductToShowDto;
    private Producto secondProduct;
    private ProductoToShowDto secondProductToShowDto;

    @BeforeEach
    void setUp() {
        firstproduct = Producto.builder()
                .id(1l)
                .nombre("Laptop")
                .stock(12)
                .price(150.000)
                .build();
        firstProductToShowDto = new ProductoToShowDto(
                firstproduct.getId(),
                firstproduct.getNombre(),
                firstproduct.getPrice(),
                firstproduct.getStock()
        );
        secondProduct = Producto.builder()
                .id(2l)
                .nombre("Mouse")
                .price(20.000)
                .stock(12)
                .build();
        secondProductToShowDto = new ProductoToShowDto(
                secondProduct.getId(),
                secondProduct.getNombre(),
                secondProduct.getPrice(),
                secondProduct.getStock()
        );
    }

    @Test
    void ProductServiceTest_WhenSaveProduct_ThenReturnProduct() {

        ProductoToSaveDto productToSaveDto = new ProductoToSaveDto(
                firstproduct.getNombre(),
                firstproduct.getPrice(),
                firstproduct.getStock()
        );

        given(productoRepository.findById(firstproduct.getId())).willReturn(Optional.ofNullable(firstproduct));
        given(productoMapper.productoToSaveDtoToProductoEntity(any())).willReturn(firstproduct);
        when(productoRepository.save(any())).thenReturn(firstproduct);

        ProductoToShowDto savedProduct = productoService.saveProducto(productToSaveDto);

        Assertions.assertThat(savedProduct).isNotNull();
        Assertions.assertThat(savedProduct.nombre()).isEqualTo("Laptop");
    }

    @Test
    void ProductServiceTest_WhenfindProductoById_ThenReturnProduct() {

        given(productoRepository.findById(secondProduct.getId())).willReturn(Optional.ofNullable(secondProduct));

        ProductoToShowDto savedProduct = productoService.findProductoById(secondProduct.getId());

        Assertions.assertThat(savedProduct).isNotNull();
        Assertions.assertThat(savedProduct.nombre()).isEqualTo("Mouse");
    }

    @Test
    void ProductServiceTest_WhenupdateProducto_ThenReturnProduct() {

        ProductoToSaveDto productToSaveDto = new ProductoToSaveDto(
                firstproduct.getNombre(),
                firstproduct.getPrice(),
                firstproduct.getStock()
        );
        when(productoRepository.findById(1l)).thenReturn(Optional.ofNullable(firstproduct));
        when(productoRepository.save(Mockito.any(Producto.class))).thenReturn(firstproduct);

        ProductoToShowDto savedProduct = productoService.findProductoById(1l);

        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.id()).isEqualTo(1l);
    }

    @Test
    void ProductServiceTest_WhendeleteProducto_ThenReturnProduct() {

        given(productoRepository.findById(1l)).willReturn(Optional.ofNullable(firstproduct));
        doNothing().when(productoRepository).deleteById(1l);

        ProductoToShowDto deletedProduct = productoService.findProductoById(1l);

        assertAll(() -> productoService.deleteProductoById(1l));
        assertThat(deletedProduct).isNull();
    }

    @Test
    void ProductServiceTest_WhenfindProductoByNombre_ThenReturnProduct() {

        when(productoRepository.findByNombreContainingIgnoreCase("Mouse")).thenReturn(List.of(secondProduct));

        List<ProductoToShowDto> savedProducts = productoService.findProductoByNombre("Mouse");

        assertThat(savedProducts.size()).isGreaterThan(0);
        assertThat(savedProducts.contains(secondProduct)).isTrue();
    }

    @Test
    void ProductServiceTest_WhenfindProductoInStock_ThenReturnProduct() {

        when(productoRepository.findInStock()).thenReturn(List.of(firstproduct, secondProduct));

        List<ProductoToShowDto> savedProducts = productoService.findProductoInStock();

        assertThat(savedProducts.size()).isGreaterThan(0);
        assertThat(savedProducts.contains(secondProduct)).isTrue();
    }

    @Test
    void ProductServiceTest_WhenfindProductoByPriceAndStock_ThenReturnProduct() {

        given(productoRepository.findByPriceLessThanAndStockLessThan(150.000, 12)).willReturn(List.of(firstproduct));

        List<ProductoToShowDto> savedProducts = productoService.findProductoInStock();

        assertThat(savedProducts.size()).isGreaterThan(0);
        assertThat(savedProducts.contains(firstproduct)).isTrue();
        assertThat(savedProducts.get(0).id()).isEqualTo(1l);
        assertThat(savedProducts.get(0).nombre());

    }
}
