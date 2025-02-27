package com.practice.TaskTracker.service.impl;


import com.practice.TaskTracker.domain.model.TaskList;
import com.practice.TaskTracker.repository.TaskListRepository;
import com.practice.TaskTracker.service.TaskListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskListServiceImpl implements TaskListService {

    private final TaskListRepository taskListRepository;

    @Autowired
    public TaskListServiceImpl(TaskListRepository taskListRepository) {
        this.taskListRepository = taskListRepository;
    }

    @Override
    public List<TaskList> listTasks() {
        return taskListRepository.findAll();
    }

    @Override
    public TaskList createTaskList(TaskList taskList) {
        //first we need to check for our constraints and throw errors if it is not me
        if(null != taskList.getId()) //Task Lists must have no ID
        {
            throw new IllegalArgumentException("This Task List has ID Already!");
        }
        //also title has a nullable = false
        if(null == taskList.getTitle() || taskList.getTitle().isBlank())
        {
            throw new IllegalArgumentException("The Title can't be empty!");
        }
        LocalDateTime now = LocalDateTime.now();
        return taskListRepository.save(
                new TaskList(
                        null,
                        taskList.getTitle(),
                        taskList.getDescription(),
                        null,
                        now,
                        now
                )
        );
    }

    @Override
    public Optional<TaskList> getTaskList(UUID id) {
        return taskListRepository.findById(id);
    }

    @Override
    public void deleteTaskListById(UUID taskListId) {

        taskListRepository.deleteById(taskListId);

    }

    @Override
    public TaskList updateTaskList(UUID taskListId,TaskList newTaskList) {
        TaskList existingTaskList = taskListRepository.findById(taskListId)
                .orElseThrow(() -> new IllegalArgumentException("There is no Task List with this Id"));

        if(newTaskList.getTitle() == null || newTaskList.getTitle().isBlank())
            throw new IllegalArgumentException("New Task List must have a title");

        //existingTaskList.setId(newTaskList.getId());
        existingTaskList.setTitle(newTaskList.getTitle());
        existingTaskList.setDescription(newTaskList.getDescription());
        existingTaskList.setTasks(newTaskList.getTasks());
        existingTaskList.setUpdated(LocalDateTime.now());

        return taskListRepository.save(existingTaskList);
    }

}
