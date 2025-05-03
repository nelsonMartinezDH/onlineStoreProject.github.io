package com.pweb.tiendaonline.repositories;

import com.pweb.tiendaonline.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    // Encontrar cliente por email
    Optional<Cliente> findByEmail(String email);

    // Encontrar cliente por dirección
    List<Cliente> findByDireccionContainingIgnoreCase(String direccion);

    // Encontrar cliente que su nombre inicie con
    List<Cliente> findByNombreStartingWithIgnoreCase(String nombre);

}
