package com.tienda.api_tienda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tienda.api_tienda.model.Rol;
import com.tienda.api_tienda.utils.RolEnum;

@Repository
public interface RolRepo extends JpaRepository<Rol, Long>{

    boolean existsByNombre(RolEnum rolEnum);
}