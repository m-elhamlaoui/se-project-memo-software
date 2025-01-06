package com.example.taskblock.repository;

import com.example.taskblock.model.task.Task;
import com.example.taskblock.model.task.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByStatus(TaskStatus status);//just an example
    List<Task> findByTaskBlockId(Long taskBlockId);

}

