package com.practice.TaskTracker.domain.dto;

import com.practice.TaskTracker.domain.model.TaskPriority;
import com.practice.TaskTracker.domain.model.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

//record for immutable data
public record TaskDto(
        UUID id,
        String title,
        String description,
        LocalDateTime dueDate,
        TaskPriority priority,
        TaskStatus status
) {
}
