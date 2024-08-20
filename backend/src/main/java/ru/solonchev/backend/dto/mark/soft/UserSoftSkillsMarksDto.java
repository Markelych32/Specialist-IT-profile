package ru.solonchev.backend.dto.mark.soft;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record UserSoftSkillsMarksDto(
        @JsonProperty("user_id")
        int id,
        @JsonProperty("soft_marks")
        List<SoftGroupWithSkillsMarksDto> softMarks
) {
}
