package com.pweb.tiendaonline.dtos.DetalleEnvio;

import com.pweb.tiendaonline.dtos.pedido.PedidoToShowDto;

public record DetalleEnvioToShowDto (
        Long id,
        String direccion,
        String transportadora,
        String numeroGuia,
        PedidoToShowDto pedido
) {

}
