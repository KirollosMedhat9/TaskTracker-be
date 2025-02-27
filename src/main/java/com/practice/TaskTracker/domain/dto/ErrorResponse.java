package com.practice.TaskTracker.domain.dto;

public record ErrorResponse(
        int status,
        String message,
        String details
) {
}
