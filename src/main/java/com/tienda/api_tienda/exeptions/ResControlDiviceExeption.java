package com.tienda.api_tienda.exeptions;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ResControlDiviceExeption {

    @ExceptionHandler(IOException.class)
    public ResponseEntity<MensajeExepcion> manejarIOExepcion(IOException ex,HttpServletRequest request){
        MensajeExepcion mensaje = MensajeExepcion.builder()
            .timestamp(LocalDateTime.now())
            .status(500)
            .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
            .message("Ocurrio un error al subir la imagen: "+ ex.getMessage())
            .path(request.getRequestURI()).build();

        return new ResponseEntity<MensajeExepcion>(mensaje,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<MensajeExepcion> manejarCredencialesInvalidas(HttpServletRequest request){
        MensajeExepcion mensaje = MensajeExepcion.builder()
            .timestamp(LocalDateTime.now())
            .status(401)
            .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
            .message("Datos y/o credenciales incorrectas")
            .path(request.getRequestURI()).build();
            
        return new ResponseEntity<MensajeExepcion>(mensaje, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<MensajeExepcion> manejarUsuarioNoEncontrado(UsernameNotFoundException ex, HttpServletRequest request){
        MensajeExepcion mensaje = MensajeExepcion.builder()
            .timestamp(LocalDateTime.now())
            .status(404)
            .error(HttpStatus.NOT_FOUND.getReasonPhrase())
            .message("Usuario no se encuentra registrado")
            .path(request.getRequestURI()).build();

        return new ResponseEntity<MensajeExepcion>(mensaje, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExepcionRecursoNoEncontrado.class)
    public ResponseEntity<?> manejarRecursosNoEncontrado(ExepcionRecursoNoEncontrado ex, HttpServletRequest request){
        MensajeExepcion mensaje= MensajeExepcion.builder()
            .timestamp(LocalDateTime.now())
            .status(404)
            .error(HttpStatus.NOT_FOUND.getReasonPhrase())
            .message(ex.getMessage())
            .path(request.getRequestURI()).build();

        return new ResponseEntity<>(mensaje, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler()
    public ResponseEntity<?> manejarErrorInesperado(Exception ex, HttpServletRequest request){
        MensajeExepcion mensaje= MensajeExepcion.builder()
            .timestamp(LocalDateTime.now())
            .status(500)
            .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
            .message(ex.getMessage())
            .path(request.getRequestURI()).build();
        
        return ResponseEntity.internalServerError().body(mensaje);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<?> manejarEndpointNoExistente(NoHandlerFoundException ex, HttpServletRequest request) {
            MensajeExepcion respuesta = MensajeExepcion.builder()
                .timestamp(LocalDateTime.now())
                .status(404)
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(request.getMethod()+" "+request.getRequestURI()+" no existe")
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> manejarMetodoNoSoportado(HttpServletRequest request){
        MensajeExepcion repuesta= MensajeExepcion.builder()
            .timestamp(LocalDateTime.now())
            .status(405)
            .error(HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase())
            .message("Este enpoint no soporta "+request.getMethod())
            .path(request.getRequestURI())
            .build();

        return new ResponseEntity<>(repuesta,HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> cuerpoInvalido(HttpMessageNotReadableException ex, HttpServletRequest request) {
        MensajeExepcion respuesta = MensajeExepcion.builder()
                .timestamp(LocalDateTime.now())
                .status(400)
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message("El cuerpo de la solicitud está vacío o mal formado.")
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.badRequest().body(respuesta);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> manejarValidaciones(MethodArgumentNotValidException ex, HttpServletRequest request) {

        var mensaje = ex.getFieldErrors()
            .stream()
            .collect(
                Collectors.toMap(
                    fieldError -> fieldError.getField(),
                    fieldError -> fieldError.getDefaultMessage(),
                    (msg1, msg2) -> msg1
                )
            );
        
        MensajeExepcion respuesta = MensajeExepcion.builder()
                .timestamp(LocalDateTime.now())
                .status(400)
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(mensaje)
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.badRequest().body(respuesta);
    }


    @ExceptionHandler(TokenInvalidoExeption.class)
    public ResponseEntity<?> manejarTokenExpiradoORevocado(TokenInvalidoExeption ex, HttpServletRequest request){
        MensajeExepcion respuesta = MensajeExepcion.builder()
                .timestamp(LocalDateTime.now())
                .status(401)
                .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(respuesta, HttpStatus.UNAUTHORIZED);
    }

}
