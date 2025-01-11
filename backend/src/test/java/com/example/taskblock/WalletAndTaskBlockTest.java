package com.example.taskblock;

import com.example.taskblock.model.task.Task;
import com.example.taskblock.model.task.TaskStatus;
import com.example.taskblock.model.taskblock.TaskBlock;
import com.example.taskblock.model.user.User;
import com.example.taskblock.model.wallet.Wallet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class WalletAndTaskBlockTest {

    private User user;
    private TaskBlock taskBlock;
    private Wallet wallet;

    @BeforeEach
    public void setup() {
        user = new User() {
            // Overriding abstract class methods for testing
            @Override
            public String getUsername() {
                return getEmail();
            }
        };
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setHandle("testUser");
        user.setPassword("hashedPassword");

        taskBlock = new TaskBlock("Project Alpha", user);

        wallet = new Wallet(user, taskBlock, BigDecimal.valueOf(100.0));
    }

    @Test
    public void testWalletCreation() {
        assertNotNull(wallet);
        assertEquals(user, wallet.getUser());
        assertEquals(taskBlock, wallet.getTaskBlock());
        assertEquals(BigDecimal.valueOf(100.0), wallet.getBalance());
    }

    @Test
    public void testAddFunds() {
        wallet.addFunds(BigDecimal.valueOf(50.0));
        assertEquals(BigDecimal.valueOf(150.0), wallet.getBalance());
    }

    @Test
    public void testDeductFunds() {
        wallet.deductFunds(BigDecimal.valueOf(30.0));
        assertEquals(BigDecimal.valueOf(70.0), wallet.getBalance());
    }

    @Test
    public void testDeductFundsThrowsExceptionWhenInsufficientBalance() {
        Exception exception = assertThrows(IllegalStateException.class, () -> wallet.deductFunds(BigDecimal.valueOf(150.0)));
        assertEquals("Insufficient balance", exception.getMessage());
    }

    @Test
    public void testHasSufficientBalance() {
        assertTrue(wallet.hasSufficientBalance(BigDecimal.valueOf(50.0)));
        assertFalse(wallet.hasSufficientBalance(BigDecimal.valueOf(150.0)));
    }

    @Test
    public void testTaskBlockRelationship() {
        assertEquals("Project Alpha", taskBlock.getName());
        assertEquals(user, taskBlock.getCreator());
    }

    @Test
    public void testAddingWalletToTaskBlock() {
        taskBlock.addWallet(wallet);
        assertTrue(taskBlock.getWallets().contains(wallet));
    }

    @Test
    public void testAddingTaskToTaskBlock() {
        Task task = new Task("Setup Environment", "Prepare the development environment",  taskBlock,100);
        taskBlock.addTask(task);

        assertTrue(taskBlock.getTasks().contains(task));
        assertEquals(taskBlock, task.getTaskBlock());
    }
}
