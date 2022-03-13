package br.com.letscode.starwars.exception;

import br.com.letscode.starwars.enums.errors.ValidationError;

public class BusinessException extends RuntimeException {

    private final ValidationError type;

    public BusinessException(ValidationError type, String message) {
        super(message);
        this.type = type;
    }

    public ValidationError getType() {
        return type;
    }
}

