package com.example.taskmanager.controller;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Map<String, String> body) {
        String title = body.get("title");
        if (title == null || title.trim().isEmpty()) {
            // BUG FIXED: Changed response from 200 OK to 400 BAD_REQUEST for missing/empty title.
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        Task task = taskService.addTask(title);
        return new ResponseEntity<>(task, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<?> markTaskComplete(@PathVariable String id) {
        try {
            UUID uuid = UUID.fromString(id);
            Optional<Task> updatedTask = taskService.markTaskComplete(uuid);
            if (updatedTask.isPresent()) {
                return ResponseEntity.ok(updatedTask.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (IllegalArgumentException e) {
            // Respond clearly for invalid (non-UUID) IDs
            Map<String, String> error = new HashMap<>();
            error.put("error", "Invalid UUID format");
            error.put("example", "123e4567-e89b-12d3-a456-426614174000");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
}
