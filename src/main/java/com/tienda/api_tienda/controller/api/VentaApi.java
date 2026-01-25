package com.tienda.api_tienda.controller.api;

import org.springframework.http.ResponseEntity;
import com.tienda.api_tienda.dtos.request.VentaRequest;
import com.tienda.api_tienda.dtos.response.VentaResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

public interface VentaApi {

    @Operation(
        summary = "Crea nueva venta",
        description = "Creas nueva venta, y devuelve el resultado de la venta hecha"
    )
    @ApiResponse(
        description = "Venta realizada",
        responseCode = "201",
        content = @Content(
            schema = @Schema(
                implementation = VentaResponse.class
            )
        )
    )
    @SecurityRequirement(name = "Bearer")
    ResponseEntity<VentaResponse> crear(VentaRequest request);
}
