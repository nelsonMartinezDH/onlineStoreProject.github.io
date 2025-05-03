package com.pweb.tiendaonline.services.cliente;

import com.pweb.tiendaonline.dtos.cliente.ClienteToSaveDto;
import com.pweb.tiendaonline.dtos.cliente.ClienteToShowDto;

import java.util.List;

public interface ClienteService {
    ClienteToShowDto SaveCliente(ClienteToSaveDto cliente);
    ClienteToShowDto updateClienteById(Long id, ClienteToSaveDto cliente);
    ClienteToShowDto findClienteById(Long id);
    List<ClienteToShowDto> findAllClientes();
    void deleteClienteById(Long id);
    ClienteToShowDto findClienteByEmail(String email);
    List<ClienteToShowDto> findClienteByDireccionContainingIgnoreCase(String direccion);
    List<ClienteToShowDto> findClienteByNombreStartingWithIgnoreCase(String nombre);

}
