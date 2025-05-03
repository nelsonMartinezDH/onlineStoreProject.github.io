package com.pweb.tiendaonline.dtos.producto;

public record ProductoToShowDto(
        Long id,
        String nombre,
        Double price,
        Integer stock
) {

}
