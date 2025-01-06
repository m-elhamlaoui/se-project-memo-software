package com.example.taskblock.model.taskblock;

import com.example.taskblock.model.task.Task;
import com.example.taskblock.model.user.User;
import com.example.taskblock.model.wallet.Wallet;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "task_blocks")
public class TaskBlock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "taskBlock", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>(); // Initialize as empty list

    @OneToMany(mappedBy = "taskBlock", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Wallet> wallets = new ArrayList<>(); // Initialize as empty list

    // Constructors
    public TaskBlock() {}

    public TaskBlock(String name, User creator) {
        this.name = name;
        this.creator = creator;
    }

    // Methods
    public void addTask(Task task) {
        task.setTaskBlock(this);
        this.tasks.add(task);
    }

    public void removeTask(Task task) {
        this.tasks.remove(task);
        task.setTaskBlock(null);
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

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

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Wallet> getWallets() {
        return wallets;
    }

    public void setWallets(List<Wallet> wallets) {
        this.wallets = wallets;
    }

    public void addWallet(Wallet wallet) {
        wallet.setTaskBlock(this);
        this.wallets.add(wallet);
    }

    public void removeWallet(Wallet wallet) {
        this.wallets.remove(wallet);
        wallet.setTaskBlock(null);
    }
}
