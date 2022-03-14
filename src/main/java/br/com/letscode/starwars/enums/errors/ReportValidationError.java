package br.com.letscode.starwars.enums.errors;

import org.springframework.http.HttpStatus;

public enum ReportValidationError implements ValidationError{
    REBELS_CAN_ONLY_REPORT_ONCE(HttpStatus.BAD_REQUEST),
    THIS_REBEL_IS_A_TRAITOR(HttpStatus.BAD_REQUEST);

    private final HttpStatus status;

    ReportValidationError(HttpStatus status) {
        this.status = status;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public HttpStatus getStatus() {
        return null;
    }
}
