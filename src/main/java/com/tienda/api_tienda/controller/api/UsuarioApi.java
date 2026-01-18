package com.tienda.api_tienda.controller.api;

import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.tienda.api_tienda.dtos.request.UsuarioRequest;
import com.tienda.api_tienda.dtos.response.UsuarioResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

public interface UsuarioApi {

    @Operation(
        summary = "Creas nuevo empleado",
        description = "Creas uno empleado y devuelve el empleado creado"
    )
    @ApiResponse(
        description = "Empleado creado",
        responseCode = "201",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = UsuarioResponse.class)
        )
    )
    @SecurityRequirement(name = "Bearer")
    ResponseEntity<UsuarioResponse> nuevoEmpleado(UsuarioRequest request);

 
    @Operation(
        summary = "Obtienes empleados",
        description = "Obtienes empleados registrados"
    )
    @ApiResponse(
        description = "Lista de empleados",
        responseCode = "200",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            array = @ArraySchema(schema = @Schema(implementation = UsuarioResponse.class))
        )
    )
    @SecurityRequirement(name = "Bearer")
    ResponseEntity<List<UsuarioResponse>> obtener();
}