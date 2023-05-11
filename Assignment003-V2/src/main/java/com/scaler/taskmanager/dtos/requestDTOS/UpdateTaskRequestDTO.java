package com.scaler.taskmanager.dtos.requestDTOS;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class UpdateTaskRequestDTO {
    LocalDate dueDate;
    Boolean completed;
}
