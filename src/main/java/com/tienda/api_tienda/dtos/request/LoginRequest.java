package com.tienda.api_tienda.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class LoginRequest {

    @Email(message = "Correo invalido")
    private String correo;

    @NotBlank(message = "Contraseña es obligatorio")
    @Size(max = 250, min = 8, message = "Contraseña debe ser mayor a 8 caracteres o maximo de 250 caracteres")
    private String contrasena;
}