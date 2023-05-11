package com.scaler.taskmanager.controllers;

import com.scaler.taskmanager.exceptions.TaskNotFoundException;
import com.scaler.taskmanager.exceptions.IllegalArgumentException;
import com.scaler.taskmanager.models.Task;
import com.scaler.taskmanager.services.TasksService;
import com.scaler.taskmanager.dtos.requestDTOS.CreateTaskRequestDTO;
import com.scaler.taskmanager.dtos.responseDTOS.TaskResponseDTO;
import com.scaler.taskmanager.dtos.requestDTOS.UpdateTaskRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TasksController {
    private final TasksService tasksService;

    public TasksController(@Autowired TasksService tasksService) {
        this.tasksService = tasksService;
    }

    // ----------------------------------------------------------------
    //                           GET
    // ----------------------------------------------------------------
    @GetMapping("")
    ResponseEntity<List<TaskResponseDTO>> getAllTasks(
            @RequestParam(name = "sort", required = false) String sortOrder,
            @RequestBody TasksService.TaskFilter filter
    ) {
        TasksService.TaskFilter taskFilter = TasksService.TaskFilter.fromQueryParams(
                filter.getBeforeDate(),
                filter.getAfterDate(),
                filter.getCompleted()
        );
        var tasks = tasksService.getAllTasks(sortOrder, filter);

        List<TaskResponseDTO> taskResponseDTOS = new ArrayList<>();

        for(Task task : tasks) {
            TaskResponseDTO taskResponseDTO = new TaskResponseDTO(task);
            taskResponseDTOS.add(taskResponseDTO);
        }
        return ResponseEntity.ok(taskResponseDTOS);
    }

    @GetMapping("/{id}")
    ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable("id") Integer id) {
        var task = tasksService.getTaskById(id);

        TaskResponseDTO taskResponseDTO = new TaskResponseDTO(task);
        return ResponseEntity.ok(taskResponseDTO);
    }

    // ----------------------------------------------------------------
    //                           POST
    // ----------------------------------------------------------------
    @PostMapping("")
    ResponseEntity<TaskResponseDTO> createTask(@RequestBody CreateTaskRequestDTO createTaskDTO) {
        var task = tasksService.createTask(createTaskDTO);

        TaskResponseDTO taskResponseDTO = new TaskResponseDTO(task);
        return ResponseEntity.ok(taskResponseDTO);
    }

    // ----------------------------------------------------------------
    //                           PATCH
    // ----------------------------------------------------------------
    @PatchMapping("/{id}")
    ResponseEntity<TaskResponseDTO> updateTask(@PathVariable("id") Integer id, @RequestBody UpdateTaskRequestDTO updateTaskDTO) {
        var task = tasksService.updateTask(id, updateTaskDTO);

        TaskResponseDTO taskResponseDTO = new TaskResponseDTO(task);
        return ResponseEntity.ok(taskResponseDTO);
    }

    // ----------------------------------------------------------------
    //                           DELETE
    // ----------------------------------------------------------------
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("")
    ResponseEntity<String> deleteCompletedTasks(
            @RequestParam(value = "completed", required = true) Boolean completed
    ) {
        Integer numberOfTasksDeleted = tasksService.deleteTask(completed);
        return ResponseEntity.ok("Number of tasks deleted : " + numberOfTasksDeleted);
    }

    @DeleteMapping({"/{id}"})
    ResponseEntity<Void> deleteTaskById(@PathVariable("id") Integer id) {
        tasksService.deleteTask(id);
        Void voidExp = null;
        return ResponseEntity.ok(voidExp);
    }

    // ----------------------------------------------------------------
    //                           PUT
    // ----------------------------------------------------------------
    @PutMapping("")
    ResponseEntity<TaskResponseDTO> createTask(
            @RequestParam(value = "id") Integer id,
            @RequestBody CreateTaskRequestDTO createTaskDTO
    ) {
        var task = tasksService.createTask(id, createTaskDTO);
        TaskResponseDTO taskResponseDTO = new TaskResponseDTO(task);
        return ResponseEntity.ok(taskResponseDTO);
    }

    // ----------------------------------------------------------------
    //                         EXCEPTIONS
    // ----------------------------------------------------------------
    @ExceptionHandler(
            {
                    TaskNotFoundException.class,
                    IllegalArgumentException.class
            }
    )
    ResponseEntity<String> handleTaskNotFoundOrIllegalArgumentException(Exception e) {
        return ResponseEntity.internalServerError().body(
                "Server side error " + HttpStatus.NOT_FOUND + " : " + e.getMessage()
        );
    }
}
