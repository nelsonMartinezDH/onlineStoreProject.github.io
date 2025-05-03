package com.pweb.tiendaonline.dtos.cliente;

public record ClienteToSaveDto(
        String nombre,
        String email,
        String direccion
) {

}
