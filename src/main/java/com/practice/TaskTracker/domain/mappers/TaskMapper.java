package com.practice.TaskTracker.domain.mappers;

import com.practice.TaskTracker.domain.dto.TaskDto;
import com.practice.TaskTracker.domain.model.Task;

public interface TaskMapper {
    Task fromDto(TaskDto taskDto);
    TaskDto toDto(Task task);
}
