package com.tienda.api_tienda.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteResponse {

    @JsonProperty("cliente_id")
    private long clienteId;

    private String nombre;

    private String apellidos;

    @JsonProperty("numero_telefono")
    private String numeroTelefono;

    private String correo;
}