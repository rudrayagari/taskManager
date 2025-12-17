package com.example.taskmanager.model;

import java.util.UUID;

public class Task {
    private UUID id;
    private String title;
    private boolean completed;

    public Task() {
        // Default constructor for deserialization
    }

    public Task(String title) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.completed = false;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
