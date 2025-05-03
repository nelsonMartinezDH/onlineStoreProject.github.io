package com.pweb.tiendaonline.dtos.DetalleEnvio;

import com.pweb.tiendaonline.dtos.pedido.PedidoToShowDto;

public record DetalleEnvioToSaveDto(
        String direccion,
        String transportadora,
        String numeroGuia,
        PedidoToShowDto pedido
) {

}
