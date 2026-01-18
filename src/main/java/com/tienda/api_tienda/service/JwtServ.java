package com.tienda.api_tienda.service;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tienda.api_tienda.utils.UsuarioDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServ {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration.access}")
    private Long expirateAccess;

    @Value("${jwt.expiration.refresh}")
    private Long expirateRefresh;


    private Map<String, Object> generatePaylod(UsuarioDetails usuario){
        var paylod = new HashMap<String, Object>();
        paylod.put("nombre_completo",usuario.nombreCompleto());
        paylod.put("roles", usuario.getAuthorities());

        return paylod;
    }

    private Key getKey(){
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    private String generateTokenAccess(UsuarioDetails usuario){
        return Jwts.builder()
            .setClaims(generatePaylod(usuario))
            .setSubject(usuario.getUsername())
            .setExpiration(new Date(System.currentTimeMillis()+expirateAccess))
            .setIssuedAt(new Date())
            .signWith(getKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    public String getTokenAccess(UsuarioDetails usuario){
        return generateTokenAccess(usuario);
    }

    private String generateTokenRefresh(UsuarioDetails usuario){
        return Jwts.builder()
            .setClaims(generatePaylod(usuario))
            .setSubject(usuario.getUsername())
            .setExpiration(new Date(System.currentTimeMillis()+expirateRefresh))
            .setIssuedAt(new Date())
            .signWith(getKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    public String getTokenRefresh(UsuarioDetails usuario){
        return generateTokenRefresh(usuario);
    }

    public String getSubject(String token){
        return getClaims(token).getSubject();
    }

    private Claims getClaims(String token){
        return Jwts.parserBuilder()
            .setSigningKey(getKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    public boolean isValid(String token, UsuarioDetails usuario){
        final String username = usuario.getUsername();

        // SI EL USERNAME ES IGUAL AL USERNAME DEL TOKEN Y ADEMAS NO ESTA EXPIRADO
        return (username.equals(getSubject(token)) && isNotExpirate(token));
    }

    public LocalDateTime getExpiration(String token){
        Date expiration = getClaims(token).getExpiration();
        return expiration.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
    }

    private boolean isNotExpirate(String token){
        Date expiration = getClaims(token).getExpiration();
        return new Date().before(expiration);
    }

}