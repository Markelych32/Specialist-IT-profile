package ru.solonchev.backend.exception.add;

import org.springframework.http.HttpStatus;
import ru.solonchev.backend.exception.BackendException;

public class AddCompetenceAlreadyInHardSkillsException extends RuntimeException
        implements BackendException {
    private static final String EXCEPTION_MESSAGE = "Additional Competence already in Hard Skills";

    @Override
    public String code() {
        return String.valueOf(HttpStatus.BAD_REQUEST.value());
    }

    @Override
    public String message() {
        return EXCEPTION_MESSAGE;
    }
}
