package ru.solonchev.backend.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FindUserByFullNameRequestDto(
        @JsonProperty("full_name")
        String fullName
) {
}
