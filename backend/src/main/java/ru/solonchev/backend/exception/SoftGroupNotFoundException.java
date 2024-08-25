package ru.solonchev.backend.exception;

public class SoftGroupNotFoundException extends RuntimeException implements BackendException {
    private static final String EXCEPTION_MESSAGE = "Soft Group not found";

    @Override
    public String message() {
        return EXCEPTION_MESSAGE;
    }
}
