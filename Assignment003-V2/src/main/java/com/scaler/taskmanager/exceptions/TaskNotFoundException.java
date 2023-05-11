package com.scaler.taskmanager.exceptions;

public class TaskNotFoundException extends IllegalStateException {
    // TODO: in error responses send the error message in a json object
    public TaskNotFoundException(Integer id) {
        super("Task with id : " + id + " not found");
    }
}
