package com.pweb.tiendaonline.services.producto;

import com.pweb.tiendaonline.dtos.producto.ProductoToSaveDto;
import com.pweb.tiendaonline.dtos.producto.ProductoToShowDto;

import java.util.List;

public interface ProductoService {

    List<ProductoToShowDto> findAllProductos();
    ProductoToShowDto findProductoById(Long id);
    List<ProductoToShowDto>  findProductoByNombre(String nombre);
    List<ProductoToShowDto> findProductoInStock();
    ProductoToShowDto saveProducto(ProductoToSaveDto producto);
    ProductoToShowDto updateProductoById(Long id, ProductoToSaveDto producto);
    void deleteProductoById(Long id);

}
