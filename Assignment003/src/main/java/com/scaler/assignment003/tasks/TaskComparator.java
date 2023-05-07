package com.scaler.assignment003.tasks;

import java.util.Comparator;

public class TaskComparator implements Comparator<Task> {

    public int compare(Task t1, Task t2) {
        if(t1.getDueDate().compareTo(t2.getDueDate()) < 0)
            return 1;
        else if(t1.getDueDate().compareTo(t2.getDueDate()) == 0)
            return 0;
        else
            return -1;
    }
}
