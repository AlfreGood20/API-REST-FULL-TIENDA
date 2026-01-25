package com.tienda.api_tienda.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tienda.api_tienda.controller.api.VentaApi;
import com.tienda.api_tienda.dtos.request.VentaRequest;
import com.tienda.api_tienda.dtos.response.VentaResponse;
import com.tienda.api_tienda.service.VentaServ;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class VentaControl implements VentaApi{

    private final VentaServ service;

    @Override
    @PostMapping("/admin/venta")
    public ResponseEntity<VentaResponse> crear(@RequestBody VentaRequest request) {
        System.out.println("Usuario ID recibido en controlador: " + request.getUsuarioId());
        return new ResponseEntity<VentaResponse>(service.nueva(request), HttpStatus.CREATED);
    }
}