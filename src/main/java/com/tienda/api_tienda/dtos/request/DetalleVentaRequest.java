package com.tienda.api_tienda.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetalleVentaRequest {

    @JsonProperty("producto_id")
    @Positive(message = "Valor debe ser mayor a 0")
    private long productoId;

    @Positive(message = "Valor debe ser mayor a 0")
    private int cantidad;
}