package com.scaler.taskmanager.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class Task {
    Integer id;
    String name;
    @Setter
    LocalDate dueDate;
    @Setter
    Boolean completed;
}
