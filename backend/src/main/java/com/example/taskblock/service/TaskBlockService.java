package com.example.taskblock.service;

import com.example.taskblock.model.task.Task;
import com.example.taskblock.model.taskblock.TaskBlock;
import com.example.taskblock.repository.TaskBlockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskBlockService {

    private final TaskBlockRepository taskBlockRepository;

    @Autowired
    public TaskBlockService(TaskBlockRepository taskBlockRepository) {
        this.taskBlockRepository = taskBlockRepository;
    }

    public TaskBlock createTaskBlock(TaskBlock taskBlock) {
        return taskBlockRepository.save(taskBlock);
    }

    public Optional<TaskBlock> getTaskBlockById(Long id) {
        return taskBlockRepository.findById(id);
    }

    public List<TaskBlock> getAllTaskBlocks() {
        return taskBlockRepository.findAll();
    }

    public void deleteTaskBlock(Long id) {
        taskBlockRepository.deleteById(id);
    }

    public void addTaskToTaskBlock(TaskBlock taskBlock, Task task) {
        taskBlock.addTask(task);
        taskBlockRepository.save(taskBlock);
    }
}
