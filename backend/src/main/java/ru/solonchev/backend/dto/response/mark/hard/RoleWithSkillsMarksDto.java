package ru.solonchev.backend.dto.response.mark.hard;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record RoleWithSkillsMarksDto(
        @JsonProperty("role_name")
        String roleName,
        @JsonProperty("hard_skills")
        List<HardSkillWithMarkDto> hardSkills
) {
}
