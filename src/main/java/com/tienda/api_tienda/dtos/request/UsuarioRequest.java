package com.tienda.api_tienda.dtos.request;

import java.util.Set;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UsuarioRequest {

    private String nombre;
    
    @JsonProperty("apellido_paterno")
    private String apellidoPaterno;

    @JsonProperty("apellido_materno")
    private String apellidoMaterno;

    @JsonProperty("numero_telefono")
    private String numeroTelefonico;

    @Email(message = "Formato incorrecto")
    @NotBlank(message = "Correo es obligatorio")
    private String correo;

    @NotBlank(message = "Contraseña es obligatorio")
    @Size(max = 250, min = 8, message = "Contraseña debe ser mayor a 8 caracteres o maximo de 250 caracteres")
    private String contrasena;

    @JsonProperty("role_ids")
    @NotEmpty(message = "Ids de los roles es obligatorio")
    private Set<Long> roles;
}