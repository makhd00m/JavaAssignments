package com.scaler.taskmanager.exceptions;

import com.scaler.taskmanager.dtos.responseDTOS.TaskExceptionResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;

@RestControllerAdvice
public class TaskManagerExceptionHandler {

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<TaskExceptionResponseDTO> handleTaskNotFoundException(TaskNotFoundException e) {
        TaskExceptionResponseDTO response = new TaskExceptionResponseDTO();
        response.setTimestamp(LocalDate.now());
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setError(e.getLocalizedMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<TaskExceptionResponseDTO>  handleIllegalArgumentException(IllegalArgumentException e) {
        TaskExceptionResponseDTO response = new TaskExceptionResponseDTO();
        response.setTimestamp(LocalDate.now());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setError(e.getLocalizedMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
