package ru.solonchev.backend.exception;

public class HardSkillMarkNotFoundException extends RuntimeException implements BackendException {
    private static final String EXCEPTION_MESSAGE = "Hard Skill Mark not found";

    @Override
    public String message() {
        return EXCEPTION_MESSAGE;
    }
}
