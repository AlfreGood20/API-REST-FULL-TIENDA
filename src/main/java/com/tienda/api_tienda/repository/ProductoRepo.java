package com.tienda.api_tienda.repository;

import com.tienda.api_tienda.model.Producto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepo extends JpaRepository<Producto, Long> {

    Page<Producto> findByNombreStartingWithIgnoreCase(String nombre, Pageable pageable);
    Page<Producto> findByCategoria_CategoriaId(long categoriaId, Pageable pageable);
}