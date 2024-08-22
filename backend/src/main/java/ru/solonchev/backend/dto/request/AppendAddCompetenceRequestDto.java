package ru.solonchev.backend.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"userId", "name", "roleId"})
public record AppendAddCompetenceRequestDto(
        @JsonProperty("user_id")
        int userId,
        @JsonProperty("skill_name")
        String name,
        @JsonProperty("role_id")
        int roleId
) {
}
