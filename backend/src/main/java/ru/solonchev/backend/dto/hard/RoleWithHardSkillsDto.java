package ru.solonchev.backend.dto.hard;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record RoleWithHardSkillsDto(
        @JsonProperty("role_name")
        String roleName,
        @JsonProperty("hard_skills")
        List<HardSkillDto> hardSkills
) {
}
