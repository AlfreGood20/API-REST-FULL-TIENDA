package com.tienda.api_tienda.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RefreshResponse(
    @JsonProperty("access_token")
    String accessToken
) {
}