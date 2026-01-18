package com.tienda.api_tienda.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.tienda.api_tienda.dtos.request.ProductoRequest;
import com.tienda.api_tienda.dtos.response.ProductoResponse;
import com.tienda.api_tienda.exeptions.ExepcionRecursoNoEncontrado;
import com.tienda.api_tienda.mapper.ProductoMapper;
import com.tienda.api_tienda.model.Categoria;
import com.tienda.api_tienda.model.Producto;
import com.tienda.api_tienda.repository.CategoriaRepo;
import com.tienda.api_tienda.repository.ProductoRepo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@SuppressWarnings("null")
public class ProductoServ {

    private final ProductoRepo productoRepo;
    private final ProductoMapper mapper;
    private final CategoriaRepo categoriaRepo;

    @Value("${app.upload.dir}")
    private String uploadDir; 

    
    private Categoria getCategoria(long id){
        return categoriaRepo.findById(id)
            .orElseThrow(() -> new ExepcionRecursoNoEncontrado("Categoria no encontrado"));
    }

    private Producto getProducto(long id){
        return productoRepo.findById(id)
            .orElseThrow(() -> new ExepcionRecursoNoEncontrado("Producto con id "+id+" no encontrado"));
    }

    private String getFileName(MultipartFile file){
        try {
            // Crear carpeta si no existe
            Path uploadPath = Paths.get(uploadDir);
            if(!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath); // Crea la carpeta
            }

            // Ruta completa
            Path ruta = uploadPath.resolve(file.getOriginalFilename());

            // Guardar archivo
            Files.copy(file.getInputStream(), ruta);
            return "/uploads/"+file.getOriginalFilename();
        } catch (IOException ex) {}

        return null;
    }


    public ProductoResponse nuevo(ProductoRequest request, MultipartFile imagen){
        Categoria categoria = getCategoria(request.getCategoriaId());

        Producto producto = mapper.dtoToEntity(request,getFileName(imagen), categoria, LocalDateTime.now());
        return mapper.entityToDto(productoRepo.save(producto), categoria.getNombre());
    }

    public ProductoResponse obtenerPorId(long id){
        Producto producto = productoRepo.findById(id)
            .orElseThrow(() -> new ExepcionRecursoNoEncontrado("Producto con id "+id+" no encontrado"));
        
        return mapper.entityToDto(producto, producto.getCategoria().getNombre());
    }

    public List<ProductoResponse> obtenerPorIniciales(String q, int pagina, int tamaño){
        Pageable pegable = PageRequest.of(pagina, tamaño);

        List<ProductoResponse> listaProducto = productoRepo.findByNombreStartingWithIgnoreCase(q, pegable)
            .stream()
            .map(p -> mapper.entityToDto(p, p.getCategoria().getNombre()))
            .collect(Collectors.toList());
        
        return listaProducto;
    }

    public List<ProductoResponse> obtenerPorCategoria(long id, int pagina, int tamaño){
        Pageable pegable = PageRequest.of(pagina, tamaño);

        List<ProductoResponse> listaProducto = productoRepo.findByCategoria_CategoriaId(id, pegable)
            .stream()
            .map(p -> mapper.entityToDto(p, p.getCategoria().getNombre()))
            .collect(Collectors.toList());

        return listaProducto;
    }

    public ProductoResponse actualizar(long id, ProductoRequest request, MultipartFile imagen){
        Producto producto = getProducto(id);

        producto.setNombre(request.getNombre());
        producto.setDescripcion(request.getDescripcion());
        producto.setStock(request.getStock());
        producto.setPrecioUnitario(request.getPrecioUnitario());
        producto.setPrecioCompra(request.getPrecioCompra());
        if(imagen != null && !imagen.isEmpty()){
            producto.setImgUrl(getFileName(imagen));
        }
        producto.setCodigoBarra(request.getCodigoBarra());
        producto.setCategoria(getCategoria(request.getCategoriaId()));
        producto.setFechaActualizacion(LocalDateTime.now());

        return mapper.entityToDto(productoRepo.save(producto), getCategoria(producto.getProductoId()).getNombre());
    }

    public void eliminarPorId(long id){
        productoRepo.delete(getProducto(id));
    }

}