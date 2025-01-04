package com.example.taskblock.service;

import com.example.taskblock.model.task.Task;
import com.example.taskblock.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private BlockchainService blockchainService;

    public Task createTask(Task task) {
        // Save the task in the database
        Task savedTask = taskRepository.save(task);

        // Record the task on the blockchain
        try {
            String txHash = blockchainService.recordTaskOnBlockchain(
                    String.valueOf(savedTask.getId()),
                    savedTask.getTitle(),
                    savedTask.getDescription()
            );
            System.out.println("Blockchain transaction hash: " + txHash);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return savedTask;
    }
}
