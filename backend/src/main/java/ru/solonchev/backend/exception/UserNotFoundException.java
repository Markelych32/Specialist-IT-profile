package ru.solonchev.backend.exception;

public class UserNotFoundException extends RuntimeException
        implements BackendException {
    private static final String EXCEPTION_MESSAGE = "User not found";

    @Override
    public String message() {
        return EXCEPTION_MESSAGE;
    }
}
