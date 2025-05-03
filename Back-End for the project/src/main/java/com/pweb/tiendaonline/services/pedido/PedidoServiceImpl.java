package com.pweb.tiendaonline.services.pedido;

import com.pweb.tiendaonline.dtos.pedido.PedidoToSaveDto;
import com.pweb.tiendaonline.dtos.pedido.PedidoToShowDto;
import com.pweb.tiendaonline.entities.EstadoPedido;
import com.pweb.tiendaonline.entities.Pedido;
import com.pweb.tiendaonline.entities.Cliente;
import com.pweb.tiendaonline.exceptions.NotFoundException;
import com.pweb.tiendaonline.mappers.PedidoMapper;
import com.pweb.tiendaonline.repositories.ClienteRepository;
import com.pweb.tiendaonline.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoServiceImpl implements PedidoService {

    PedidoRepository pedidoRepository;
    PedidoMapper pedidoMapper;
    ClienteRepository clienteRepository;

    @Autowired
    public PedidoServiceImpl(PedidoRepository pedidoRepository, PedidoMapper pedidoMapper, ClienteRepository clienteRepository) {
        this.pedidoMapper = pedidoMapper;
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
    }

    @Override
    public PedidoToShowDto savePedido(PedidoToSaveDto pedidoToSave) {

        Pedido pedidoToSaveEntity = pedidoMapper.pedidoToSaveDtoToPedido(pedidoToSave);

        Optional<Cliente> cliente = clienteRepository.findById(pedidoToSave.cliente().id());

        if(cliente.isEmpty())
            throw new NotFoundException("Cliente no encontrado");

        pedidoToSaveEntity.setCliente(cliente.get());

        System.out.println(pedidoToSaveEntity);

        Pedido pedidoGuardado = pedidoRepository.save(pedidoToSaveEntity);

        System.out.println(pedidoGuardado);;

        return pedidoMapper.pedidoEntityToPedidoToShow(pedidoGuardado);
    }

    @Override
    public PedidoToShowDto updatePedidoById(Long id, PedidoToSaveDto pedidoToUpdate) {

        Optional<Pedido> pedidoExistente = pedidoRepository.findById(id);

        if(pedidoExistente.isEmpty())
            throw new NotFoundException("Pedido no encontrado");

        if (pedidoToUpdate.fechaPedido() != null)
            pedidoExistente.get().setFechaPedido(pedidoToUpdate.fechaPedido());

        if (pedidoToUpdate.status() != null)
            pedidoExistente.get().setStatus(pedidoToUpdate.status());

        Pedido pedidoActualizado = pedidoRepository.save(pedidoExistente.get());

        return pedidoMapper.pedidoEntityToPedidoToShow(pedidoActualizado);
    }

    @Override
    public PedidoToShowDto findPedidoById(Long id) {

        Optional<Pedido> pedido = pedidoRepository.findById(id);

        if(pedido.isEmpty())
            throw new NotFoundException("Pedido con ID " + id + " no encontrado");

        return pedidoMapper.pedidoEntityToPedidoToShow(pedido.get());
    }

    @Override
    public List<PedidoToShowDto> findAllPedidos() {

        List<Pedido> pedidos = pedidoRepository.findAll();

        if(pedidos.isEmpty())
            throw new NotFoundException("No se ha encontrado pedidos");

        List<PedidoToShowDto> allPedidos = new ArrayList<>();

        pedidos.forEach( pedido -> {
            PedidoToShowDto p = pedidoMapper.pedidoEntityToPedidoToShow(pedido);
            allPedidos.add(p);
        });

        return allPedidos;
    }

    @Override
    public void deletePedidoById(Long id) {
        Optional<Pedido> pedidoAEliminar = pedidoRepository.findById(id);

        if(pedidoAEliminar.isEmpty())
            throw new NotFoundException("Pedid con ID " + id + " no existe");

        pedidoRepository.deleteById(id);
    }

    @Override
    public List<PedidoToShowDto> findPedidosByFechaPedidoBetween(LocalDateTime startDate, LocalDateTime endDate) {
        List<Pedido> pedidosMatch = pedidoRepository.findByFechaPedidoBetween(startDate, endDate);

        if(pedidosMatch.isEmpty())
            throw new NotFoundException("No se encontró pedidos dentro esas fechas");

        List<PedidoToShowDto> pedidosARegresar = new ArrayList<>();

        pedidosMatch.forEach( pedido -> {
            PedidoToShowDto pedidoMappeado = pedidoMapper.pedidoEntityToPedidoToShow(pedido);
            pedidosARegresar.add(pedidoMappeado);
        });

        return pedidosARegresar;
    }

    @Override
    public List<PedidoToShowDto> findPedidosByClienteIdAndStatus(Long cliente_id, EstadoPedido status) {

        List<Pedido> pedidosMatch = pedidoRepository.findByClienteIdAndStatus(cliente_id, status);

        if(pedidosMatch.isEmpty())
            throw new NotFoundException("No se encontró ningún pedido con ese id y ese status");

        List<PedidoToShowDto> pedidosARegresar = new ArrayList<>();

        pedidosMatch.forEach( pedido -> {
            PedidoToShowDto pedidoMappeado = pedidoMapper.pedidoEntityToPedidoToShow(pedido);
            pedidosARegresar.add(pedidoMappeado);
        });

        return pedidosARegresar;
    }

    @Override
    public List<PedidoToShowDto> findPedidosByClienteIdWithItemsFetch(Long cliente_id) {

        List<Pedido> pedidosMatch = pedidoRepository.findByClienteIdWithItemsFetch(cliente_id);

        if(pedidosMatch.isEmpty())
            throw new NotFoundException("No se encontró ningún pedido que haga match");

        List<PedidoToShowDto> pedidosARegresar = new ArrayList<>();

        pedidosMatch.forEach( pedido -> {
            PedidoToShowDto pedidoMappeado = pedidoMapper.pedidoEntityToPedidoToShow(pedido);
            pedidosARegresar.add(pedidoMappeado);
        });

        return pedidosARegresar;
    }

}
