package com.example.taskmanager;

import com.example.taskmanager.controller.TaskController;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    private UUID testId;
    private Task testTask;

    @BeforeEach
    void setup() {
        testId = UUID.randomUUID();
        testTask = new Task("Test task");
        testTask.setId(testId);
    }

    @Test
    void completeTask_happyPath() throws Exception {
        testTask.setCompleted(true); // Ensure completed state for correct result
        when(taskService.markTaskComplete(testId)).thenReturn(Optional.of(testTask));

        mockMvc.perform(put("/tasks/" + testId + "/complete"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.completed").value(true));
    }

    @Test
    void completeNonExistentTask_returnsNotFound() throws Exception {
        when(taskService.markTaskComplete(any(UUID.class))).thenReturn(Optional.empty());

        mockMvc.perform(put("/tasks/" + UUID.randomUUID() + "/complete"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllTasks_returnsTaskList() throws Exception {
        List<Task> tasks = List.of(testTask);
        when(taskService.getAllTasks()).thenReturn(tasks);

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(testId.toString()))
                .andExpect(jsonPath("$[0].title").value("Test task"))
                .andExpect(jsonPath("$[0].completed").value(false));
    }

    @Test
    void createTask_happyPath() throws Exception {
        String json = "{\"title\":\"New Task\"}";
        Task newTask = new Task("New Task");
        when(taskService.addTask("New Task")).thenReturn(newTask);

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("New Task"))
                .andExpect(jsonPath("$.completed").value(false));
    }

    @Test
    void createTask_withEmptyTitle_returnsBadRequest() throws Exception {
        String json = "{\"title\":\"\"}";
        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void markTaskComplete_invalidUUID_returnsBadRequest() throws Exception {
        mockMvc.perform(put("/tasks/not-a-uuid/complete"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid UUID format"));
    }
}
