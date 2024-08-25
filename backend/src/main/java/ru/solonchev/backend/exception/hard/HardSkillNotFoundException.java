package ru.solonchev.backend.exception.hard;

import ru.solonchev.backend.exception.BackendException;

public class HardSkillNotFoundException extends RuntimeException implements BackendException {
    private static final String EXCEPTION_MESSAGE = "Hard Skill not found";

    @Override
    public String message() {
        return EXCEPTION_MESSAGE;
    }
}
