package br.com.letscode.starwars.exception;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ClientHttpError {
    private int statusCode;
    private String errorCode;
    private String message;
    private final LocalDateTime localDateTime = LocalDateTime.now();

    public ClientHttpError(String message, String errorCode, int statusCode) {
        this.message = message;
        this.errorCode = errorCode;
        this.statusCode = statusCode;
    }
}

