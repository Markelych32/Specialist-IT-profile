package ru.solonchev.backend.exception.user;

import ru.solonchev.backend.exception.NotFoundException;

public class UserNotFoundException extends RuntimeException
        implements NotFoundException {
    private static final String EXCEPTION_MESSAGE = "User not found";

    @Override
    public String message() {
        return EXCEPTION_MESSAGE;
    }
}
