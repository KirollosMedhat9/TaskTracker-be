package com.practice.TaskTracker.domain.mappers;

import com.practice.TaskTracker.domain.dto.TaskListDto;
import com.practice.TaskTracker.domain.model.TaskList;

public interface TaskListMapper {
    TaskList fromDto(TaskListDto taskListDto);
    TaskListDto toDto(TaskList taskList);
}
