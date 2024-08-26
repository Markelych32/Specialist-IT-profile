package ru.solonchev.backend.exception.soft;

import ru.solonchev.backend.exception.NotFoundException;

public class SoftSkillNotFoundException extends RuntimeException implements NotFoundException {
    private static final String EXCEPTION_MESSAGE = "Soft Skill not found";

    @Override
    public String message() {
        return EXCEPTION_MESSAGE;
    }
}
