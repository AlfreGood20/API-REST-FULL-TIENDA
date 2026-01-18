package com.tienda.api_tienda.repository;

import com.tienda.api_tienda.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepo extends JpaRepository<Categoria, Long> {
    boolean existsByNombre(String nombre);
}