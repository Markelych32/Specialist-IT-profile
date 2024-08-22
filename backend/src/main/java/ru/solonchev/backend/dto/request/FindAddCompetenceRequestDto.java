package ru.solonchev.backend.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FindAddCompetenceRequestDto(
        @JsonProperty("competence_name")
        String competenceName
) {
}
