package com.practice.TaskTracker.domain.mappers.impl;


import com.practice.TaskTracker.domain.dto.TaskDto;
import com.practice.TaskTracker.domain.mappers.TaskMapper;
import com.practice.TaskTracker.domain.model.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapperImpl implements TaskMapper {


    //check that the task list is set to null
    @Override
    public Task fromDto(TaskDto taskDto) {
        return new Task(
                taskDto.id(),
                taskDto.title(),
                taskDto.description(),
                taskDto.dueDate(),
                taskDto.priority(),
                taskDto.status(),
                null,
                null,
                null
        );
    }

    @Override
    public TaskDto toDto(Task task) {
        return new TaskDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                task.getTaskPriority(),
                task.getTaskStatus()
        );
    }
}
