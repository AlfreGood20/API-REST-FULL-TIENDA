package com.tienda.api_tienda.exeptions;

import java.io.IOException;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AutorizacionExeption implements AccessDeniedHandler{

    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
         MensajeExepcion body = MensajeExepcion.builder()
            .timestamp(LocalDateTime.now())
            .status(403)
            .error(HttpStatus.FORBIDDEN.getReasonPhrase())
            .message("Sin permiso a este recurso")
            .path(request.getRequestURI())
            .build();

        response.setContentType("application/json");
        response.setStatus(403);
        response.getWriter().write(objectMapper.writeValueAsString(body));
    }

}
