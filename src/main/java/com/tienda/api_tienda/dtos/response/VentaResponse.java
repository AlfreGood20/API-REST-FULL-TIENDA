package com.tienda.api_tienda.dtos.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tienda.api_tienda.model.Estado;
import com.tienda.api_tienda.model.Metodo;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class VentaResponse {

    @JsonProperty("venta_id")
    private long ventaId;

    private UsuarioResponse usuario;
     
    private ClienteResponse cliente;

    private Metodo metodo;

    private Estado estado;

    private List<DetalleVentaResponse> detalles;

    @JsonProperty("monto_total")
    private BigDecimal montoTotal;

    @JsonProperty("monto_recibido")
    private BigDecimal montoRecibido;

    @JsonProperty("monto_cambio")
    private BigDecimal montoCambio;

    @JsonProperty("fecha_creado")
    private LocalDateTime fechaCreacion;
}