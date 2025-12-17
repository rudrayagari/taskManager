package com.example.taskmanager.service;

import com.example.taskmanager.model.Task;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TaskService {
    private final Map<UUID, Task> taskStore = new LinkedHashMap<>();

    public List<Task> getAllTasks() {
        return new ArrayList<>(taskStore.values());
    }

    public Task addTask(String title) {
        Task task = new Task(title);
        taskStore.put(task.getId(), task);
        return task;
    }

    public Optional<Task> markTaskComplete(UUID id) {
        Task task = taskStore.get(id);
        if (task != null) {
            task.setCompleted(true);
            return Optional.of(task);
        }
        return Optional.empty();
    }

    public Optional<Task> getTask(UUID id) {
        return Optional.ofNullable(taskStore.get(id));
    }
}
