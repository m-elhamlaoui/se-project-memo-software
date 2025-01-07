package com.example.taskblock.model.task;

import com.example.taskblock.model.taskblock.TaskBlock;
import com.example.taskblock.model.vote.Vote;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status;

    @ManyToOne
    @JoinColumn(name = "taskblock_id", nullable = false)
    private TaskBlock taskBlock;

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now(); // Automatically set creation time


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vote> votes = new ArrayList<>();

    public Task() {}
    public Task(String title, String description, TaskBlock taskBlock) {
        this.title = title;
        this.description = description;
        this.status = TaskStatus.PENDING;
        this.taskBlock = taskBlock;
    }
    public Task(String title, String description, TaskStatus status, TaskBlock taskBlock) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.taskBlock = taskBlock;
    }

    // Methods
    public void addVote(Vote vote) {
        vote.setTask(this);
        this.votes.add(vote);
    }

    public void removeVote(Vote vote) {
        this.votes.remove(vote);
        vote.setTask(null);
    }
    public void setCreatedAt(LocalDateTime localDateTime) {
        this.createdAt=localDateTime;
    }

    public void checkAndRejectIfExpired() {
        if (status == TaskStatus.PENDING && taskBlock.getTimeRejectionStrategy().shouldReject(createdAt)) {
            this.status = TaskStatus.REJECTED;
        }
    }

}
