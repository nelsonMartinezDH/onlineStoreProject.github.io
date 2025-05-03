package com.pweb.tiendaonline.repositories;

import com.pweb.tiendaonline.entities.EstadoPedido;
import com.pweb.tiendaonline.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    // Buscar pedidos en un rango de fechas
    List<Pedido> findByFechaPedidoBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Buscar pedidos por Cliente Id y Estado Pedido
    List<Pedido> findByClienteIdAndStatus(Long clienteId, EstadoPedido status);

    // Buscar pedidos con sus artículos por Cliente Id
    @Query("SELECT p FROM Pedido p JOIN FETCH p.itemsPedido WHERE p.cliente.id = ?1")
    List<Pedido> findByClienteIdWithItemsFetch(Long clienteId);

}
