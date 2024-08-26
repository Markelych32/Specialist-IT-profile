package ru.solonchev.backend.exception.soft;


import ru.solonchev.backend.exception.NotFoundException;

public class SoftSkillMarkNotFoundException extends RuntimeException implements NotFoundException {
    private static final String EXCEPTION_MESSAGE = "Soft Skill Mark not found";

    @Override
    public String message() {
        return EXCEPTION_MESSAGE;
    }
}
