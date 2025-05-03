package com.pweb.tiendaonline.repositories;

import com.pweb.tiendaonline.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Buscar producto por nombre
    List<Producto> findByNombreContainingIgnoreCase(String searchTerm);

    // Buscar productos en stock
    @Query("SELECT p FROM Producto p WHERE p.stock > 0")
    List<Producto> findInStock();

    // Buscar productos que no superen un precio y un stock determinado
    List<Producto> findByPriceLessThanAndStockLessThan(Double precio, Integer stock);

}