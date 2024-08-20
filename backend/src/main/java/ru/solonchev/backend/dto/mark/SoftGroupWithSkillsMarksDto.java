package ru.solonchev.backend.dto.mark;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record SoftGroupWithSkillsMarksDto(
        @JsonProperty("soft_group_name")
        String softGroupName,
        @JsonProperty("soft_skills")
        List<SoftSkillWithMarkDto> softSkills
) {
}
