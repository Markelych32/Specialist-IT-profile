package ru.solonchev.backend.dto.response.hard;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record HardSkillDto(
        @JsonProperty("hard_skill")
        String hardSkill,
        List<String> indicators
) {
}
