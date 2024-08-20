package ru.solonchev.backend.dto.mark.soft;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"skillId", "skillName", "mark"})
public record SoftSkillWithMarkDto(
        @JsonProperty("skill_id")
        int skillId,
        @JsonProperty("skill_name")
        String skillName,
        int mark
) {
}
