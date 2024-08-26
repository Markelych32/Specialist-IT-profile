package ru.solonchev.backend.exception.add;

import ru.solonchev.backend.exception.NotFoundException;

public class AddCompetenceNotFoundException extends RuntimeException
        implements NotFoundException {
    private static final String EXCEPTION_MESSAGE = "Additional Competence not found";

    @Override
    public String message() {
        return EXCEPTION_MESSAGE;
    }
}
