package com.pweb.tiendaonline.services.itemPedido;


import com.pweb.tiendaonline.dtos.ItemPedido.ItemPedidoToSaveDto;
import com.pweb.tiendaonline.dtos.ItemPedido.ItemPedidoToShowDto;

import java.util.List;

public interface ItemPedidoService {

    ItemPedidoToShowDto getItemPedidoById(Long id);
    List<ItemPedidoToShowDto> getAllItemPedidos();
    ItemPedidoToShowDto saveItemPedido(ItemPedidoToSaveDto itemPedidoToSaveDto);
    ItemPedidoToShowDto updateItemPedido(Long itemPedidoId, ItemPedidoToSaveDto itemPedidoToSaveDto);
    List<ItemPedidoToShowDto> findItemPedidoToShowDtoByPedidoId(Long pedidoId);
    List<ItemPedidoToShowDto> findItemPedidoToShowDtoByProductoId(Long productoId);
    void deleteItemPedidoById(Long id);

}
