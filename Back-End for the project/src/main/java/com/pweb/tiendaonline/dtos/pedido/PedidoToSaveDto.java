package com.pweb.tiendaonline.dtos.pedido;

import com.pweb.tiendaonline.dtos.cliente.ClienteToShowDto;
import com.pweb.tiendaonline.entities.EstadoPedido;

import java.time.LocalDateTime;

public record PedidoToSaveDto(
        LocalDateTime fechaPedido,
        EstadoPedido status,
        ClienteToShowDto cliente
) {

}
