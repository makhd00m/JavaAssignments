package com.scaler.taskmanager.repositories;

import com.scaler.taskmanager.dtos.requestDTOS.CreateTaskRequestDTO;
import com.scaler.taskmanager.dtos.requestDTOS.UpdateTaskRequestDTO;
import com.scaler.taskmanager.models.Task;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Data
@Repository
public class TasksRepository {
    private final List<Task> tasksList = new ArrayList<>();
    private Integer id = 0;
}
