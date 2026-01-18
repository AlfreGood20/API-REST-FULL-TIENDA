package com.tienda.api_tienda.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tienda.api_tienda.model.Usuario;

@Repository
public interface UsuarioRepo extends JpaRepository<Usuario,Long>{

    Optional<Usuario> findByCorreo(String correo);
    boolean existsByCorreo(String correo);
}