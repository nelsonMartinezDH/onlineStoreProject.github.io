package com.pweb.tiendaonline.controllers;

import com.pweb.tiendaonline.dtos.producto.ProductoToSaveDto;
import com.pweb.tiendaonline.dtos.producto.ProductoToShowDto;
import com.pweb.tiendaonline.exceptions.NotFoundException;
import com.pweb.tiendaonline.services.producto.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
public class ProductoController {

    private final ProductoService productoService;

    @Autowired
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoToShowDto> getProductoById(@PathVariable Long id) {
        try{
            ProductoToShowDto res = productoService.findProductoById(id);
            return ResponseEntity.ok().body(res);
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ProductoToShowDto>> getAllProductos() {
        try{
            List<ProductoToShowDto> res = productoService.findAllProductos();
            return ResponseEntity.ok().body(res);
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductoToShowDto>> getProductoByNombre(@RequestParam String searchTerm) {
        try{
            List<ProductoToShowDto> res = productoService.findProductoByNombre(searchTerm);
            return ResponseEntity.ok().body(res);
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/instock")
    public ResponseEntity<List<ProductoToShowDto>> getProductosInStock() {
        try{
            List<ProductoToShowDto> res = productoService.findProductoInStock();
            return ResponseEntity.ok().body(res);
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ProductoToShowDto> saveProducto(@RequestBody ProductoToSaveDto producto) {
        try {
            ProductoToShowDto res = productoService.saveProducto(producto);
            return ResponseEntity.ok().body(res);
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoToShowDto> updateProducto(@PathVariable Long id, @RequestBody ProductoToSaveDto producto) {
        try{
            ProductoToShowDto updatedProduct = productoService.updateProductoById(id, producto);
            return ResponseEntity.ok(updatedProduct);
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        try{
            productoService.deleteProductoById(id);
            return ResponseEntity.ok().body("Producto Eliminado");
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

}
