package com.scaler.taskmanager.dtos.requestDTOS;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateTaskRequestDTO {
    String name;
    LocalDate dueDate;
}
