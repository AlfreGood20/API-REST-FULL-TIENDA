package com.tienda.api_tienda.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tienda.api_tienda.model.RefreshToken;
import com.tienda.api_tienda.model.Usuario;


@Repository
public interface RefreshTokenRepo extends JpaRepository<RefreshToken, Long>{
    boolean existsByToken(String token);
    Optional<RefreshToken> findByToken(String token);
    Optional<RefreshToken> findByUsuarioAndRevocadoFalse(Usuario usuario);
}