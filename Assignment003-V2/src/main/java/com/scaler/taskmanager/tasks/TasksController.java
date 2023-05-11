package com.scaler.taskmanager.tasks;

import com.scaler.taskmanager.tasks.dtos.CreateTaskDTO;
import com.scaler.taskmanager.tasks.dtos.TaskResponseDTO;
import com.scaler.taskmanager.tasks.dtos.UpdateTaskDTO;
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

    @GetMapping("")
    ResponseEntity<List<TaskResponseDTO>> getAllTasks(@RequestBody TasksService.TaskFilter filter) {
        TasksService.TaskFilter taskFilter = TasksService.TaskFilter.fromQueryParams(filter.beforeDate, filter.afterDate, filter.completed);
        var tasks = tasksService.getAllTasks(filter);

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

    @PostMapping("")
    ResponseEntity<TaskResponseDTO> createTask(@RequestBody CreateTaskDTO createTaskDTO) {
        var task = tasksService.createTask(createTaskDTO);

        TaskResponseDTO taskResponseDTO = new TaskResponseDTO(task);
        return ResponseEntity.ok(taskResponseDTO);
    }

    @PatchMapping("/{id}")
    ResponseEntity<TaskResponseDTO> updateTask(@PathVariable("id") Integer id, @RequestBody UpdateTaskDTO updateTaskDTO) {
        var task = tasksService.updateTask(id, updateTaskDTO);

        TaskResponseDTO taskResponseDTO = new TaskResponseDTO(task);
        return ResponseEntity.ok(taskResponseDTO);
    }

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

    @ExceptionHandler(
            {
                    TasksService.TaskNotFoundException.class,
                    TasksService.IllegalArgumentException.class
            }
    )
    ResponseEntity<String> handleTaskNotFoundException(Exception e) {
        return ResponseEntity.notFound().build();
    }
}
