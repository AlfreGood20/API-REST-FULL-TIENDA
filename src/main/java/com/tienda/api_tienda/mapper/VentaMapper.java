package com.tienda.api_tienda.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.tienda.api_tienda.dtos.response.ClienteResponse;
import com.tienda.api_tienda.dtos.response.DetalleVentaResponse;
import com.tienda.api_tienda.dtos.response.UsuarioResponse;
import com.tienda.api_tienda.dtos.response.VentaResponse;
import com.tienda.api_tienda.model.Venta;

@Mapper(componentModel = "spring")
public interface VentaMapper {

    // ENTITY -> DTO
    @Mapping(target = "usuario", source = "usuario")
    @Mapping(target = "cliente", source = "cliente")
    @Mapping(target = "metodo", source = "venta.metodoId")
    @Mapping(target = "estado", source = "venta.estadoId")
    @Mapping(target = "detalles", source = "detalles")
    VentaResponse entityToDto(Venta venta, UsuarioResponse usuario, ClienteResponse cliente, List<DetalleVentaResponse> detalles);
}
