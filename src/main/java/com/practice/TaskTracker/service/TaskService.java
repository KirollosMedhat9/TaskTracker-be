package com.practice.TaskTracker.service;

import com.practice.TaskTracker.domain.model.Task;
import org.springframework.beans.propertyeditors.UUIDEditor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface TaskService {
    List<Task> listTasks(UUID taskListId);
    Task createTask(UUID taskListId,Task addedTask);
    void deleteTask(UUID taskListId,UUID id);
    Optional<Task> getTask(UUID taskListId, UUID id);
    Task updateTask(UUID taskListId,UUID id,Task newTask);
}
