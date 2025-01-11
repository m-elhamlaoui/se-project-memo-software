package com.example.taskblock.repository;

import com.example.taskblock.model.task.Task;
import com.example.taskblock.model.task.TaskStatus;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByStatus(TaskStatus status);//just an example
    List<Task> findByTaskBlockId(Long taskBlockId);


}

