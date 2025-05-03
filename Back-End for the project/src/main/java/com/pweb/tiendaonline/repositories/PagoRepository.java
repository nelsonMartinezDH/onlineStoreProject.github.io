package com.pweb.tiendaonline.repositories;

import com.pweb.tiendaonline.entities.MetodoPago;
import com.pweb.tiendaonline.entities.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PagoRepository  extends JpaRepository<Pago, Long> {

    // Buscar pagos dentro de un rango de fecha
    List<Pago> findByFechaPagoBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Buscar pagos por Pedido Id y método de pago
    Optional<Pago> findByPedidoIdAndMetodoPago(Long PedidoId, MetodoPago metodoPago);

}
