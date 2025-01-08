package com.example.taskblock.service;

import com.example.taskblock.model.task.Task;
import com.example.taskblock.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final NotificationService notificationService;

    @Autowired
    public TaskService(TaskRepository taskRepository, NotificationService notificationService) {
        this.taskRepository = taskRepository;
        this.notificationService = notificationService;
    }

    public Task createTask(Task task) {
        if (task.getDurationSeconds() < 0) {
            throw new IllegalArgumentException("Duration cannot be negative");
        }

        task.addObserver(notificationService);
        return taskRepository.save(task);
    }

    public Optional<Task> getTaskById(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        task.ifPresent(t -> t.addObserver(notificationService));
        return task;
    }

    public List<Task> getTasksByTaskBlockId(Long taskBlockId) {
        return taskRepository.findByTaskBlockId(taskBlockId);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
