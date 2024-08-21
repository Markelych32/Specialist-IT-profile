package ru.solonchev.backend.dto.response.hard;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"id", "name", "role"})
public record AddCompetenceDto(
        int id,
        String name,
        String role
) {
}
