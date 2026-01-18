package com.tienda.api_tienda.service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.tienda.api_tienda.dtos.request.UsuarioRequest;
import com.tienda.api_tienda.dtos.response.UsuarioResponse;
import com.tienda.api_tienda.exeptions.ExepcionRecursoNoEncontrado;
import com.tienda.api_tienda.mapper.UsuarioMapper;
import com.tienda.api_tienda.model.Rol;
import com.tienda.api_tienda.model.Usuario;
import com.tienda.api_tienda.repository.RolRepo;
import com.tienda.api_tienda.repository.UsuarioRepo;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UsuarioServ {

    private final UsuarioRepo usuarioRepo;
    private final RolRepo rolRepo;
    private final UsuarioMapper mapper;
    private final PasswordEncoder encoder;

    @SuppressWarnings("null")
    public UsuarioResponse crear(UsuarioRequest request){
        var roles = new HashSet<Rol>();

        for (Long id: request.getRoles()) {
            roles.add(rolRepo.findById(id)
                .orElseThrow(()->new ExepcionRecursoNoEncontrado("Rol con id "+id+" no encontrado")
            ));
        }

        Usuario usuario = mapper.dtoToEntity(request, roles);
        usuario.setContrasena(encoder.encode(request.getContrasena()));
        
        return mapper.entityToDto(usuarioRepo.save(usuario));
    }

    public List<UsuarioResponse> obtener(){
        return usuarioRepo.findAll()
            .stream()
            .map(u -> mapper.entityToDto(u))
            .collect(Collectors.toList());
    }
}