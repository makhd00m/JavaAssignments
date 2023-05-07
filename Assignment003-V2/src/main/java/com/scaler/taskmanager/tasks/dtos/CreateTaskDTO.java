package com.scaler.taskmanager.tasks.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateTaskDTO {
    String name;
    LocalDate dueDate;
}
