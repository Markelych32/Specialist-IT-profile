package ru.solonchev.backend.dto.mark.hard;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record UserHardSkillsMarksDto(
        @JsonProperty("user_id")
        int id,
        @JsonProperty("hard_marks")
        List<RoleWithSkillsMarksDto> hardMarks
) {
}
