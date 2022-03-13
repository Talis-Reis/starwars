package br.com.letscode.starwars.enums.errors;

import org.springframework.http.HttpStatus;

public enum RebelValidationError implements ValidationError{
    NOT_FOUND_REBEL(HttpStatus.NOT_FOUND);

    private final HttpStatus status;

    RebelValidationError(HttpStatus status) {
        this.status = status;
    }

    @Override
    public String getName() {
        return this.name();
    }

    @Override
    public HttpStatus getStatus() {
        return this.status;
    }
}
