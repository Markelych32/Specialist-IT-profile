package ru.solonchev.backend.dto.response.user;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"post", "role", "specialization"})
public record UserJobInfoDto(
        String post,
        String role,
        String specialization
) {
}
