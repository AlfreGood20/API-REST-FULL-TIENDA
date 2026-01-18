package com.tienda.api_tienda.repository;

import com.tienda.api_tienda.model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoRepo extends JpaRepository<Estado, Long> {
}
