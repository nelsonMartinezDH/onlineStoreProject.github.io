package com.pweb.tiendaonline.dtos.cliente;

import com.pweb.tiendaonline.dtos.pedido.PedidoDto;

import java.util.Collections;
import java.util.List;

public record ClienteDto(
        Long id,
        String nombre,
        String email,
        String direccion,
        List<PedidoDto> pedidos

) {
    public List<PedidoDto> pedidos(){
        return Collections.unmodifiableList(pedidos);
    }
}
