package ru.solonchev.backend.dto.response.error;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"code", "message"})
public record ErrorDto(
        String code,
        String message
) {
}
