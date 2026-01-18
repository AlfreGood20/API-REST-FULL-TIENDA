package com.tienda.api_tienda.mapper;

import java.time.LocalDateTime;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.tienda.api_tienda.dtos.request.ProductoRequest;
import com.tienda.api_tienda.dtos.response.ProductoResponse;
import com.tienda.api_tienda.model.Categoria;
import com.tienda.api_tienda.model.Producto;

@Mapper(componentModel = "spring")
public interface ProductoMapper {

    // DTO -> ENTITY
    @Mapping(target = "productoId", ignore = true)
    @Mapping(target = "imgUrl", source = "imgUrl")
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaActualizacion", source = "fechaActualizacion")
    @Mapping(target = "categoria", source = "categoria")
    @Mapping(target = "nombre", source = "request.nombre")
    @Mapping(target = "descripcion", source = "request.descripcion")
    Producto dtoToEntity(ProductoRequest request, String imgUrl,Categoria categoria ,LocalDateTime fechaActualizacion);

    // ENTITY -> DTO
    @Mapping(target = "categoria", source = "categoria")
    ProductoResponse entityToDto(Producto response, String categoria);
}
