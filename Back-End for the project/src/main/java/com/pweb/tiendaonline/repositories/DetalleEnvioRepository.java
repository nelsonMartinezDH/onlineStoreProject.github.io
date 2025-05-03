package com.pweb.tiendaonline.repositories;

import com.pweb.tiendaonline.entities.DetalleEnvio;
import com.pweb.tiendaonline.entities.EstadoPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DetalleEnvioRepository extends JpaRepository<DetalleEnvio, Long> {

    // Buscar detalles de envío por pedido id
    Optional<DetalleEnvio> findByPedidoId(Long pedidoId);

    // Buscar detalles de envío por transportadora
    List<DetalleEnvio> findByTransportadoraContainingIgnoreCase(String transportadora);

    // Buscar detalles de envio por estado pedido
    List<DetalleEnvio> findByPedidoStatus(EstadoPedido status);

}
