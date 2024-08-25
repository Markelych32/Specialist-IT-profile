package ru.solonchev.backend.exception.hard;


import ru.solonchev.backend.exception.BackendException;

public class RoleNotFoundException extends RuntimeException implements BackendException {
    private static final String EXCEPTION_MESSAGE = "Role not found";

    @Override
    public String message() {
        return EXCEPTION_MESSAGE;
    }
}
