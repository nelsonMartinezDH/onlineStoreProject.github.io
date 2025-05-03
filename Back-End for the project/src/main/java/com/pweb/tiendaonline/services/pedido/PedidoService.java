package com.pweb.tiendaonline.services.pedido;


import com.pweb.tiendaonline.dtos.pedido.PedidoToSaveDto;
import com.pweb.tiendaonline.dtos.pedido.PedidoToShowDto;
import com.pweb.tiendaonline.entities.EstadoPedido;

import java.time.LocalDateTime;
import java.util.List;

public interface PedidoService {
    PedidoToShowDto savePedido(PedidoToSaveDto pedido);
    PedidoToShowDto updatePedidoById(Long id, PedidoToSaveDto pedido);
    PedidoToShowDto findPedidoById(Long id);
    List<PedidoToShowDto> findAllPedidos();
    void deletePedidoById(Long id);

    List<PedidoToShowDto> findPedidosByFechaPedidoBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<PedidoToShowDto> findPedidosByClienteIdAndStatus(Long cliente_id, EstadoPedido status);
    List<PedidoToShowDto> findPedidosByClienteIdWithItemsFetch(Long cliente_id);

}
