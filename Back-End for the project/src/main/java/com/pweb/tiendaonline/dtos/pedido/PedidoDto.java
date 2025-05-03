package com.pweb.tiendaonline.dtos.pedido;

import com.pweb.tiendaonline.dtos.DetalleEnvio.DetalleEnvioDto;
import com.pweb.tiendaonline.dtos.ItemPedido.ItemPedidoDto;
import com.pweb.tiendaonline.dtos.cliente.ClienteDto;
import com.pweb.tiendaonline.dtos.pago.PagoDto;
import com.pweb.tiendaonline.entities.EstadoPedido;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public record PedidoDto(
        Long id,
        LocalDateTime fechaPedido,
        EstadoPedido status,
        PagoDto pago,
        DetalleEnvioDto detalleEnvio,
        ClienteDto cliente,
        List<ItemPedidoDto> itemsPedido

) {
    public List<ItemPedidoDto> itemsPedidos(){
        return Collections.unmodifiableList(itemsPedido);
    }

}
