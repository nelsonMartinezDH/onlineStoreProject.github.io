package com.pweb.tiendaonline.services.itemPedido;

import com.pweb.tiendaonline.dtos.ItemPedido.ItemPedidoToSaveDto;
import com.pweb.tiendaonline.dtos.ItemPedido.ItemPedidoToShowDto;
import com.pweb.tiendaonline.entities.ItemPedido;
import com.pweb.tiendaonline.entities.Pedido;
import com.pweb.tiendaonline.entities.Producto;
import com.pweb.tiendaonline.exceptions.NotFoundException;
import com.pweb.tiendaonline.mappers.ItemPedidoMapper;
import com.pweb.tiendaonline.repositories.ItemPedidoRepository;
import com.pweb.tiendaonline.repositories.PedidoRepository;
import com.pweb.tiendaonline.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemPedidoServiceImpl implements  ItemPedidoService {

    ItemPedidoRepository itemPedidoRepository;
    PedidoRepository pedidoRepository;
    ProductoRepository productoRepository;
    ItemPedidoMapper itemPedidoMapper;

    @Autowired
    public ItemPedidoServiceImpl(
            ItemPedidoRepository itemPedidoRepository,
            ItemPedidoMapper itemPedidoMapper,
            PedidoRepository pedidoRepository,
            ProductoRepository productoRepository
    ) {
        this.itemPedidoRepository = itemPedidoRepository;
        this.itemPedidoMapper = itemPedidoMapper;
        this.pedidoRepository = pedidoRepository;
        this.productoRepository = productoRepository;
    }

    @Override
    public ItemPedidoToShowDto getItemPedidoById(Long id) {

        Optional<ItemPedido> itemPedidoConsultado = itemPedidoRepository.findById(id);

        if(itemPedidoConsultado.isEmpty())
            throw new NotFoundException("ItemPedido con ID " + id + " no encontrado");

        return itemPedidoMapper.itemPedidoEntityToItemPedidoToShowDto( itemPedidoConsultado.get() );
    }

    @Override
    public List<ItemPedidoToShowDto> getAllItemPedidos() {

        List<ItemPedido> todosLosItemPedido = itemPedidoRepository.findAll();

        if(todosLosItemPedido.isEmpty())
            throw new NotFoundException("No se encontraron ItemPedidos");

        List<ItemPedidoToShowDto> itemPedidoARegresar = new ArrayList<>();

        todosLosItemPedido.forEach(itemPedido -> {
            itemPedidoARegresar.add(itemPedidoMapper.itemPedidoEntityToItemPedidoToShowDto(itemPedido));
        });

        return itemPedidoARegresar;
    }

    @Override
    public ItemPedidoToShowDto saveItemPedido(ItemPedidoToSaveDto itemPedidoToSaveDto) {
        ItemPedido itemPedido = itemPedidoMapper.itemPedidoToSaveDtoToItemPedidoEntity(itemPedidoToSaveDto);

        Long pedidoId = itemPedidoToSaveDto.pedido().id();
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new NotFoundException("Pedido con ID " + pedidoId + " no encontrado"));

        Long productoId = itemPedidoToSaveDto.producto().id();
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new NotFoundException("Producto con ID " + productoId + " no encontrado"));

        itemPedido.setPedido(pedido);
        itemPedido.setProducto(producto);

        ItemPedido itemPedidoGuardado = itemPedidoRepository.save(itemPedido);

        return itemPedidoMapper.itemPedidoEntityToItemPedidoToShowDto(itemPedidoGuardado);
    }

    @Override
    public ItemPedidoToShowDto updateItemPedido(Long itemPedidoId, ItemPedidoToSaveDto itemPedidoToSaveDto) {

        Optional<ItemPedido> itemPedidoConsultado = itemPedidoRepository.findById(itemPedidoId);

        if(itemPedidoConsultado.isEmpty())
            throw new NotFoundException("ItemPedido con ID " + itemPedidoId + " no encontrado");

        ItemPedido itemPedido = itemPedidoConsultado.get();

        if (itemPedidoToSaveDto.cantidad() != null) itemPedido.setCantidad(itemPedidoToSaveDto.cantidad());
        if (itemPedidoToSaveDto.precioUnitario() != null) itemPedido.setPrecioUnitario(itemPedidoToSaveDto.precioUnitario());

        if(itemPedidoToSaveDto.pedido() != null && itemPedidoToSaveDto.pedido().id() != null){
            Long pedidoId = itemPedidoToSaveDto.pedido().id();
            Optional<Pedido> pedido = pedidoRepository.findById(pedidoId);
            pedido.ifPresent(itemPedido::setPedido);
        }

        if(itemPedidoToSaveDto.producto() != null && itemPedidoToSaveDto.producto().id() != null){
            Long productoId = itemPedidoToSaveDto.producto().id();
            Optional<Producto> producto = productoRepository.findById(productoId);
            producto.ifPresent(itemPedido::setProducto);
        }

        ItemPedido itemPedidoActualizado = itemPedidoRepository.save(itemPedido);

        return itemPedidoMapper.itemPedidoEntityToItemPedidoToShowDto(itemPedidoActualizado);
    }

    @Override
    public List<ItemPedidoToShowDto> findItemPedidoToShowDtoByPedidoId(Long pedidoId) {

        List<ItemPedido> itemPedido = itemPedidoRepository.findByPedidoId(pedidoId);

        if(itemPedido.isEmpty())
            throw new NotFoundException("ItemPedido con Pedido ID " + pedidoId + " no encontrado");

        List<ItemPedidoToShowDto> itemPedidoARegresar = new ArrayList<>();

        itemPedido.forEach(item -> {
            itemPedidoARegresar.add(itemPedidoMapper.itemPedidoEntityToItemPedidoToShowDto(item));
        });

        return itemPedidoARegresar;

    }

    @Override
    public List<ItemPedidoToShowDto> findItemPedidoToShowDtoByProductoId(Long productoId) {

        List<ItemPedido> itemPedido = itemPedidoRepository.findByProductoId(productoId);

        if(itemPedido.isEmpty())
            throw new NotFoundException("ItemPedido con Producto ID " + productoId + " no encontrado");

        List<ItemPedidoToShowDto> itemPedidoARegresar = new ArrayList<>();

        itemPedido.forEach(item -> {
            itemPedidoARegresar.add(itemPedidoMapper.itemPedidoEntityToItemPedidoToShowDto(item));
        });

        return itemPedidoARegresar;

    }

    @Override
    public void deleteItemPedidoById(Long id) {

        Optional<ItemPedido> itemPedidoConsultado = itemPedidoRepository.findById(id);

        if(itemPedidoConsultado.isEmpty())
            throw new NotFoundException("ItemPedido con ID " + id + " no encontrado");

        itemPedidoRepository.deleteById(id);

    }

}
