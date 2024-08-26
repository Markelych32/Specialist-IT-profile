package ru.solonchev.backend.exception.hard;

import ru.solonchev.backend.exception.NotFoundException;

public class HardSkillMarkNotFoundException extends RuntimeException implements NotFoundException {
    private static final String EXCEPTION_MESSAGE = "Hard Skill Mark not found";

    @Override
    public String message() {
        return EXCEPTION_MESSAGE;
    }
}
