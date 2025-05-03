package com.pweb.tiendaonline.dtos.producto;

public record ProductoToSaveDto(
        String nombre,
        Double price,
        Integer stock
) {

}
