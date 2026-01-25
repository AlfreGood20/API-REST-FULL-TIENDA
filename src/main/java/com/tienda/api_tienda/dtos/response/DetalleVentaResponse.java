package com.tienda.api_tienda.dtos.response;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetalleVentaResponse {

    private ProductoResponse producto;
    private int cantidad;
}