package com.scaler.taskmanager.services;

import com.scaler.taskmanager.models.Task;
import com.scaler.taskmanager.dtos.requestDTOS.CreateTaskRequestDTO;
import com.scaler.taskmanager.dtos.requestDTOS.UpdateTaskRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class TasksService {
    private final List<Task> tasksList = new ArrayList<>();
    private Integer id = 0;

    public List<Task> getAllTasks(String sortOrder, TaskFilter taskFilter) {
        if(taskFilter == null) {
            return tasksList;
        }

        var filteredTasks = new ArrayList<>(tasksList.stream().filter(task -> {
            if (taskFilter.beforeDate != null && task.getDueDate().isAfter(taskFilter.beforeDate)) {
                return false;
            }
            if (taskFilter.afterDate != null && task.getDueDate().isBefore(taskFilter.afterDate)) {
                return false;
            }
            if (taskFilter.completed != null && task.getCompleted() != taskFilter.completed) {
                return false;
            }
            return true;
        }).toList());

        filteredTasks.sort(new Sorter());
        return filteredTasks;
    }

    public Task getTaskById(Integer id) {
        for(Task task : tasksList) {
            if(task.getId().equals(id)) {
                return task;
            }
        }
        throw new TaskNotFoundException(id);
    }

    public Task createTask(CreateTaskRequestDTO createTaskRequestDTO) {
        validateTask(createTaskRequestDTO.getName());
        validateTask(createTaskRequestDTO.getDueDate());

        Task task = new Task(id++, createTaskRequestDTO.getName(), createTaskRequestDTO.getDueDate(), false);
        tasksList.add(task);
        return task;
    }

    public Task createTask(Integer newId, CreateTaskRequestDTO createTaskRequestDTO) {
        validateTask(createTaskRequestDTO.getName());
        validateTask(createTaskRequestDTO.getDueDate());

        if(tasksList.stream().anyMatch(t -> t.getId().equals(newId))) {
            return updateTask(newId, new UpdateTaskRequestDTO(createTaskRequestDTO.getDueDate(), false));
        }
        else {
            Task task = new Task(id++, createTaskRequestDTO.getName(), createTaskRequestDTO.getDueDate(), false);
            tasksList.add(task);
            return task;
        }
    }

    public Task updateTask(Integer id, UpdateTaskRequestDTO updateTaskRequestDTO) {
        validateTask(updateTaskRequestDTO.getDueDate());

        Task task = getTaskById(id);
        if(updateTaskRequestDTO.getDueDate() != null) {
            task.setDueDate(updateTaskRequestDTO.getDueDate());
        }
        if(updateTaskRequestDTO.getCompleted() != null) {
            task.setCompleted(updateTaskRequestDTO.getCompleted());
        }
        return task;
    }

    public void deleteTask(Integer id) {
        Task task = getTaskById(id);
        tasksList.remove(task);
    }

    public Integer deleteTask(Boolean completed) {
        List<Task> updatedTaskList = tasksList.stream().filter(task -> !task.getCompleted()).toList();
        Integer countDeletedTasks = tasksList.size() - updatedTaskList.size();
        tasksList.clear();
        tasksList.addAll(updatedTaskList);
        return countDeletedTasks;
    }

    public void validateTask(LocalDate dueDate) {
        if(dueDate.isBefore(LocalDate.now()))
            throw new IllegalArgumentException(dueDate);
    }

    public void validateTask(String name) {
        if(name.length() < 5 || name.length() > 100)
            throw new IllegalArgumentException(name);
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
            return new TaskFilter(beforeDate, afterDate, completed);
        }
    }

    static class Sorter implements Comparator<Task> {
        public int compare(Task t1, Task t2) {
            if (t1.getDueDate().isBefore(t2.getDueDate()))
                return 1;
            else if (t1.getDueDate().isEqual(t2.getDueDate()))
                return 0;
            else
                return -1;
        }
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
