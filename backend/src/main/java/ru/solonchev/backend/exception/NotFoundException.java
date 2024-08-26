package ru.solonchev.backend.exception;

import org.springframework.http.HttpStatus;

public interface NotFoundException extends BackendException {

    @Override
    default String code() {
        return String.valueOf(HttpStatus.NOT_FOUND.value());
    }

    @Override
    default String message() {
        return "Entity not found";
    }
}
