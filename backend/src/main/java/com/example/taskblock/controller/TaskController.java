package com.example.taskblock.controller;

import com.example.taskblock.dto.TaskDto;
import com.example.taskblock.model.task.Task;
import com.example.taskblock.model.user.Member;
import com.example.taskblock.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/{taskBlockId}")
    public ResponseEntity<Task> createTask(
            @AuthenticationPrincipal Member currentUser,
            @PathVariable Long taskBlockId,
            @RequestBody TaskDto taskDto) {
        Task createdTask = taskService.createTask(currentUser, taskBlockId, taskDto);
        return ResponseEntity.ok(createdTask);
    }
}
