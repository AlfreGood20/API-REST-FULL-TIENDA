package com.tienda.api_tienda.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.tienda.api_tienda.service.ProductoServ;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import com.tienda.api_tienda.controller.api.ProductoApi;
import com.tienda.api_tienda.dtos.request.ProductoRequest;
import com.tienda.api_tienda.dtos.response.ProductoResponse;
import lombok.AllArgsConstructor;


@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Tag(name = "Productos", description = "Operaciones relacionadas con productos")
public class ProductoControl implements ProductoApi {
    
    private final ProductoServ service;

    @Override
    @PostMapping(value = "/admin/producto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductoResponse> crear(@Valid @RequestPart(name = "producto") ProductoRequest request, @RequestPart(name = "file") MultipartFile file){
        return new ResponseEntity<ProductoResponse>(service.nuevo(request, file), HttpStatus.CREATED);
    }

    @Override
    @GetMapping("/admin/producto/{id}")
    public ResponseEntity<ProductoResponse> obtenerPorId(@PathVariable long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @Override
    @GetMapping("/public/productos/buscar")
    public ResponseEntity<List<ProductoResponse>> obtenerPorNombre(
            @RequestParam(defaultValue = "A", required = false) String q,
            @RequestParam(defaultValue = "0", required = true) int pagina,
            @RequestParam(defaultValue = "15", required = true) int tamaño) {
                
        return ResponseEntity.ok(service.obtenerPorIniciales(q, pagina, tamaño));
    }

    @Override
    @GetMapping("/public/productos/categoria")
    public ResponseEntity<List<ProductoResponse>> obtenerPorCategoria(
            @RequestParam(defaultValue = "0") long id,
            @RequestParam(defaultValue = "0", required = true) int pagina, 
            @RequestParam(defaultValue = "15", required = true) int tamaño) {

        return ResponseEntity.ok(service.obtenerPorCategoria(id, pagina, tamaño));
    }

    @Override
    @PutMapping(value = "/admin/producto/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductoResponse> actualizarPorId(
            @PathVariable(required = true) long id,
            @RequestPart(required = true, name = "producto") ProductoRequest request,
            @RequestPart(required = false, name = "file") MultipartFile file) {

        return ResponseEntity.ok(service.actualizar(id, request, file));
    }

    @Override
    @GetMapping("/admin/productos/mas-vendidos")
    public ResponseEntity<List<ProductoResponse>> obtenerMasVendidos(
            @RequestParam int pagina,
            @RequestParam int tamano) {
        return ResponseEntity.ok(null);
    }

    @Override
    @DeleteMapping("/admin/producto/{id}")
    public ResponseEntity<Void> eliminarPorId(long id) {
        service.eliminarPorId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    @GetMapping("/public/productos")
    public ResponseEntity<List<ProductoResponse>> obtenerProductosGlobal(@RequestParam int pagina,@RequestParam int tamano) {
        return ResponseEntity.ok(service.obtenerProductos(pagina, tamano));
    }

}