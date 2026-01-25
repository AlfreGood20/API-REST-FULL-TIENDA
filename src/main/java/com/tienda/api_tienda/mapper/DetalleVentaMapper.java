package com.tienda.api_tienda.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.tienda.api_tienda.dtos.response.DetalleVentaResponse;
import com.tienda.api_tienda.dtos.response.ProductoResponse;
import com.tienda.api_tienda.model.DetalleVenta;

@Mapper(componentModel = "spring")
public interface DetalleVentaMapper {

    @Mapping(target = "producto", source = "producto")
    DetalleVentaResponse entityToDto(DetalleVenta detalle, ProductoResponse producto);
}
