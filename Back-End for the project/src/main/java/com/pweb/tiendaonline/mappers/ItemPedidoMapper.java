package com.pweb.tiendaonline.mappers;

import com.pweb.tiendaonline.dtos.ItemPedido.ItemPedidoDto;
import com.pweb.tiendaonline.dtos.ItemPedido.ItemPedidoToSaveDto;
import com.pweb.tiendaonline.dtos.ItemPedido.ItemPedidoToShowDto;
import com.pweb.tiendaonline.entities.ItemPedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ItemPedidoMapper {

    ItemPedido itemPedidoDtoToItemPedidoEntity(ItemPedidoDto itemPedidoDto);

    ItemPedidoDto itemPedidoEntityToItemPedidoDto(ItemPedido itemPedido);

    @Mapping(target = "id", ignore = true)
    ItemPedido itemPedidoToSaveDtoToItemPedidoEntity(ItemPedidoToSaveDto itemPedidoToSaveDto);

    ItemPedidoToSaveDto itemPedidoEntityToItemPedidoToSaveDto(ItemPedido itemPedido);

    ItemPedido itemPedidoToShowDtoToItemPedidoEntity(ItemPedidoToShowDto itemPedidoToShowDto);

    ItemPedidoToShowDto itemPedidoEntityToItemPedidoToShowDto(ItemPedido itemPedido);

}
