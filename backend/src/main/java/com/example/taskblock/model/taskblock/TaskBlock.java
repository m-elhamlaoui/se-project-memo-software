package com.example.taskblock.model.taskblock;

import com.example.taskblock.model.task.Task;
import com.example.taskblock.model.user.User;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class TaskBlock {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    @OneToMany(mappedBy = "taskBlock", cascade = CascadeType.ALL)
    private List<Task> tasks;

    // Constructors
    public TaskBlock() {}

    public TaskBlock(String name, User creator) {
        this.name = name;
        this.creator = creator;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

}
