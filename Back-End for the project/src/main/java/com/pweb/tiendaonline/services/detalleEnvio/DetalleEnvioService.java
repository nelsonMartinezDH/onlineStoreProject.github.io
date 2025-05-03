package com.pweb.tiendaonline.services.detalleEnvio;


import com.pweb.tiendaonline.dtos.DetalleEnvio.DetalleEnvioToSaveDto;
import com.pweb.tiendaonline.dtos.DetalleEnvio.DetalleEnvioToShowDto;

import java.util.List;

public interface DetalleEnvioService {

    DetalleEnvioToShowDto getDetalleEnvioById(Long id);

    List<DetalleEnvioToShowDto> getAllDetalleEnvios();

    DetalleEnvioToShowDto getDetalleEnvioByPedidoId(Long pedido_id);

    List<DetalleEnvioToShowDto> getDetalleEnvioByTransportadora(String transportadora);

    DetalleEnvioToShowDto saveDetalleEnvio(DetalleEnvioToSaveDto detalleEnvioToSaveDto);

    DetalleEnvioToShowDto updateDetalleEnvioById(Long id, DetalleEnvioToSaveDto detalleEnvioToSaveDto);

    void deleteDetalleEnvioById(Long id);

}
