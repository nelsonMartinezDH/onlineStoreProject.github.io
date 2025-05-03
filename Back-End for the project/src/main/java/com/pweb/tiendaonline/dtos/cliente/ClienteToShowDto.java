package com.pweb.tiendaonline.dtos.cliente;

public record ClienteToShowDto(
        Long id,
        String nombre,
        String email,
        String direccion
) {

}
