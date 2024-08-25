package ru.solonchev.backend.exception;

import org.springframework.http.HttpStatus;

public interface BackendException {
    default String code() {
        return String.valueOf(HttpStatus.NOT_FOUND.value());
    }

    String message();
}
