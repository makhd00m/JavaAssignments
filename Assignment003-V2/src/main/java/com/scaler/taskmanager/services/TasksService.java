package com.scaler.taskmanager.services;

import com.scaler.taskmanager.models.Task;
import com.scaler.taskmanager.repositories.TasksRepository;
import com.scaler.taskmanager.dtos.requestDTOS.CreateTaskRequestDTO;
import com.scaler.taskmanager.dtos.requestDTOS.UpdateTaskRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TasksService {
    private final TasksRepository tasksRepository;
    private List<Task> tasks = new ArrayList<>();
    private Integer id = 0;

    public TasksService(@Autowired TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    @Getter
    @AllArgsConstructor
    public static class TaskFilter {
        LocalDate beforeDate;
        LocalDate afterDate;
        Boolean completed;

        public static TaskFilter fromQueryParams(LocalDate beforeDate, LocalDate afterDate, Boolean completed) {
            if(beforeDate == null && afterDate == null && completed == null) {
                return null;
            }

            TaskFilter taskFilter = new TaskFilter(beforeDate, afterDate, completed);
            return taskFilter;
        }
    }

    public List<Task> getAllTasks(TaskFilter taskFilter) {
        if(taskFilter == null) {
            return tasks;
        } else {
            var filteredTasks = tasks.stream().filter(task -> {
                if(taskFilter.beforeDate != null && task.getDueDate().compareTo(taskFilter.beforeDate) > 0) {
                    return false;
                }
                if(taskFilter.afterDate != null && task.getDueDate().compareTo(taskFilter.afterDate) < 0) {
                    return false;
                }
                if(taskFilter.completed != null && task.getCompleted() != taskFilter.completed) {
                    return false;
                }
                return true;
            }).toList();
            return filteredTasks;
        }
    }

    public Task getTaskById(Integer id) {
        for(Task task : tasks) {
            if(task.getId().equals(id)) {
                return task;
            }
        }
        throw new TaskNotFoundException(id);
    }

    public Task createTask(CreateTaskRequestDTO createTaskDTO) {
        if(createTaskDTO.getName().length() < 5 || createTaskDTO.getName().length() > 100)
            throw new IllegalArgumentException(createTaskDTO.getName());
        if(createTaskDTO.getDueDate().compareTo(java.time.LocalDate.now()) < 0)
            throw new IllegalArgumentException(createTaskDTO.getDueDate());

        Task task = new Task(id++, createTaskDTO.getName(), createTaskDTO.getDueDate(), false);
        tasks.add(task);
        return task;
    }

    public Task createTask(Integer newId, CreateTaskRequestDTO createTaskDTO) {
        if(createTaskDTO.getName().length() < 5 || createTaskDTO.getName().length() > 100)
            throw new IllegalArgumentException(createTaskDTO.getName());
        if(createTaskDTO.getDueDate().compareTo(java.time.LocalDate.now()) < 0)
            throw new IllegalArgumentException(createTaskDTO.getDueDate());

        if(!tasks.stream().filter(t -> t.getId().equals(newId)).findAny().equals(tasks.isEmpty())) {
            return updateTask(newId, new UpdateTaskRequestDTO(createTaskDTO.getDueDate(), false));
        }
        else {
            Task task = new Task(id++, createTaskDTO.getName(), createTaskDTO.getDueDate(), false);
            tasks.add(task);
            return task;
        }
    }

    public Task updateTask(Integer id, UpdateTaskRequestDTO updateTaskDTO) {
        if (updateTaskDTO.getDueDate().compareTo(java.time.LocalDate.now()) < 0)
            throw new IllegalArgumentException(updateTaskDTO.getDueDate());

        Task task = getTaskById(id);
        if(updateTaskDTO.getDueDate() != null) {
            task.setDueDate(updateTaskDTO.getDueDate());
        }
        if(updateTaskDTO.getCompleted() != null) {
            task.setCompleted(updateTaskDTO.getCompleted());
        }
        return task;
    }

    public void deleteTask(Integer id) {
        Task task = getTaskById(id);
        tasks.remove(task);
    }

    public Integer deleteTask(Boolean completed) {
        List<Task> updatedTaskList = new ArrayList<Task>();
        Integer countDeletedTasks = 0;

        for(Task task : tasks) {
            if(task.getCompleted()) {
                updatedTaskList.add(task);
            }
            else {
                countDeletedTasks++;
            }
        }
        tasks = updatedTaskList;
        return countDeletedTasks;
    }

    // TODO: in error responses send the error message in a json object
    public static class TaskNotFoundException extends IllegalStateException {
        public TaskNotFoundException(Integer id) {
            super("Task with id : " + id + " not found");
        }
    }

    public static class IllegalArgumentException extends java.lang.IllegalArgumentException {
        public IllegalArgumentException(LocalDate dueDate) {
            super("Given dueDate : " + dueDate + " is less than the current date : " + new Date());
        }

        public IllegalArgumentException(String name) {
            super("Given name : " + name + " has less than 5 or more than 100 characters");
        }
    }
}
