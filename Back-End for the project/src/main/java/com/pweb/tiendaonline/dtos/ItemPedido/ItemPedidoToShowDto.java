package com.pweb.tiendaonline.dtos.ItemPedido;

import com.pweb.tiendaonline.dtos.pedido.PedidoToShowDto;
import com.pweb.tiendaonline.dtos.producto.ProductoToShowDto;

public record ItemPedidoToShowDto(
        Long id,
        Integer cantidad,
        Double precioUnitario,
        PedidoToShowDto pedido,
        ProductoToShowDto product
) {

}
