package com.tienda.api_tienda.repository;

import com.tienda.api_tienda.model.Metodo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetodoRepo extends JpaRepository<Metodo, Long> {
}
