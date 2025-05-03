package com.pweb.tiendaonline.dtos.pago;

import com.pweb.tiendaonline.dtos.pedido.PedidoDto;
import com.pweb.tiendaonline.entities.MetodoPago;

import java.time.LocalDateTime;

public record PagoDto(
        Long id,
        Double totalPago,
        LocalDateTime fechaPago,
        MetodoPago metodoPago,
        PedidoDto pedido
) {

}
