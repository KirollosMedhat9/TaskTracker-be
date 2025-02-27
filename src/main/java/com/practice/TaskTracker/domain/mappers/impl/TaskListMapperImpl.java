package com.practice.TaskTracker.domain.mappers.impl;


import com.practice.TaskTracker.domain.dto.TaskListDto;
import com.practice.TaskTracker.domain.mappers.TaskListMapper;
import com.practice.TaskTracker.domain.mappers.TaskMapper;
import com.practice.TaskTracker.domain.model.Task;
import com.practice.TaskTracker.domain.model.TaskList;
import com.practice.TaskTracker.domain.model.TaskStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TaskListMapperImpl implements TaskListMapper {


    /*I need a method from the TasKMapperImpl why ?
    as I have TaskListDto contains List<TaskDto>
    if I want to return a TaskList then I need it to be List<Task>
    so I need to take this TaskDto and convert it to Task
    How ?
    first if I have a list and I want to navigate each item I can navigate through .stream() method
    second if I want to apply a change to every item in a list ( in a stream ) use .map()
    But ! we need to change each TaskDto to a Task
    We implemented that in the TaskMapperImpl fromDto method
    How we can access it ?
    We can inject this component in our design so we can use this method mapped for every TaskDto in list to be Task
    then change them to .List() again to have our TaskList converted from TaskListDto
     */

    private final TaskMapper taskMapper;

    public TaskListMapperImpl(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    @Override
    public TaskList fromDto(TaskListDto taskListDto) {
        return new TaskList(
                taskListDto.id(),
                taskListDto.title(),
                taskListDto.description(),
                Optional.ofNullable(taskListDto.tasks())
                        .map(tasks -> tasks.stream()
                                .map(taskMapper::fromDto).toList())
                        .orElse(null),
                null,
                null
        );
    }

    @Override
    public TaskListDto toDto(TaskList taskList) {

        final List<Task> tasks = taskList.getTasks();

        return new TaskListDto(
                taskList.getId(),
                taskList.getTitle(),
                taskList.getDescription(),
                Optional.ofNullable(tasks).map(List::size).orElse(0),
                calculateTasksProgress(tasks),
                Optional.ofNullable(tasks)
                        .map(taskDto -> taskDto.stream().map(taskMapper::toDto).toList())
                        .orElse(null)
        );
    }

    private Double calculateTasksProgress(List<Task> tasks) {
        if(tasks == null)
            return null;
        //we need a filter to check for a property in each object in the list that we are receiving and streaming
        long closedTasks = tasks.stream().filter(t -> t.getTaskStatus() == TaskStatus.CLOSED).count();
        return (double) closedTasks / tasks.size();
    }



    private Integer calculateTasks(List<Task> tasks) {
        if (tasks == null)
            return 0;
        return tasks.size(); //check another implementation do we need map ?
    }
}