package com.tienda.api_tienda.configuration.security;

import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import com.tienda.api_tienda.exeptions.AutenticacionExeption;
import com.tienda.api_tienda.exeptions.AutorizacionExeption;
import com.tienda.api_tienda.service.UsuarioDetailsServ;

import lombok.AllArgsConstructor;

@EnableWebSecurity
@Configuration
@AllArgsConstructor
public class SpringSecurityConfig {

    private final FilterJwt filterJwt;
    private final UsuarioDetailsServ usuarioDetailsServ;
    private final AutenticacionExeption autenticacionExeption;
    private final AutorizacionExeption autorizacionExeption;
    

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http

            .csrf((csrf) -> csrf.disable())

            .cors((cors) -> cors
                .configurationSource(request -> {
                    CorsConfiguration configuration = new CorsConfiguration();
                    configuration.setAllowCredentials(true);
                    configuration.setAllowedOriginPatterns(Arrays.asList("http://localhost:5173"));
                    configuration.setAllowedMethods(Arrays.asList("*"));
                    configuration.setAllowedHeaders(Arrays.asList("*"));
                    
                    return configuration;
                }))

            .exceptionHandling((ex) -> ex
                .authenticationEntryPoint(autenticacionExeption)
                .accessDeniedHandler(autorizacionExeption)
            )


            .authorizeHttpRequests((authorizeHttpRequest) -> authorizeHttpRequest
                .requestMatchers("/api/public/**").permitAll()
                .requestMatchers("/api/admin/**").permitAll()
                .requestMatchers("/api/customer/**").hasAnyRole("ADMIN","CUSTOMER","EMPLOYEE")
                .requestMatchers("/api/employee/**").hasAnyRole("ADMIN","EMPLOYEE")
                .requestMatchers("/api-documentacion/**").permitAll()
                .requestMatchers("/v3/api-docs/**").permitAll()
                .requestMatchers("/swagger-ui/**").permitAll()
                .requestMatchers("/uploads/**").permitAll()
                .anyRequest().authenticated()
            )

            .sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            .userDetailsService(usuarioDetailsServ)

            .addFilterBefore(filterJwt, UsernamePasswordAuthenticationFilter.class)

            .httpBasic((httpBasic) -> httpBasic.disable())

        .build();
    }
}