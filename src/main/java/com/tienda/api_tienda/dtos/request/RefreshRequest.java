package com.tienda.api_tienda.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;

public record RefreshRequest(

    @JsonProperty("refresh_token")
    @NotBlank(message = "Refresh token es obligatorio")
    String refreshToken
) {
}