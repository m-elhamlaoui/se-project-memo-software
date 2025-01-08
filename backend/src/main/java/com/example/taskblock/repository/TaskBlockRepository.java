package com.example.taskblock.repository;

import com.example.taskblock.model.taskblock.TaskBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskBlockRepository extends JpaRepository<TaskBlock, Long> {
    // Add custom methods if needed
}
