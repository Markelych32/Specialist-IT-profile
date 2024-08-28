package ru.solonchev.backend.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"addSkillId", "mark", "newRoleId"})
public record ChangeMarkAndRoleRequestDto(
        @JsonProperty("skill_id")
        int addSkillId,
        @JsonProperty("mark")
        int newMark,
        @JsonProperty("new_role_id")
        int newRoleId
) {
}
