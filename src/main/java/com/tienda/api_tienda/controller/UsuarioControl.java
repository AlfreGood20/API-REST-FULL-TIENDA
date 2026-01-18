package com.tienda.api_tienda.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tienda.api_tienda.controller.api.UsuarioApi;
import com.tienda.api_tienda.dtos.request.UsuarioRequest;
import com.tienda.api_tienda.dtos.response.UsuarioResponse;
import com.tienda.api_tienda.service.UsuarioServ;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Tag(name = "Usuarios", description = "Operaciones de usuario")
public class UsuarioControl implements UsuarioApi{

    private final UsuarioServ service;

    @Override
    @PostMapping("/public/usuario")
    public ResponseEntity<UsuarioResponse> nuevoEmpleado(@Valid @RequestBody UsuarioRequest request) {
        return new ResponseEntity<UsuarioResponse>(service.crear(request), HttpStatus.CREATED);
    }

    @Override
    @GetMapping("/admin/usuarios")
    public ResponseEntity<List<UsuarioResponse>> obtener() {
        return ResponseEntity.ok(service.obtener());
    }
}