package com.tienda.api_tienda.dtos.request;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
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
public class VentaRequest {

    @JsonProperty("usuario_id")
    @Positive(message = "valor debe ser mayor a 0")
    private long usuarioId;
    
    @JsonProperty("cliente_id")
    private long clienteId;

    @JsonProperty("metodo_id")
    @Positive(message = "valor debe ser mayor a 0")
    private long metodoId;

    @JsonProperty("monto_recibido")
    @PositiveOrZero(message = "valor debe ser positivo")
    private BigDecimal montoRecibido;

    @NotEmpty(message = "No vacio, debe contener detalles")
    private List<DetalleVentaRequest> detalles;
}