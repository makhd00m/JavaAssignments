package com.scaler.taskmanager.dtos.responseDTOS;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class TaskExceptionResponseDTO {
    private LocalDate timestamp;
    private int status;
    private String error;
}
