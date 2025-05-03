package com.pweb.tiendaonline.mappers;

import com.pweb.tiendaonline.dtos.producto.ProductoDto;
import com.pweb.tiendaonline.dtos.producto.ProductoToSaveDto;
import com.pweb.tiendaonline.dtos.producto.ProductoToShowDto;
import com.pweb.tiendaonline.entities.Producto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductoMapper {

    Producto productoDtoToProductoEntity(ProductoDto productoDto);

    ProductoDto productoEntityToProductoDto(Producto producto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "itemsPedidos", expression = "java(new ArrayList<>())")
    Producto productoToSaveDtoToProductoEntity(ProductoToSaveDto productoToSaveDto);

    ProductoToSaveDto productoEntityToProductoToSaveDto(Producto producto);

    @Mapping(target = "itemsPedidos", ignore = true)
    Producto productoToShowDtoToProductoEntity(ProductoToShowDto productoToShowDto);

    ProductoToShowDto productoEntityToProductoToShowDto(Producto producto);

}
