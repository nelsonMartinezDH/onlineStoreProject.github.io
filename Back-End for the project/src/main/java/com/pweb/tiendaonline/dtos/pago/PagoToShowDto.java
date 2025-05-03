package com.pweb.tiendaonline.dtos.pago;

import com.pweb.tiendaonline.dtos.pedido.PedidoToShowDto;
import com.pweb.tiendaonline.entities.MetodoPago;

import java.time.LocalDateTime;

public record PagoToShowDto (
        Long id,
        Double totalPago,
        LocalDateTime fechaPago,
        MetodoPago metodoPago,
        PedidoToShowDto pedido
) {

}
