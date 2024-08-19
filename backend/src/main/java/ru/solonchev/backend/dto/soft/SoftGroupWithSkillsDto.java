package ru.solonchev.backend.dto.soft;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record SoftGroupWithSkillsDto(
        @JsonProperty("group_name")
        String groupName,
        @JsonProperty("soft_skills")
        List<String> softSkills
) {
}
