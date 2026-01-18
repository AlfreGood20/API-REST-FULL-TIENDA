package com.tienda.api_tienda.exeptions;

public class ExepcionRecursoNoEncontrado extends RuntimeException{

    public ExepcionRecursoNoEncontrado(String mensaje){
        super(mensaje);
    }
}
