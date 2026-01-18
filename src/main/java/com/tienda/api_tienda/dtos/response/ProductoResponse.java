package com.tienda.api_tienda.dtos.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductoResponse {

    @JsonProperty("producto_id")
    private Long productoId;

    @JsonProperty("nombre")
    private String nombre;

    @JsonProperty("descripcion")
    private String descripcion;

    @JsonProperty("stock")
    private int stock;

    @JsonProperty("precio_unitario")
    private BigDecimal precioUnitario;

    @JsonProperty("precio_compra")
    private BigDecimal precioCompra;

    @JsonProperty("img_url")
    private String imgUrl;

    @JsonProperty("codigo_barra")
    private long codigoBarra;

    @JsonProperty("categoria")
    private String categoria;

    @JsonProperty("fecha_creacion")
    private LocalDateTime fechaCreacion;

    @JsonProperty("fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
}
