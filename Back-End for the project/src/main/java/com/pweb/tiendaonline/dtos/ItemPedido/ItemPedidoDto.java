package com.pweb.tiendaonline.dtos.ItemPedido;

import com.pweb.tiendaonline.dtos.pedido.PedidoDto;
import com.pweb.tiendaonline.dtos.producto.ProductoDto;

public record ItemPedidoDto(
        Long id,
        Integer cantidad,
        Double precioUnitario,
        PedidoDto pedido,
        ProductoDto producto
) {

}
