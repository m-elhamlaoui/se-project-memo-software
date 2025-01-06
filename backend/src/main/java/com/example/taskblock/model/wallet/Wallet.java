package com.example.taskblock.model.wallet;

import com.example.taskblock.model.taskblock.TaskBlock;
import com.example.taskblock.model.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "wallets")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "taskblock_id", nullable = false)
    private TaskBlock taskBlock;

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TaskBlock getTaskBlock() {
        return taskBlock;
    }

    public void setTaskBlock(TaskBlock taskBlock) {
        this.taskBlock = taskBlock;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(nullable = false)
    private BigDecimal balance;

    // Constructors
    public Wallet() {}

    public Wallet(User user, TaskBlock taskBlock, BigDecimal initialBalance) {
        this.user = user;
        this.taskBlock = taskBlock;
        this.balance = initialBalance;
    }

    // Methods
    public void addFunds(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }

    public void deductFunds(BigDecimal amount) {
        if (this.balance.compareTo(amount) < 0) {
            throw new IllegalStateException("Insufficient balance");
        }
        this.balance = this.balance.subtract(amount);
    }

    public boolean hasSufficientBalance(BigDecimal requiredAmount) {
        return this.balance.compareTo(requiredAmount) >= 0;
    }
}
