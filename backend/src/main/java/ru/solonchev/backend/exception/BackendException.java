package ru.solonchev.backend.exception;

import org.springframework.http.HttpStatus;
import ru.solonchev.backend.dto.response.error.ErrorDto;

public interface BackendException {

    default ErrorDto fromBackendExceptionToErrorDto() {
        return new ErrorDto(code(), message());
    }

    String code();

    String message();
}
