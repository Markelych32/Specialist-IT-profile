package ru.solonchev.backend.dto.mark.add;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({"user_id", "add_competences"})
public record UserAddCompetencesWithMarksDto(
        @JsonProperty("user_id")
        int id,
        @JsonProperty("add_competences")
        List<AddCompetenceDto> addCompetences
) {
}
