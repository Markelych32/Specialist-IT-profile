package ru.solonchev.backend.dto.soft;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record SoftGroupsDto(
        @JsonProperty("soft_groups")
        List<String> softGroups
) {
}
