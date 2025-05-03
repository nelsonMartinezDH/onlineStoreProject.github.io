package com.pweb.tiendaonline.dtos.DetalleEnvio;

import com.pweb.tiendaonline.dtos.pedido.PedidoDto;

public record DetalleEnvioDto(
        Long id,
        String direccion,
        String transportadora,
        String numeroGuia,
        PedidoDto pedido
) {

}
