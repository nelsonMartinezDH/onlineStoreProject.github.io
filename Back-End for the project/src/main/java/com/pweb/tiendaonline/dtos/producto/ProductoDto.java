package com.pweb.tiendaonline.dtos.producto;

import com.pweb.tiendaonline.dtos.ItemPedido.ItemPedidoDto;

import java.util.Collections;
import java.util.List;

public record ProductoDto(
        Long id,
        String nombre,
        Double price,
        Integer stock,
        List<ItemPedidoDto> itemsPedidos
) {

    public List<ItemPedidoDto> itemPedidos(){
        return Collections.unmodifiableList(itemsPedidos);
    }

}
