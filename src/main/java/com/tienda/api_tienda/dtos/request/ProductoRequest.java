package com.tienda.api_tienda.dtos.request;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductoRequest {

    @JsonProperty("nombre")
    @NotBlank(message = "Obligatorio")
    private String nombre;

    @JsonProperty("descripcion")
    private String descripcion;

    @JsonProperty("stock")
    @PositiveOrZero(message = "NO debe ser valor negativo")
    private int stock;

    @JsonProperty("precio_unitario")
    @PositiveOrZero(message = "NO debe ser valor negativo")
    private BigDecimal precioUnitario;

    @JsonProperty("precio_compra")
    @PositiveOrZero(message = "NO debe ser valor negativo")
    private BigDecimal precioCompra;

    @JsonProperty("codigo_barra")
    private long codigoBarra;

    @JsonProperty("categoria_id")
    @PositiveOrZero(message = "NO debe ser valor negativo")
    private long categoriaId;
}