package com.example.taskblock.service;

import com.example.taskblock.model.task.Task;
import com.example.taskblock.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public List<Task> getTasksByTaskBlockId(Long taskBlockId) {
        return taskRepository.findByTaskBlockId(taskBlockId);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
