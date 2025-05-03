package com.pweb.tiendaonline.mappers;

import com.pweb.tiendaonline.dtos.cliente.ClienteDto;
import com.pweb.tiendaonline.dtos.cliente.ClienteToSaveDto;
import com.pweb.tiendaonline.dtos.cliente.ClienteToShowDto;
import com.pweb.tiendaonline.entities.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    Cliente clienteDtoToClienteEntity(ClienteDto clienteDto);

    @Mapping(target = "pedidos", expression = "java(new ArrayList<>())")
    @Mapping(target = "pedidos.cliente", ignore = true)
    ClienteDto clienteEntityToClienteDto(Cliente cliente);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pedidos", expression = "java(new ArrayList<>())")
    Cliente clienteToSaveDtoToClienteEntity(ClienteToSaveDto clienteToSaveDto);

    ClienteToSaveDto clienteEntityToClienteToSaveDto(Cliente cliente);

    @Mapping(target = "pedidos", ignore = true)
    Cliente clienteToShowDtoToClienteEntity( ClienteToShowDto clienteToShowDto );

    ClienteToShowDto clienteEntityToclienteToShowDto( Cliente cliente );

}
