package com.tienda.api_tienda.configuration.security;

import java.io.IOException;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.tienda.api_tienda.service.JwtServ;
import com.tienda.api_tienda.service.UsuarioDetailsServ;
import com.tienda.api_tienda.utils.UsuarioDetails;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class FilterJwt extends OncePerRequestFilter{

    private final JwtServ jwtServ;
    private final UsuarioDetailsServ usuarioDetailsServ;
    
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response,@NonNull FilterChain filterChain)throws ServletException, IOException {
        
        String nameEx="JWT_ERROR";

        try {

            final String token = getTokenOfHeader(request);

            if(token == null){
                request.setAttribute(nameEx,"empty"); 
            }
            else{

                final String username = jwtServ.getSubject(token);

                // SI NO ES NULL EL USERNAME Y ADEMAS NO SE ENCUENTRA EN EL CONTEXTO SPRIN SECURITY
                if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                    // BUSCAMOS EL USUARIO DE LA BASE DE DATOS
                    UsuarioDetails usuario = (UsuarioDetails) usuarioDetailsServ.loadUserByUsername(username);

                    if(jwtServ.isValid(token, usuario)){
                        Authentication authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }
            
         } catch(ExpiredJwtException ex){
            request.setAttribute(nameEx,"expired");
        } catch (IllegalArgumentException ex){
            request.setAttribute(nameEx,"empty");
        } catch (SecurityException ex){
            request.setAttribute(nameEx,"invalid-signature");
        }catch (MalformedJwtException ex){
            request.setAttribute(nameEx,"malformed");
        }finally{
            filterChain.doFilter(request, response);
        }
    }

    private String getTokenOfHeader(HttpServletRequest request){
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(header != null && header.startsWith("Bearer ")){
            return header.substring(7);
        }
        
        return null;
    }

}
