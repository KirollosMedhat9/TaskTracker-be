package com.practice.TaskTracker.controller;


import com.practice.TaskTracker.domain.dto.TaskListDto;
import com.practice.TaskTracker.domain.mappers.TaskListMapper;
import com.practice.TaskTracker.domain.model.TaskList;
import com.practice.TaskTracker.service.TaskListService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/task-lists")
public class TaskListController {

    private final TaskListService taskListService;
    private final TaskListMapper taskListMapper;


    public TaskListController(TaskListService taskListService, TaskListMapper taskListMapper) {
        this.taskListService = taskListService;
        this.taskListMapper = taskListMapper;
    }

    @GetMapping
    public ResponseEntity<List<TaskListDto>> listTaskLists()
    {
        return ResponseEntity.ok(taskListService.listTasks()
                .stream()
                .map(taskListMapper::toDto).toList());
    }

    @PostMapping
    public ResponseEntity<TaskListDto> createTaskList(@RequestBody TaskListDto taskListDto)
    {
        TaskList createdTaskList = taskListService.createTaskList(taskListMapper.fromDto(taskListDto));
        TaskListDto createdTaskListDto = taskListMapper.toDto(createdTaskList);
        return ResponseEntity.created(URI.create("/task-lists/"+ createdTaskListDto.id())).body(createdTaskListDto);
//        return  taskListMapper.toDto(taskListService.createTaskList(taskListMapper.fromDto(taskListDto));
    }

    @GetMapping(path = "/{task_list_id}")
    public ResponseEntity<Optional<TaskListDto>> getTaskList(@PathVariable("task_list_id") UUID taskListId)
    {
        return ResponseEntity.ok(taskListService.getTaskList(taskListId)
                .map(taskListMapper::toDto));
    }

    @DeleteMapping(path = "/{task_list_id}")
    public ResponseEntity<Void> deleteTaskList(@PathVariable("task_list_id") UUID id)
    {
        taskListService.deleteTaskListById(id);
        return ResponseEntity.noContent().build();
    }



    @PutMapping(path = "/{task_list_id}")
    public TaskListDto updateTaskList(@PathVariable("task_list_id") UUID taskListId,
                                      @RequestBody TaskListDto taskListDto)
    {
        return taskListMapper.toDto(
                taskListService.updateTaskList(
                        taskListId,taskListMapper.fromDto(taskListDto)));
    }

}
