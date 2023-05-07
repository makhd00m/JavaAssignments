package com.scaler.taskmanager.tasks.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateTaskDTO {
    LocalDate dueDate;
    Boolean completed;
}
