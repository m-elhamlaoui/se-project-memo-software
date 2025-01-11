package com.example.taskblock.model.task;

import com.example.taskblock.model.notification.TaskObserver;
import com.example.taskblock.model.notification.TaskSubject;
import com.example.taskblock.model.taskblock.TaskBlock;
import com.example.taskblock.model.taskblock.TaskBlockGroup;
import com.example.taskblock.model.user.Member;
import com.example.taskblock.model.vote.Vote;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Entity
@Table(name = "tasks")
public class Task implements TaskSubject {

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
    @JsonIgnore
    private TaskBlock taskBlock;



    @Column(name = "duration_seconds", nullable = false)
    private Integer durationSeconds = 0;

    // Add getter and setter
    public Integer getDurationSeconds() {
        return durationSeconds;
    }

    public void setDurationSeconds(Integer durationSeconds) {
        this.durationSeconds = durationSeconds;
    }

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
        TaskStatus oldStatus = this.status;
        this.status = status;

        if (oldStatus == TaskStatus.PENDING) {
            if (status == TaskStatus.ACCEPTED) {
                notifyTaskAccepted(this);
            } else if (status == TaskStatus.REJECTED) {
                notifyTaskRejected(this);
            }
        }
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

    public List<TaskObserver> getObservers() {
        return observers;
    }

    public void setObservers(List<TaskObserver> observers) {
        this.observers = observers;
    }

    public List<TaskBlockGroup> getTaggedGroups() {
        return taggedGroups;
    }

    public void setTaggedGroups(List<TaskBlockGroup> taggedGroups) {
        this.taggedGroups = taggedGroups;
    }

    public List<Member> getTaggedIndividuals() {
        return taggedIndividuals;
    }

    public void setTaggedIndividuals(List<Member> taggedIndividuals) {
        this.taggedIndividuals = taggedIndividuals;
    }

    public Task(String title, String description, TaskBlock taskBlock, Integer durationSeconds) {
        this.title = title;
        this.description = description;
        this.status = TaskStatus.PENDING;
        this.taskBlock = taskBlock;
        this.durationSeconds = durationSeconds;
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
    @PreRemove
    private void cleanupObservers() {
        observers.clear();
    }
    @Transient
    private List<TaskObserver> observers = new ArrayList<>();

    @Override
    public void addObserver(TaskObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(TaskObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyVoteCast(Task task, Member voter) {
        for (TaskObserver observer : observers) {
            observer.onVoteCast(task, voter);
        }
    }

    @Override
    public void notifyTaskAccepted(Task task) {
        for (TaskObserver observer : observers) {
            observer.onTaskAccepted(task);
        }
    }

    @Override
    public void notifyTaskRejected(Task task) {
        for (TaskObserver observer : observers) {
            observer.onTaskRejected(task);
        }
    }

    public void addVote(Vote vote) {
        vote.setTask(this);
        this.votes.add(vote);
        notifyVoteCast(this, vote.getSender());
    }

    @ManyToMany
    @JoinTable(
            name = "task_tagged_individuals",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id")
    )
    @JsonIgnore
    @OrderColumn(name = "individual_order")
    private List<Member> taggedIndividuals = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "task_tagged_groups",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    @JsonIgnore
    @OrderColumn(name = "group_order")
    private List<TaskBlockGroup> taggedGroups = new ArrayList<>();

    // Helper methods for list management
    public void addTaggedIndividual(Member member) {
        if (!taggedIndividuals.contains(member)) {
            taggedIndividuals.add(member);
        }
    }

    public void removeTaggedIndividual(Member member) {
        taggedIndividuals.remove(member);
    }

    public void addTaggedGroup(TaskBlockGroup group) {
        if (!taggedGroups.contains(group)) {
            taggedGroups.add(group);
        }
    }

    public void removeTaggedGroup(TaskBlockGroup group) {
        taggedGroups.remove(group);
    }

}
