package com.example.taskblock.model.vote;

import com.example.taskblock.model.task.Task;
import com.example.taskblock.model.user.Member;
import com.example.taskblock.model.user.User;
import jakarta.persistence.*;

@Entity
@Table(name = "votes")
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Member getSender() {
        return (Member) sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @Column(nullable = false)
    private boolean value; // True for upvote, False for downvote

    // Constructors
    public Vote() {}

    public Vote(Task task, User sender, boolean value) {
        this.task = task;
        this.sender = sender;
        this.value = value;
    }
}
