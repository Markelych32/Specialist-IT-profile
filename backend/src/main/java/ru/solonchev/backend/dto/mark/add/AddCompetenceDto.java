package ru.solonchev.backend.dto.mark.add;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"id", "name", "roleName", "mark"})
public record AddCompetenceDto(
        int id,
        String name,
        @JsonProperty("role_name")
        String roleName,
        int mark
) {
}
