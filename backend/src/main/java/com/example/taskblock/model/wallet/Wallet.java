package com.example.taskblock.model.wallet;

import com.example.taskblock.model.taskblock.TaskBlock;
import com.example.taskblock.model.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

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
    @JsonIgnore
    private TaskBlock taskBlock;

    @JsonProperty("taskBlock")
    public Map<String, Object> getTaskBlockJson() {
        Map<String, Object> taskBlockJson = new HashMap<>();
        taskBlockJson.put("id", taskBlock.getId());
        taskBlockJson.put("name", taskBlock.getName());
        return taskBlockJson;
    }









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
    private BigDecimal balance = new BigDecimal(0); 

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
