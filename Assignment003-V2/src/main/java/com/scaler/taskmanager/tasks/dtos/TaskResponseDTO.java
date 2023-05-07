package com.scaler.taskmanager.tasks.dtos;

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
}
