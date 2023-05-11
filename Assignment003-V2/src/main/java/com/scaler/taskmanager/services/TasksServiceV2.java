package com.scaler.taskmanager.services;

import com.scaler.taskmanager.exceptions.TaskNotFoundException;
import com.scaler.taskmanager.exceptions.IllegalArgumentException;
import com.scaler.taskmanager.models.Task;
import com.scaler.taskmanager.dtos.requestDTOS.CreateTaskRequestDTO;
import com.scaler.taskmanager.dtos.requestDTOS.UpdateTaskRequestDTO;
import com.scaler.taskmanager.repositories.TasksRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class TasksServiceV2 {
    private final TasksRepository tasksRepository;

    public TasksServiceV2(@Autowired TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    // ----------------------------------------------------------------
    //                    RETURN_TASKS
    // ----------------------------------------------------------------
    public List<Task> getAllTasks(String sortOrder, TaskFilter taskFilter) {
        var tasksList = tasksRepository.getTasksList();
        if(taskFilter == null) {
            return tasksList;
        }
        var filteredTasks = filterTasks(taskFilter, tasksList);
        return sortTasks(sortOrder, filteredTasks);
    }

    public Task getTaskById(Integer id) {
        var tasksList = tasksRepository.getTasksList();
        for(Task task : tasksList) {
            if(task.getId().equals(id)) {
                return task;
            }
        }
        throw new TaskNotFoundException(id);
    }

    // ----------------------------------------------------------------
    //                         CREATE_TASK
    // ----------------------------------------------------------------
    public Task createTask(CreateTaskRequestDTO createTaskRequestDTO) {
        validateTask(createTaskRequestDTO.getName());
        validateTask(createTaskRequestDTO.getDueDate());

        Task task = new Task(
                tasksRepository.getId(),
                createTaskRequestDTO.getName(),
                createTaskRequestDTO.getDueDate(),
                false
        );
        tasksRepository.getTasksList().add(task);
        tasksRepository.setId(tasksRepository.getId() + 1);
        return task;
    }

    public Task createTask(Integer newId, CreateTaskRequestDTO createTaskRequestDTO) {
        validateTask(createTaskRequestDTO.getName());
        validateTask(createTaskRequestDTO.getDueDate());

        var tasksList = tasksRepository.getTasksList();
        if(tasksList.stream().anyMatch(t -> t.getId().equals(newId))) {
            return updateTask(newId, new UpdateTaskRequestDTO(createTaskRequestDTO.getDueDate(), false));
        }

        return createTask(createTaskRequestDTO);
    }

    // ----------------------------------------------------------------
    //                         UPDATE_TASK
    // ----------------------------------------------------------------
    public Task updateTask(Integer id, UpdateTaskRequestDTO updateTaskRequestDTO) {
        validateTask(updateTaskRequestDTO.getDueDate());

        var task = getTaskById(id);
        if(updateTaskRequestDTO.getDueDate() != null) {
            task.setDueDate(updateTaskRequestDTO.getDueDate());
        }
        if(updateTaskRequestDTO.getCompleted() != null) {
            task.setCompleted(updateTaskRequestDTO.getCompleted());
        }
        return task;
    }

    public void deleteTask(Integer id) {
        var tasksList = tasksRepository.getTasksList();
        Task task = getTaskById(id);
        tasksList.remove(task);
    }

    // ----------------------------------------------------------------
    //                         DELETE_TASK
    // ----------------------------------------------------------------
    public Integer deleteTask() {
        var tasksList = tasksRepository.getTasksList();
        List<Task> updatedTaskList = tasksList.stream().filter(task -> !task.getCompleted()).toList();

        Integer countDeletedTasks = tasksList.size() - updatedTaskList.size();

        tasksList.clear();
        tasksList.addAll(updatedTaskList);

        return countDeletedTasks;
    }

    // ----------------------------------------------------------------
    //           FUNCTIONS(VALIDATION + FILTERING + SORTING)
    // ----------------------------------------------------------------
    public void validateTask(LocalDate dueDate) {
        if(dueDate.isBefore(LocalDate.now()))
            throw new IllegalArgumentException(dueDate);
    }

    public void validateTask(String name) {
        if(name.length() < 5 || name.length() > 100)
            throw new IllegalArgumentException(name);
    }

    public List<Task> filterTasks(TaskFilter taskFilter, List<Task> tasksList) {
        return new ArrayList<>(tasksList.stream().filter(task -> {
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
    }

    public List<Task> sortTasks(String sortOrder, List<Task> filteredTasks) {
        if(sortOrder != null) {
            filteredTasks.sort(new TasksService.Sorter());

            if (sortOrder.equals("dateDesc"))
                Collections.reverse(filteredTasks);

            return filteredTasks;
        }
        return filteredTasks;
    }

    // ----------------------------------------------------------------
    //                    FILTER_CLASS
    // ----------------------------------------------------------------
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

    // ----------------------------------------------------------------
    //                    SORTING_COMPARATOR
    // ----------------------------------------------------------------
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
}
