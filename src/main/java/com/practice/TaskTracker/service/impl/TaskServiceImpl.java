package com.practice.TaskTracker.service.impl;

import com.practice.TaskTracker.domain.model.Task;
import com.practice.TaskTracker.domain.model.TaskList;
import com.practice.TaskTracker.domain.model.TaskPriority;
import com.practice.TaskTracker.domain.model.TaskStatus;
import com.practice.TaskTracker.repository.TaskListRepository;
import com.practice.TaskTracker.repository.TaskRepository;
import com.practice.TaskTracker.service.TaskService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService
{

    private final TaskRepository taskRepository;
    private final TaskListRepository taskListRepository;

    public TaskServiceImpl(TaskRepository taskRepository, TaskListRepository taskListRepository) {
        this.taskRepository = taskRepository;
        this.taskListRepository = taskListRepository;
    }

    @Override
    public List<Task> listTasks(UUID taskListId) {
        return taskRepository.findByTaskListId(taskListId);
    }

    @Override
    public Task createTask(UUID taskListId, Task addedTask) {

        TaskList taskList = taskListRepository.findById(taskListId)
                .orElseThrow(() -> new IllegalArgumentException("TaskList not found with id: " + taskListId));


    if(addedTask.getId() != null)
        throw new IllegalArgumentException("This Task has been created before");

    if(addedTask.getTitle() == null || addedTask.getTitle().isBlank())
        throw new IllegalArgumentException("Task Title can't be Blank");


    LocalDateTime now = LocalDateTime.now();

    TaskPriority priority = Optional.ofNullable(addedTask.getTaskPriority())
            .orElse(TaskPriority.MEDIUM);

    return taskRepository.save(new Task(null,
            addedTask.getTitle(),
            addedTask.getDescription(),
            addedTask.getDueDate(),
            priority,
            TaskStatus.OPEN,
            taskList,
            now,
            now
    ));
}

    @Override
    public void deleteTask(UUID taskListId, UUID id) {
        TaskList taskList = taskListRepository.findById(taskListId).orElseThrow(
                () -> new IllegalArgumentException("No Task List found by this Id")
        );
        taskRepository.deleteById(id);
    }

    @Override
    public Optional<Task> getTask(UUID taskListId, UUID id) {

        return taskRepository.findByTaskListIdAndId(taskListId,id);
    }

    @Override
    public Task updateTask(UUID taskListId, UUID id, Task updatedTask) {
        TaskList taskList = taskListRepository.findById(taskListId).orElseThrow(
                () -> new IllegalArgumentException("No Task List by this Id")
        );
        Task existingTask = taskRepository.findByTaskListIdAndId(taskListId,id).orElseThrow(
                () -> new IllegalArgumentException("No Task by this Id")
        );

        if(updatedTask.getTitle() == null || updatedTask.getTitle().isBlank())
            throw new IllegalArgumentException("Task Can't Have Blank Id");

        LocalDateTime now = LocalDateTime.now(); //for having updated with the time of now


        return taskRepository.save(new Task(
                existingTask.getId(),
                updatedTask.getTitle(),
                updatedTask.getDescription(),
                updatedTask.getDueDate(),
                updatedTask.getTaskPriority(),
                updatedTask.getTaskStatus(),
                taskList,
                existingTask.getCreated(),
                now
        ));
    }
}
