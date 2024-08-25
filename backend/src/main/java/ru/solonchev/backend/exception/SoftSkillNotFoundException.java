package ru.solonchev.backend.exception;

public class SoftSkillNotFoundException extends RuntimeException implements BackendException {
    private static final String EXCEPTION_MESSAGE = "Soft Skill not found";
    @Override
    public String message() {
        return EXCEPTION_MESSAGE;
    }
}
