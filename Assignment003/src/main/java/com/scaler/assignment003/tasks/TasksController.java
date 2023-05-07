package com.scaler.assignment003.tasks;

import com.scaler.assignment003.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TasksController {

    List<Task> taskList = new ArrayList<>();
    private int nextTaskId = 1;

    void reverseList(List<Task> list) {
        if(list.size() < 1 || list == null)
            return;

        Task task = list.remove(0);
        reverseList(list);
        list.add(task);
    }

    @GetMapping("")
    List<Task> getAllTasks(@RequestParam("completed") Boolean completed, @RequestParam("sort") String sort) {
        if(completed) {
            List<Task> completeTaskList = new ArrayList<>();

            for (Task task : taskList) {
                if(task.getCompleted()== true)
                    completeTaskList.add(task);
            }

            Collections.sort(completeTaskList, new TaskComparator());

            if(sort == "dateAsc") {
                reverseList(completeTaskList);
            }

            return completeTaskList;
        }
        else {
            List<Task> incompleteTaskList = new ArrayList<>();

            for(Task task : taskList) {
                if(task.getCompleted() == false)
                    incompleteTaskList.add(task);
            }

            Collections.sort(incompleteTaskList, new TaskComparator());

            if(sort == "dateAsc") {
                reverseList(incompleteTaskList);
            }

            return incompleteTaskList;
        }
    }

    @PostMapping("")
    Task createTask(@RequestBody Task task) {

        //Error if name is missing
        //Error if due date is missing and or invalid (before today)
        if(task.getName() == null || task.getDueDate() == null || task.getDueDate().compareTo(java.time.LocalDate.now()) < 0)
            throw new IllegalArgumentException("Provide valid parameters");

        task.setId(nextTaskId++);
        taskList.add(task);
        return task;
    }

    @GetMapping("/{id}")
    Task getTask(@PathVariable("id") Integer id) {
        var foundTask = taskList.stream().filter(
                task -> task.getId().equals(id)
        ).findFirst().orElse(null);

        // foundTask is null, throw 404 error
        if(foundTask != null)
            return foundTask;
        else
            throw new ResourceNotFoundException();
    }

    @PatchMapping("/update/{id}")
    Task updateTask(@RequestBody Task updateTask, @PathVariable("id") Integer id) {
        var foundTask = taskList.stream().filter(
                task -> task.getId().equals(id)
        ).findFirst().orElse(null);

        if(foundTask != null) {
            foundTask.setCompleted(updateTask.getCompleted());
            foundTask.setDueDate(updateTask.getDueDate());
        }
        else {
            throw new ResourceNotFoundException();
        }

        return foundTask;
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<Integer> deleteTask(@PathVariable("id") Integer id) {
        var foundTask = taskList.stream().filter(
                task -> task.getId().equals(id)
        ).findFirst().orElse(null);

        if(foundTask != null) {
            var isRemoved = taskList.remove(foundTask);

            if(!isRemoved)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            else
                return new ResponseEntity<>(id, HttpStatus.OK);
        }
        else {
            throw new ResourceNotFoundException();
        }
    }

    @DeleteMapping("")
    List<Task> deleteCompleted(@RequestParam("completed") Boolean completed) {
        List<Task> updatedList = new ArrayList<>();

        for(Task task : taskList) {
            if(!task.getCompleted())
                updatedList.add(task);
        }

        taskList = updatedList;

        return taskList;
    }
}
