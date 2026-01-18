package com.tienda.api_tienda.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tienda.api_tienda.controller.api.AuthApi;
import com.tienda.api_tienda.dtos.request.LoginRequest;
import com.tienda.api_tienda.dtos.request.RefreshRequest;
import com.tienda.api_tienda.dtos.response.LoginResponse;
import com.tienda.api_tienda.dtos.response.RefreshResponse;
import com.tienda.api_tienda.service.AuthServ;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/public/auth")
@AllArgsConstructor
@Tag(name = "Autenticacion", description = "Operaciones de auth")
public class AuthControl implements AuthApi{

    private final AuthServ service;

    @Override
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> autenticarse(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(service.login(request));
    }

    @Override
    @PostMapping("/refresh")
    public ResponseEntity<RefreshResponse> refrescarTokensAccess(@Valid @RequestBody RefreshRequest request) {
        return ResponseEntity.ok(service.refresh(request));
    }

    @Override
    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> desautenticarse(@Valid @RequestBody RefreshRequest request) {
        return ResponseEntity.ok(service.logout(request));
    }
}