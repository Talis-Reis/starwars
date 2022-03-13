package br.com.letscode.starwars.enums.errors;

import org.springframework.http.HttpStatus;

public enum NegotiationValidationError implements ValidationError{
    REBEL_SELLER_NOT_FOUND(HttpStatus.NOT_FOUND),
    REBEL_BUYER_NOT_FOUND(HttpStatus.NOT_FOUND),
    FORBIDDEN_NEGOTIATE_WITH_YOURSELF(HttpStatus.BAD_REQUEST),
    UNFAIR_NEGOTIATION(HttpStatus.BAD_REQUEST),
    CLIENT_WITH_LOCKED_INVENTORY(HttpStatus.BAD_REQUEST),
    DEALER_WITH_LOCKED_INVENTORY(HttpStatus.BAD_REQUEST),
    DEALER_WITH_INSUFFICIENT_ITEMS(HttpStatus.BAD_REQUEST),
    CLIENT_WITH_INSUFFICIENT_ITEMS(HttpStatus.BAD_REQUEST);

    private final HttpStatus status;

    NegotiationValidationError(HttpStatus status) {
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
