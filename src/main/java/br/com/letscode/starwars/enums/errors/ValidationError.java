package br.com.letscode.starwars.enums.errors;

import org.springframework.http.HttpStatus;

public interface ValidationError {
    String getName();
    HttpStatus getStatus();
}
