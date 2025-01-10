package com.example.taskblock.model.taskblock;

import com.example.taskblock.model.strategy.PercentageAcceptanceStrategy;
import com.example.taskblock.model.strategy.TimeRejectionStrategy;
import com.example.taskblock.model.task.Task;
import com.example.taskblock.model.task.TaskStatus;
import com.example.taskblock.model.user.User;
import com.example.taskblock.model.wallet.Wallet;
import com.example.taskblock.repository.VoteRepository;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "taskBlock", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    private List<Task> tasks = new ArrayList<>(); // Initialize as empty list to avoid null issues

    
    @OneToMany(mappedBy = "taskBlock", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    private List<Wallet> wallets = new ArrayList<>(); // Initialize as empty list to avoid null issues

    @Column(nullable = false)
    private double percentageToAccept = 0.0; // Default percentage (can be overridden later)

    @Column(nullable = false)
    private long voteDurationInSeconds = 0; // Default duration (can be overridden later)

    @Transient
    private TimeRejectionStrategy timeRejectionStrategy = null; // Default to null, set dynamically if needed

    @Transient
    private VoteRepository voteRepository = null; // Default to null, injected dynamically if needed

    @Transient
    private PercentageAcceptanceStrategy percentageAcceptanceStrategy = null; // Default to null, set dynamically if needed

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

    public PercentageAcceptanceStrategy getPercentageAcceptanceStrategy() {
        return percentageAcceptanceStrategy;
    }

    public void setPercentageAcceptanceStrategy(PercentageAcceptanceStrategy percentageAcceptanceStrategy) {
        this.percentageAcceptanceStrategy = percentageAcceptanceStrategy;
    }

    public double getPercentageToAccept() {
        return percentageToAccept;
    }

    public void setPercentageToAccept(double percentageToAccept) {
        this.percentageToAccept = percentageToAccept;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public TimeRejectionStrategy getTimeRejectionStrategy() {
        return timeRejectionStrategy;
    }

    public void setTimeRejectionStrategy(TimeRejectionStrategy timeRejectionStrategy) {
        this.timeRejectionStrategy = timeRejectionStrategy;
    }

    public long getVoteDurationInSeconds() {
        return voteDurationInSeconds;
    }

    public void setVoteDurationInSeconds(long voteDurationInSeconds) {
        this.voteDurationInSeconds = voteDurationInSeconds;
    }

    public VoteRepository getVoteRepository() {
        return voteRepository;
    }

    public void setVoteRepository(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    public List<Wallet> getWallets() {
        return wallets;
    }

    public void setWallets(List<Wallet> wallets) {
        this.wallets = wallets;
    }

    public void removeTask(Task task) {
        this.tasks.remove(task);
        task.setTaskBlock(null);
    }

    public void addWallet(Wallet wallet) {
        wallet.setTaskBlock(this);
        this.wallets.add(wallet);
    }

    public void removeWallet(Wallet wallet) {
        this.wallets.remove(wallet);
        wallet.setTaskBlock(null);
    }

    public void evaluateTask(Task task) {
        TaskStatus oldStatus = task.getStatus();
        task.checkAndRejectIfExpired();

        if (task.getStatus() == TaskStatus.PENDING && voteRepository != null && percentageAcceptanceStrategy != null) {
            Double votePercentage = voteRepository.calculateVotePercentage(task.getId());
            if (votePercentage != null && percentageAcceptanceStrategy.shouldAccept(votePercentage)) {
                task.setStatus(TaskStatus.ACCEPTED);
            }
        }
        if (task.getStatus() != oldStatus) {
            if (task.getStatus() == TaskStatus.ACCEPTED) {
                task.notifyTaskAccepted(task);
            } else if (task.getStatus() == TaskStatus.REJECTED) {
                task.notifyTaskRejected(task);
            }
        }
    }

}
