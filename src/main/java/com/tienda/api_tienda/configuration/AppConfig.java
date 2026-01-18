package com.tienda.api_tienda.configuration;

import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.tienda.api_tienda.model.Categoria;
import com.tienda.api_tienda.model.Rol;
import com.tienda.api_tienda.repository.CategoriaRepo;
import com.tienda.api_tienda.repository.RolRepo;
import com.tienda.api_tienda.service.UsuarioDetailsServ;
import com.tienda.api_tienda.utils.CategoriaEnum;
import com.tienda.api_tienda.utils.RolEnum;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;


@OpenAPIDefinition(
    info = @Info(
        title = "API - TIENDA",
        version = "1.0.0",
        summary = "Api rest full",
        contact = @Contact(email = "josealfredolopezdelacruz2@gmail.com", name = "AlfreGood20", url = "https://github.com/AlfreGood20"),
        description = 
        """
                                            API-TIENDA
            Api rest full, contruido para consumir en un front-end que se encuentra en proceso.
            Esta api contiene:
                - Autenticacion basada en token (JWT)
                - Buenas practicas
                - 100% rest full
                - Endpoint seguros
        """
    ),
    servers = {
        @Server(url = "http://localhost:8080", description = "Servidor Local"), 
        @Server(url = "https://", description = "Servidor Produccion")
    }
)

@SecurityScheme(
    name = "Bearer",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    scheme = "bearer"
)

@Configuration
@AllArgsConstructor
public class AppConfig implements WebMvcConfigurer{

    private final RolRepo rolRepo;
    private final UsuarioDetailsServ usuarioDetailsServ;
    private final CategoriaRepo categoriaRepo;

    @Bean
    public AuthenticationManager authenticationManager (AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @SuppressWarnings("deprecation")
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(encoder());
        provider.setUserDetailsService(usuarioDetailsServ);

        return provider;
    }

    @PostConstruct
    public void preRoles(){
        
        var roles = Arrays.asList(
            Rol.builder().nombre(RolEnum.ADMIN).build(),
            Rol.builder().nombre(RolEnum.CUSTOMER).build(),
            Rol.builder().nombre(RolEnum.EMPLOYEE).build()
        );

        for (Rol rol: roles) {
            if(!rolRepo.existsByNombre(rol.getNombre())) rolRepo.save(rol);
        }
    }

    @SuppressWarnings("null")
    @PostConstruct
    public void preCategorias() {
        var categorias = Arrays.asList(CategoriaEnum.values());
        
        for (CategoriaEnum catEnum : categorias) {
            String nombre = catEnum.name();

            if (!categoriaRepo.existsByNombre(nombre)) {
                categoriaRepo.save(Categoria.builder().nombre(nombre).build());
            }
        }
    }

    @Override
    public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:C:/Users/HP/Documents/PROGRAMACION/SPRING-BOOTS/api-tienda/uploads/");
    }

}