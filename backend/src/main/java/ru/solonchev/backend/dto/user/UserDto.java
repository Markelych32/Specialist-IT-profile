package ru.solonchev.backend.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.solonchev.backend.model.role.Role;
import ru.solonchev.backend.model.user.Post;

import java.time.LocalDate;

public record UserDto(
        int id,
        @JsonProperty("first_name")
        String firstName,
        @JsonProperty("last_name")
        String lastName,
        String patronymic,
        @JsonProperty("date_of_birth")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        LocalDate dateOfBirth,
        String gender,
        String city,
        Post post,
        Role role,
        String specialization
) {
}
