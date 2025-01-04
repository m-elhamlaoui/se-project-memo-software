package com.example.taskblock.model.task;

import com.example.taskblock.model.taskblock.TaskBlock;
import jakarta.persistence.*;

@Entity
public class Task{
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING) // Stores the enum name (e.g., "PENDING") in the database
    private TaskStatus status;

    @ManyToOne
    @JoinColumn(name = "taskblock_id", nullable = false)
    private TaskBlock taskBlock;

    public Task() {}

    public Task(String title, String description, TaskStatus status, TaskBlock taskBlock) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.taskBlock = taskBlock;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public TaskBlock getTaskBlock() {
        return taskBlock;
    }

    public void setTaskBlock(TaskBlock taskBlock) {
        this.taskBlock = taskBlock;
    }


}
