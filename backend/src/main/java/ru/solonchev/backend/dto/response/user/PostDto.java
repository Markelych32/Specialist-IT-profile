package ru.solonchev.backend.dto.response.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PostDto(
        int id,
        @JsonProperty("post_name")
        String postName) {
}
