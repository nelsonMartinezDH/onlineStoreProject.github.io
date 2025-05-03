package com.pweb.tiendaonline.services.detalleEnvio;

import com.pweb.tiendaonline.dtos.DetalleEnvio.DetalleEnvioToSaveDto;
import com.pweb.tiendaonline.dtos.DetalleEnvio.DetalleEnvioToShowDto;
import com.pweb.tiendaonline.entities.DetalleEnvio;
import com.pweb.tiendaonline.entities.Pedido;
import com.pweb.tiendaonline.exceptions.NotFoundException;
import com.pweb.tiendaonline.mappers.DetalleEnvioMapper;
import com.pweb.tiendaonline.repositories.DetalleEnvioRepository;
import com.pweb.tiendaonline.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DetalleEnvioServiceImpl implements DetalleEnvioService {

    DetalleEnvioMapper detalleEnvioMapper;
    DetalleEnvioRepository detalleEnvioRepository;
    PedidoRepository pedidoRepository;

    @Autowired
    public DetalleEnvioServiceImpl(DetalleEnvioMapper detalleEnvioMapper, DetalleEnvioRepository detalleEnvioRepository, PedidoRepository pedidoRepository){
        this.detalleEnvioMapper = detalleEnvioMapper;
        this.detalleEnvioRepository = detalleEnvioRepository;
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    public DetalleEnvioToShowDto getDetalleEnvioById(Long id) {

        Optional<DetalleEnvio> detalleEnvio = detalleEnvioRepository.findById(id);

        if(detalleEnvio.isEmpty())
            throw new NotFoundException("No se ha encontrado ningún detalle de envio con el id: " + id);

        return detalleEnvioMapper.detalleEnvioEntityToDetalleEnvioToShowDto(detalleEnvio.get());
    }

    @Override
    public List<DetalleEnvioToShowDto> getAllDetalleEnvios() {

        List<DetalleEnvio> detallesEnvio = detalleEnvioRepository.findAll();

        if(detallesEnvio.isEmpty())
            throw new NotFoundException("No se ha encontrado ningún detalle de envío");

        List<DetalleEnvioToShowDto> detalle = new ArrayList<>();

        detallesEnvio.forEach( d -> {
            DetalleEnvioToShowDto d2 = detalleEnvioMapper.detalleEnvioEntityToDetalleEnvioToShowDto(d);
            detalle.add(d2);
        });

        return detalle;
    }

    @Override
    public DetalleEnvioToShowDto getDetalleEnvioByPedidoId(Long pedido_id) {

        Optional<DetalleEnvio> detalle = detalleEnvioRepository.findByPedidoId(pedido_id);

        if(detalle.isEmpty())
            throw new NotFoundException("No se ha encontrado el detalle de envio para ese id de pedido");

        return detalleEnvioMapper.detalleEnvioEntityToDetalleEnvioToShowDto(detalle.get());
    }

    @Override
    public List<DetalleEnvioToShowDto> getDetalleEnvioByTransportadora(String transportadora) {

        List<DetalleEnvio> detalles = detalleEnvioRepository.findByTransportadoraContainingIgnoreCase(transportadora);

        if(detalles.isEmpty())
            throw new NotFoundException("No se ha encontrado el detalle de envio para la transportadora " + transportadora);

        List<DetalleEnvioToShowDto> detalle = new ArrayList<>();

        detalles.forEach( d -> {
            DetalleEnvioToShowDto d2 = detalleEnvioMapper.detalleEnvioEntityToDetalleEnvioToShowDto(d);
            detalle.add(d2);
        });

        return detalle;
    }

    @Override
    public DetalleEnvioToShowDto saveDetalleEnvio(DetalleEnvioToSaveDto detalleEnvioToSaveDto) {
        DetalleEnvio detalleEnvio = detalleEnvioMapper.detalleEnvioToSaveDtoToDetalleEnvioEntity(detalleEnvioToSaveDto);

        Optional<Pedido> pedido = pedidoRepository.findById(detalleEnvioToSaveDto.pedido().id());
        if(  pedido.isEmpty() )
            throw new NotFoundException("No se ha encontrado el pedido con ese id");

        detalleEnvio.setPedido(pedido.get());

        DetalleEnvio detalleGuardado = detalleEnvioRepository.save(detalleEnvio);
        return detalleEnvioMapper.detalleEnvioEntityToDetalleEnvioToShowDto(detalleGuardado);
    }

    @Override
    public DetalleEnvioToShowDto updateDetalleEnvioById(Long id, DetalleEnvioToSaveDto detalleEnvioToSaveDto) {

        Optional<DetalleEnvio> detalleEnvio = detalleEnvioRepository.findById(id);

        if(detalleEnvio.isEmpty())
            throw new NotFoundException("No se ha encontrado el detalle de envio con el id " + id);

        if(detalleEnvioToSaveDto.numeroGuia() != null )
            detalleEnvio.get().setNumeroGuia(detalleEnvioToSaveDto.numeroGuia());

        if(detalleEnvioToSaveDto.direccion() != null)
            detalleEnvio.get().setDireccion(detalleEnvioToSaveDto.direccion());

        if(detalleEnvioToSaveDto.transportadora() != null)
            detalleEnvio.get().setTransportadora(detalleEnvioToSaveDto.transportadora());

        DetalleEnvio detalleGuardado = detalleEnvioRepository.save(detalleEnvio.get());

        return detalleEnvioMapper.detalleEnvioEntityToDetalleEnvioToShowDto(detalleGuardado);
    }

    @Override
    public void deleteDetalleEnvioById(Long id) {

        Optional<DetalleEnvio> detalleEnvio = detalleEnvioRepository.findById(id);

        if(detalleEnvio.isEmpty())
            throw new NotFoundException("No se ha encontrado el detalle de envio con ese id " + id);

        detalleEnvioRepository.deleteById(id);

    }

}
