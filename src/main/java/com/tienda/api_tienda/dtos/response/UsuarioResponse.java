package com.tienda.api_tienda.dtos.response;

import java.time.LocalDateTime;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tienda.api_tienda.model.Rol;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UsuarioResponse {

    private String nombre;
    
    @JsonProperty("apellido_paterno")
    private String apellidoPaterno;

    @JsonProperty("apellido_materno")
    private String apellidoMaterno;

    @JsonProperty("numero_telefono")
    private String numeroTelefonico;

    private String correo;

    @JsonProperty("fecha_creado")
    private LocalDateTime fechaCreado;

    private Set<Rol> roles;
}
