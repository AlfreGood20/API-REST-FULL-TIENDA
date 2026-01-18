package com.tienda.api_tienda.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tienda.api_tienda.dtos.request.LoginRequest;
import com.tienda.api_tienda.dtos.request.RefreshRequest;
import com.tienda.api_tienda.dtos.response.LoginResponse;
import com.tienda.api_tienda.dtos.response.RefreshResponse;
import com.tienda.api_tienda.exeptions.ExepcionRecursoNoEncontrado;
import com.tienda.api_tienda.exeptions.TokenInvalidoExeption;
import com.tienda.api_tienda.model.RefreshToken;
import com.tienda.api_tienda.model.Usuario;
import com.tienda.api_tienda.repository.RefreshTokenRepo;
import com.tienda.api_tienda.repository.UsuarioRepo;
import com.tienda.api_tienda.utils.UsuarioDetails;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthServ {

    private JwtServ jwtServ;
    private UsuarioRepo usuarioRepo;
    private RefreshTokenRepo refreshTokenRepo;
    private AuthenticationManager authenticationManager;


    // Login donde el usaurio va loguiarse y va obtener tokens
    @SuppressWarnings("null")
    public LoginResponse login(LoginRequest request){

        if(!usuarioRepo.existsByCorreo(request.getCorreo())) throw new UsernameNotFoundException("No existe usuario");

        Usuario usuario = usuarioRepo.findByCorreo(request.getCorreo()).get();

        List<RefreshToken> tokensNotRevocados = refreshTokenRepo.findByUsuarioAndRevocadoFalse(usuario).stream().toList();
        for (RefreshToken token : tokensNotRevocados) {
            token.setRevocado(true);
            refreshTokenRepo.save(token);
        }

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getCorreo(), request.getContrasena())
        );

        String tokenAccess = jwtServ.getTokenAccess((UsuarioDetails) authentication.getPrincipal());
        String tokenRefresh = jwtServ.getTokenRefresh((UsuarioDetails) authentication.getPrincipal());

        RefreshToken refreshToken = RefreshToken.builder()
            .token(tokenRefresh)
            .usuario(usuario)
            .fechaCreacion(LocalDateTime.now())
            .revocado(false)
            .fechaExpiracion(jwtServ.getExpiration(tokenRefresh))
            .build();

        refreshTokenRepo.save(refreshToken);
        
        return new LoginResponse(tokenAccess, tokenRefresh);
    }

    // Aqui va estar refrescando el token de access
    public RefreshResponse refresh(RefreshRequest request){

        // 1. Vereficar si existe token
        RefreshToken refreshToken = refreshTokenRepo.findByToken(request.refreshToken())
            .orElseThrow(() -> new ExepcionRecursoNoEncontrado("Token no encontrado"));

        // 2. Verificar si no esta revovado o expirado
        if(refreshToken.isRevocado() || refreshToken.getFechaExpiracion().isBefore(LocalDateTime.now())){
            throw new TokenInvalidoExeption("Token expirado o revocado");
        }

        // 3. Y aqui paso los primeras cosas, ahora si obtener access token
        Usuario usuario= refreshToken.getUsuario();
        UsuarioDetails usuarioDetails = new UsuarioDetails(usuario);

        final String newAccessToken = jwtServ.getTokenAccess(usuarioDetails);

        return new RefreshResponse(newAccessToken);
    }

    // Aqui va logout -> salir 
    public Map<String, Object> logout(RefreshRequest request){
        RefreshToken refreshToken = refreshTokenRepo.findByToken(request.refreshToken())
            .orElseThrow(() -> new ExepcionRecursoNoEncontrado("Token no encontrado"));

        refreshToken.setRevocado(true);
        refreshTokenRepo.save(refreshToken);

        var response = new HashMap<String, Object>();
        response.put("time", LocalDateTime.now());
        response.put("status", 200);
        response.put("message", "Logout exitoso");

        return response;
    }


}
