package com.tienda.api_tienda.controller.api;

import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.tienda.api_tienda.dtos.request.LoginRequest;
import com.tienda.api_tienda.dtos.request.RefreshRequest;
import com.tienda.api_tienda.dtos.response.LoginResponse;
import com.tienda.api_tienda.dtos.response.RefreshResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;


public interface AuthApi {

    @Operation(
        summary = "Autenticacion",
        description = "Te autentica, obtienes token de refresco y de accesso"
    )
    @ApiResponse(
        description = "Token de refreco y acceso",
        responseCode = "200",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = LoginResponse.class)
        )
    )
    ResponseEntity<LoginResponse> autenticarse(LoginRequest request);

   
    @Operation(
        summary = "Refrescar token de acceso",
        description = "Refresca el token de acceso, usando el token de refresco"
    )
    @ApiResponse(
        description = "Token de acceso",
        responseCode = "200",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = RefreshResponse.class)
        )
    )
    ResponseEntity<RefreshResponse> refrescarTokensAccess(RefreshRequest request);
        

    @Operation(
        summary = "Desautenticarse",
        description = "Te desautenticas usando el token de refreco"
    )
    @ApiResponse(
        description = "Mensaje de logout exitoso",
        responseCode = "200"
    )
    ResponseEntity<Map<String, Object>> desautenticarse(RefreshRequest request);
        
}
