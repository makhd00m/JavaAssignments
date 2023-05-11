package com.scaler.taskmanager.dtos.responseDTOS;

import com.scaler.taskmanager.models.Task;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TaskResponseDTO {
    private Integer id;
    private String name;
    private LocalDate dueDate;
    private Boolean completed;

    public TaskResponseDTO(Task task) {
        this.id = task.getId();
        this.name = task.getName();
        this.dueDate = task.getDueDate();
        this.completed = task.getCompleted();
    }
}
