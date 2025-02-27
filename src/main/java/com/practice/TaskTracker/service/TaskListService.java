package com.practice.TaskTracker.service;

import com.practice.TaskTracker.domain.model.TaskList;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskListService {
    List<TaskList> listTasks();
    TaskList createTaskList(TaskList taskList);
    Optional<TaskList> getTaskList(UUID taskListId);
    void deleteTaskListById(UUID taskListId);
    TaskList updateTaskList(UUID taskListId,TaskList newTaskList);
}
