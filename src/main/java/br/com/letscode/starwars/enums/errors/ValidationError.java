package br.com.letscode.starwars.enums.errors;

import org.springframework.http.HttpStatus;

import java.util.Enumeration;

public interface ValidationError {
    String getName();
    HttpStatus getStatus();
}
