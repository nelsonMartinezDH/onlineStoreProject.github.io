package com.pweb.tiendaonline.repositories;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pweb.tiendaonline.AbstractIntegrationDBTest;
import com.pweb.tiendaonline.entities.Producto;

public class ProductoRepositoryTest extends AbstractIntegrationDBTest{

    private ProductoRepository productoRepository;

    @Autowired
    public ProductoRepositoryTest(ProductoRepository productoRepository){
        this.productoRepository = productoRepository;
    }

    private Producto firstProduct;
    private Producto secondProduct;
    private Producto thirdProduct;
    @SuppressWarnings("null")
    void initMockProducts(){

        firstProduct = Producto.builder()
                .nombre("Laptop")
                .price(100.000)
                .stock(12)
                .build();
        secondProduct = Producto.builder()
                .nombre("Mouse")
                .price(5.000)
                .stock(12)
                .build();
        thirdProduct = Producto.builder()
                .nombre("Keyboard")
                .price(20.000)
                .stock(12)
                .build();

        productoRepository.save(firstProduct);
        productoRepository.save(secondProduct);
        productoRepository.save(thirdProduct);

        productoRepository.flush();
    }

    @BeforeEach
    void productsetUp(){
        productoRepository.deleteAll();
    }

    @SuppressWarnings("null")
    @Test
    public void ProductoRepositoryTest_SaveAll_ReturnSavedProduct(){

        Producto producto = Producto.builder()
                .nombre("Head&Shoulder")
                .stock(2)
                .price(10.000)
                .build();

        Producto savedProduct = productoRepository.save(producto);

        Assertions.assertThat(savedProduct).isNotNull();
        Assertions.assertThat(savedProduct.getId()).isGreaterThan(0);
        Assertions.assertThat(savedProduct.getStock()).isEqualTo(2);
    }

    @Test
    public void ProductoRepositoryTest_SaveAll_ReturnMoreThanOneProduct(){

        List<Producto> productList = productoRepository.findAll();
        productoRepository.saveAll(productList);

        Assertions.assertThat(productList).isNotNull();
        Assertions.assertThat(productList.size()).isEqualTo(2);
        Assertions.assertThat(productList).hasSize(2);
        Assertions.assertThat(productList.contains(firstProduct)).isTrue();
        Assertions.assertThat(productList.contains(secondProduct)).isTrue();
    }

    @SuppressWarnings("null")
    @Test
    public void ProductoRepositoryTest_FindById_ReturnIsNotNull(){

        Long firstProductId = firstProduct.getId();
        Producto savedProduct = productoRepository.findById(firstProductId).get();

        Assertions.assertThat(savedProduct).isNotNull();
        Assertions.assertThat(savedProduct.getNombre()).isEqualTo("Laptop");
    }

    @Test
    public void ProductoRepositoryTest_UpdateProduct_ReturnIsNotNull(){

        Long thirdProductId = thirdProduct.getId();
        Optional<Producto> savedProduct = productoRepository.findById(thirdProductId);
        savedProduct.get().setPrice(50.000);
        savedProduct.get().setNombre("Keyboard pro max");

        Producto updatedProduct = productoRepository.save(savedProduct.get());

        Assertions.assertThat(savedProduct.get().getPrice()).isNotNull();
        Assertions.assertThat(savedProduct.get().getPrice()).isNotEqualTo(20.000);
        Assertions.assertThat(updatedProduct.getPrice()).isEqualTo(50.000);
        Assertions.assertThat(updatedProduct.getNombre().equals(savedProduct.get().getNombre())).isTrue();
    }

    @SuppressWarnings("null")
    @Test
    public void ProductoRepositoryTest_DeleteProduct_ReturnProductIsEmpty(){

        Long secondProductId = secondProduct.getId();
        productoRepository.deleteById(secondProductId);

        Optional<Producto> returnedProduct = productoRepository.findById(secondProductId);

        Assertions.assertThat(returnedProduct).isEmpty();
    }

    @Test
    public void ProductoRepositoryTest_findProductoByNombre_ReturnIsNotEmpty(){

        List<Producto> productList = productoRepository.findByNombreContainingIgnoreCase("Mouse");

        Assertions.assertThat(productList.size()).isGreaterThan(0);
        Assertions.assertThat(productList.contains(secondProduct)).isTrue();
    }

    @Test
    public void ProductoRepositoryTest_findProductoInStock_ReturnIsNotEmpty(){

        List<Producto> productList = productoRepository.findInStock();

        Assertions.assertThat(productList.size()).isGreaterThan(0);
    }

    @Test
    public void ProductoRepositoryTest_findProductoByPrecioAndStock_ReturnIsNotEmpty(){

        List<Producto> productList = productoRepository.findByPriceLessThanAndStockLessThan(20.000, 12);

        Assertions.assertThat(productList.isEmpty()).isFalse();
        Assertions.assertThat(productList.size()).isGreaterThan(0);
        Assertions.assertThat(productList.contains(thirdProduct)).isTrue();
    }
}
