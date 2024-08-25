package ru.solonchev.backend.exception;

public class HardSkillNotFoundException extends RuntimeException implements BackendException {
    private static final String EXCEPTION_MESSAGE = "Hard Skill not found";

    @Override
    public String message() {
        return EXCEPTION_MESSAGE;
    }
}
