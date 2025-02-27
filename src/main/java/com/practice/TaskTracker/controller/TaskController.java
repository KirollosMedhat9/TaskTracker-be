package com.practice.TaskTracker.controller;

import com.practice.TaskTracker.domain.dto.TaskDto;
import com.practice.TaskTracker.domain.mappers.TaskMapper;
import com.practice.TaskTracker.domain.model.Task;
import com.practice.TaskTracker.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/task-lists/{task_list_id}/tasks")

public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    public TaskController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }


    @GetMapping
    public ResponseEntity<List<TaskDto>> getTasksByTaskListId(@PathVariable("task_list_id") UUID id)
    {
        return ResponseEntity.ok(taskService.listTasks(id).stream().map(taskMapper::toDto).toList());
    }

    @PostMapping
    public ResponseEntity<TaskDto> createTaskByTaskListId(@PathVariable("task_list_id") UUID id,
                                          @Validated @RequestBody TaskDto taskDto)
    {
        Task created = taskService.createTask(id,taskMapper.fromDto(taskDto));
        TaskDto createdTask = taskMapper.toDto(created);
        return  ResponseEntity.created(URI.create("/task-lists/" + id + "/tasks/" + createdTask.id())).body(createdTask);
    }

    @DeleteMapping(path = "/{task_id}")
    public ResponseEntity<Void> deleteTask(@PathVariable("task_list_id") UUID taskListId,
                           @PathVariable("task_id") UUID taskId)
    {
        taskService.deleteTask(taskListId,taskId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/{task_id}")
    public ResponseEntity<Optional<TaskDto>> getTask(@PathVariable("task_list_id") UUID taskListId,
                                     @PathVariable("task_id") UUID taskId)
    {
        return ResponseEntity.ok(taskService.getTask(taskListId,taskId).map(taskMapper::toDto));
    }

    @PutMapping(path = "/{task_id}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable("task_list_id") UUID taskListId,
                                             @PathVariable("task_id") UUID taskId,
                                             @RequestBody TaskDto taskDto)
    {
            Task updatedTask = taskService.updateTask(taskListId,taskId,taskMapper.fromDto(taskDto));
            return ResponseEntity.ok(taskMapper.toDto(updatedTask));
    }
}
