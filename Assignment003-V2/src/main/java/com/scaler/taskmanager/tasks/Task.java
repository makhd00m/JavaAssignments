package com.scaler.taskmanager.tasks;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

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
