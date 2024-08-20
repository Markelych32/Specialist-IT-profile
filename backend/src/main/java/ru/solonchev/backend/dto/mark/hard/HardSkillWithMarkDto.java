package ru.solonchev.backend.dto.mark.hard;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({"id", "skillName", "gradeMethod",
        "developMethod", "indicators"})
public record HardSkillWithMarkDto(
        @JsonProperty("skill_id")
        int id,
        @JsonProperty("skill_name")
        String skillName,
        @JsonProperty("grade_method")
        String gradeMethod,
        @JsonProperty("develop_method")
        String developMethod,
        int mark,
        List<String> indicators
) {
}
