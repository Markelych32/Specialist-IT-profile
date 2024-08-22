package ru.solonchev.backend.dto.response.hard;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"id", "skill_name", "roleName"})
public record SuitableRoleDto(
        @JsonProperty("skill_id")
        int id,
        @JsonProperty("skill_name")
        String skillName,
        @JsonProperty("role_name")
        String roleName
) {
}
