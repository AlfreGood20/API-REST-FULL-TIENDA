package com.tienda.api_tienda.repository;

import com.tienda.api_tienda.model.DetalleVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleVentaRepo extends JpaRepository<DetalleVenta, Long> {
}
