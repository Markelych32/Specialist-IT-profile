package ru.solonchev.backend.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"skillId", "mark"})
public record ChangeMarkHardSkillRequest(
        @JsonProperty("skill_id")
        int skillId,
        int mark
) {
}
