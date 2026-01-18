package com.tienda.api_tienda.exeptions;

public class TokenInvalidoExeption extends RuntimeException{

    public TokenInvalidoExeption(String message) {
        super(message);
    }
}
