package com.tienda.api_tienda.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tienda.api_tienda.model.Usuario;
import com.tienda.api_tienda.repository.UsuarioRepo;
import com.tienda.api_tienda.utils.UsuarioDetails;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UsuarioDetailsServ implements UserDetailsService{

    private final UsuarioRepo usuarioRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Usuario usuario = usuarioRepo.findByCorreo(username)
            .orElseThrow(()-> new UsernameNotFoundException("Usuario No encontrado"));

        UsuarioDetails usuarioDetails = new UsuarioDetails(usuario);

        return usuarioDetails;
    }

}
