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
import com.tienda.api_tienda.dtos.response.LoginResponse;
import com.tienda.api_tienda.dtos.response.RefreshResponse;
import com.tienda.api_tienda.exeptions.ExepcionRecursoNoEncontrado;
import com.tienda.api_tienda.exeptions.TokenInvalidoExeption;
import com.tienda.api_tienda.model.RefreshToken;
import com.tienda.api_tienda.model.Usuario;
import com.tienda.api_tienda.repository.RefreshTokenRepo;
import com.tienda.api_tienda.repository.UsuarioRepo;
import com.tienda.api_tienda.utils.UsuarioDetails;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.var;

@Service
@AllArgsConstructor
public class AuthServ {

    private JwtServ jwtServ;
    private UsuarioRepo usuarioRepo;
    private RefreshTokenRepo refreshTokenRepo;
    private AuthenticationManager authenticationManager;


    // Login donde el usaurio va loguiarse y va obtener tokens
    @SuppressWarnings("null")
    public LoginResponse login(LoginRequest request, HttpServletResponse response){

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

        Cookie refreshCookie = new Cookie("refreshToken", tokenRefresh);
        refreshCookie.setHttpOnly(true);
        refreshCookie.setPath("/"); 
        refreshCookie.setMaxAge(7 * 24 * 60 * 60); // Maxima de duracion
        response.addCookie(refreshCookie);
        
        return new LoginResponse(tokenAccess);
    }

    private String getTokenRefresh(HttpServletRequest request){
        if(request.getCookies() == null){
            throw new ExepcionRecursoNoEncontrado("Refresh token no encontrado");
        }

        Cookie[] cookies = request.getCookies();
        String token = null;

        for(Cookie cookie : cookies){
            if("refreshToken".equals(cookie.getName())){
                token = cookie.getValue();
                break;
            }
        }

        return token;
    }

    // Aqui va estar refrescando el token de access
    public RefreshResponse refresh(HttpServletRequest request){
        
        String token = getTokenRefresh(request);
        if(token == null){
            throw new ExepcionRecursoNoEncontrado("Refresh token no encontrado");
        }

        // 1. Vereficar si existe token en base de datos
        RefreshToken refreshToken = refreshTokenRepo.findByToken(token)
            .orElseThrow(() -> new ExepcionRecursoNoEncontrado("Token no encontrado"));

        // 2. Verificar si no esta revovado o expirado
        if(refreshToken.isRevocado() || refreshToken.getFechaExpiracion().isBefore(LocalDateTime.now())){
            throw new TokenInvalidoExeption("Token expirado o revocado");
        }

        // 3. obtenemos usuario registrado
        Usuario usuario= refreshToken.getUsuario();
        UsuarioDetails usuarioDetails = new UsuarioDetails(usuario);

        // 4. Creamos token acces con jwtServ
        final String newAccessToken = jwtServ.getTokenAccess(usuarioDetails);

        return new RefreshResponse(newAccessToken);
    }

    // Aqui va logout -> salir 
    public Map<String, Object> logout(HttpServletRequest request){
        String token = getTokenRefresh(request);

        RefreshToken refreshToken = refreshTokenRepo.findByToken(token)
            .orElseThrow(() -> new ExepcionRecursoNoEncontrado("Token no encontrado"));

        if(refreshToken.isRevocado()){
            throw new TokenInvalidoExeption("El token refresh ya se encuentra revocado");
        }
        refreshToken.setRevocado(true);
        refreshTokenRepo.save(refreshToken);

        var response = new HashMap<String, Object>();
        response.put("time", LocalDateTime.now());
        response.put("status", 200);
        response.put("message", "Logout exitoso");

        return response;
    }


}
