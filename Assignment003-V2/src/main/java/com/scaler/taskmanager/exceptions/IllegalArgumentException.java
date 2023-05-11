package com.scaler.taskmanager.exceptions;

import java.time.LocalDate;
import java.util.Date;

public class IllegalArgumentException extends java.lang.IllegalArgumentException {
    public IllegalArgumentException(LocalDate dueDate) {
        super("Given dueDate : " + dueDate + " is less than the current date : " + new Date());
    }

    public IllegalArgumentException(String name) {
        super("Given name : " + name + " has less than 5 or more than 100 characters");
    }
}
