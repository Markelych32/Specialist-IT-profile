package ru.solonchev.backend.exception;

public class AddCompetenceNotFoundException extends RuntimeException
        implements BackendException {
    private static final String EXCEPTION_MESSAGE = "Additional Competence not found";

    @Override
    public String message() {
        return EXCEPTION_MESSAGE;
    }
}
