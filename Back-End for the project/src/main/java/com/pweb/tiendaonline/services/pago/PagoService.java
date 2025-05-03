package com.pweb.tiendaonline.services.pago;


import com.pweb.tiendaonline.dtos.pago.PagoToSaveDto;
import com.pweb.tiendaonline.dtos.pago.PagoToShowDto;
import com.pweb.tiendaonline.entities.MetodoPago;

import java.time.LocalDateTime;
import java.util.List;

public interface PagoService {

    PagoToShowDto findPagoById(Long id);
    List<PagoToShowDto> findAllPagos();
    PagoToShowDto findPagoByPedidoIdAndMetodoPago(Long PedidoId, MetodoPago metodoPago);
    List<PagoToShowDto> findPagoByFechaPagoBetween(LocalDateTime startDate, LocalDateTime endDate);
    PagoToShowDto savePago(PagoToSaveDto pagoToSaveDto);
    PagoToShowDto updatePagoById(Long pagoId, PagoToSaveDto pagoToSaveDto);
    void deletePagoById(Long pagoId);

}
