package ru.solonchev.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.solonchev.backend.dto.response.error.ErrorDto;
import ru.solonchev.backend.exception.NotFoundException;
import ru.solonchev.backend.exception.add.AddCompetenceNotFoundException;
import ru.solonchev.backend.exception.hard.HardSkillMarkNotFoundException;
import ru.solonchev.backend.exception.hard.HardSkillNotFoundException;
import ru.solonchev.backend.exception.soft.SoftGroupNotFoundException;
import ru.solonchev.backend.exception.soft.SoftSkillMarkNotFoundException;
import ru.solonchev.backend.exception.soft.SoftSkillNotFoundException;
import ru.solonchev.backend.exception.user.UserNotFoundException;

import javax.management.relation.RoleInfoNotFoundException;

@RestControllerAdvice
public class BackendExceptionApiHandler {

    @ExceptionHandler({AddCompetenceNotFoundException.class, HardSkillNotFoundException.class,
            HardSkillMarkNotFoundException.class, RoleInfoNotFoundException.class,
            SoftGroupNotFoundException.class, SoftSkillNotFoundException.class,
            SoftSkillMarkNotFoundException.class, UserNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto entityNotFound(NotFoundException exception) {
        return exception.fromBackendExceptionToErrorDto();
    }
}
