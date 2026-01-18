package com.tienda.api_tienda.mapper;

import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.tienda.api_tienda.dtos.request.UsuarioRequest;
import com.tienda.api_tienda.dtos.response.UsuarioResponse;
import com.tienda.api_tienda.model.Rol;
import com.tienda.api_tienda.model.Usuario;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    // DTO -> ENTITY
    @Mapping(target = "usuarioId", ignore = true)
    @Mapping(target = "fechaCreado", ignore = true)
    @Mapping(target = "roles", source = "mapping")
    Usuario dtoToEntity(UsuarioRequest request, Set<Rol> mapping);

    // ENTITY -> DTO
    UsuarioResponse entityToDto(Usuario entity);
}
