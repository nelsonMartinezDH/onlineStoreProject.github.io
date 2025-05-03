package com.pweb.tiendaonline.services.cliente;

import com.pweb.tiendaonline.dtos.cliente.ClienteToSaveDto;
import com.pweb.tiendaonline.dtos.cliente.ClienteToShowDto;
import com.pweb.tiendaonline.entities.Cliente;
import com.pweb.tiendaonline.exceptions.NotFoundException;
import com.pweb.tiendaonline.mappers.ClienteMapper;
import com.pweb.tiendaonline.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    @Autowired
    public ClienteServiceImpl(ClienteRepository clienteRepository, ClienteMapper clienteMapper){
        this.clienteMapper = clienteMapper;
        this.clienteRepository = clienteRepository;
    }

    @Override
    public ClienteToShowDto SaveCliente(ClienteToSaveDto cliente) {
        Cliente clienteToSave = clienteMapper.clienteToSaveDtoToClienteEntity(cliente);
        Cliente clienteGuardado = clienteRepository.save(clienteToSave);
        return clienteMapper.clienteEntityToclienteToShowDto(clienteGuardado);
    }

    @Override
    public ClienteToShowDto updateClienteById(Long id, ClienteToSaveDto cliente) {
        Optional<Cliente> clienteConsultado = clienteRepository.findById(id);

        if(clienteConsultado.isEmpty())
            throw new NotFoundException("Cliente con ID " + id + " no encontrado");

        Cliente cl = clienteConsultado.get();

        if (cliente.nombre() != null) cl.setNombre(cliente.nombre());
        if (cliente.email() != null) cl.setEmail(cliente.email());
        if (cliente.direccion() != null) cl.setDireccion(cliente.direccion());

        Cliente clienteActualizado = clienteRepository.save(cl);

        return clienteMapper.clienteEntityToclienteToShowDto(clienteActualizado);
    }

    @Override
    public List<ClienteToShowDto> findAllClientes() {

        List<Cliente> clientes = clienteRepository.findAll();

        if(clientes.isEmpty())
            throw new NotFoundException("No se ha encontrado ningún cliente");

        List<ClienteToShowDto> allClientes =  new ArrayList<>();

        clientes.forEach( cliente -> {
            ClienteToShowDto c = clienteMapper.clienteEntityToclienteToShowDto(cliente);
            allClientes.add(c);
        });

        return allClientes;
    }

    @Override
    public ClienteToShowDto findClienteById(Long id) {

        Optional<Cliente> cliente = clienteRepository.findById(id);

        if(cliente.isEmpty())
            throw new NotFoundException("Cliente con ID " + id + " no encontrado");

        return clienteMapper.clienteEntityToclienteToShowDto(cliente.get());
    }

    @Override
    public void deleteClienteById(Long id) {
        Optional<Cliente> clienteAEliminar = clienteRepository.findById(id);

        if(clienteAEliminar.isEmpty())
            throw new NotFoundException("Cliente con ID " + id + " no existe");

        clienteRepository.deleteById(id);
    }

    @Override
    public ClienteToShowDto findClienteByEmail(String email) {

        Optional<Cliente> clienteMatch = clienteRepository.findByEmail(email);

        if(clienteMatch.isEmpty())
            throw new NotFoundException("No se encontró cliente asociado al email");

        return clienteMapper.clienteEntityToclienteToShowDto(clienteMatch.get());
    }

    @Override
    public List<ClienteToShowDto> findClienteByDireccionContainingIgnoreCase(String direccion) {

        List<Cliente> clienteMatch = clienteRepository.findByDireccionContainingIgnoreCase(direccion);

        if(clienteMatch.isEmpty())
            throw new NotFoundException("No se encontraron direcciones que contengan "+ direccion);

        List<ClienteToShowDto> clienteARegresar =  new ArrayList<>();

        clienteMatch.forEach( cliente -> {
            ClienteToShowDto clienteMapeado = clienteMapper.clienteEntityToclienteToShowDto(cliente);
            clienteARegresar.add(clienteMapeado);
        });

        return clienteARegresar;
    }

    @Override
    public List<ClienteToShowDto> findClienteByNombreStartingWithIgnoreCase(String nombre) {
        List<Cliente> clienteMatch = clienteRepository.findByNombreStartingWithIgnoreCase(nombre);

        if(clienteMatch.isEmpty())
            throw new NotFoundException("No se encontraron clientes con nombre " + nombre);

        List<ClienteToShowDto> clienteARegresar =  new ArrayList<>();

        clienteMatch.forEach( cliente -> {
            ClienteToShowDto clienteMapeado = clienteMapper.clienteEntityToclienteToShowDto(cliente);
            clienteARegresar.add(clienteMapeado);
        });

        return clienteARegresar;
    }

}
