package ru.solonchev.backend.dto.mark;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"skillName", "mark"})
public record SoftSkillWithMarkDto(
        @JsonProperty("skill_name")
        String skillName,
        int mark
) {
}
