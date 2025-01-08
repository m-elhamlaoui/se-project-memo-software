package com.example.taskblock;

import com.example.taskblock.model.task.Task;
import com.example.taskblock.model.task.TaskStatus;
import com.example.taskblock.model.taskblock.TaskBlock;
import com.example.taskblock.model.user.User;
import com.example.taskblock.model.vote.Vote;
import com.example.taskblock.model.wallet.Wallet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TaskBlockModelWorkflowTest {

    private User user1;
    private User user2;
    private User user3;
    private TaskBlock taskBlock;
    private Wallet wallet1;
    private Wallet wallet2;
    private Wallet wallet3;

    @BeforeEach
    public void setup() {
        // Step 1: Create Users
        user1 = new User() {
            @Override
            public String getUsername() {
                return getEmail();
            }
        };
        user1.setId(1L);
        user1.setEmail("creator@example.com");
        user1.setHandle("creator");
        user1.setPassword("password");

        user2 = new User() {
            @Override
            public String getUsername() {
                return getEmail();
            }
        };
        user2.setId(2L);
        user2.setEmail("voter1@example.com");
        user2.setHandle("voter1");
        user2.setPassword("password");

        user3 = new User() {
            @Override
            public String getUsername() {
                return getEmail();
            }
        };
        user3.setId(3L);
        user3.setEmail("voter2@example.com");
        user3.setHandle("voter2");
        user3.setPassword("password");

        // Step 2: Create TaskBlock
        taskBlock = new TaskBlock("Project Alpha", user1);

        // Step 3: Create Wallets for Users
        wallet1 = new Wallet(user1, taskBlock, BigDecimal.valueOf(200.0));
        wallet2 = new Wallet(user2, taskBlock, BigDecimal.valueOf(100.0));
        wallet3 = new Wallet(user3, taskBlock, BigDecimal.valueOf(150.0));

        taskBlock.addWallet(wallet1);
        taskBlock.addWallet(wallet2);
        taskBlock.addWallet(wallet3);
    }

    @Test
    public void testTaskBlockWorkflow() {
        Task task = new Task("Implement Feature X", "Develop backend logic for feature X", taskBlock,100);
        taskBlock.addTask(task);

        assertNotNull(taskBlock.getTasks());
        assertEquals(1, taskBlock.getTasks().size());
        assertEquals(task, taskBlock.getTasks().get(0));
        Vote vote1 = new Vote(task, user2, true); // User 2 casts an upvote
        Vote vote2 = new Vote(task, user3, false); // User 3 casts a downvote
        task.addVote(vote1);
        task.addVote(vote2);
        assertNotNull(task.getVotes());
        assertEquals(2, task.getVotes().size());
        assertTrue(task.getVotes().contains(vote1));
        assertTrue(task.getVotes().contains(vote2));

        long upvotes = task.getVotes().stream().filter(Vote::isValue).count();
        long downvotes = task.getVotes().size() - upvotes;
        System.out.println("Task Voting Results:");
        System.out.println("Upvotes: " + upvotes);
        System.out.println("Downvotes: " + downvotes);
        assertEquals(1, upvotes);
        assertEquals(1, downvotes);
    }

    @Test
    public void testWalletOperations() {
        // Ensure initial wallet balances
        assertEquals(BigDecimal.valueOf(200.0), wallet1.getBalance());
        assertEquals(BigDecimal.valueOf(100.0), wallet2.getBalance());
        assertEquals(BigDecimal.valueOf(150.0), wallet3.getBalance());

        // Add funds to a wallet
        wallet1.addFunds(BigDecimal.valueOf(50.0));
        assertEquals(BigDecimal.valueOf(250.0), wallet1.getBalance());

        // Deduct funds from a wallet
        wallet2.deductFunds(BigDecimal.valueOf(30.0));
        assertEquals(BigDecimal.valueOf(70.0), wallet2.getBalance());

        // Ensure insufficient funds throw an exception
        Exception exception = assertThrows(IllegalStateException.class, () -> wallet3.deductFunds(BigDecimal.valueOf(200.0)));
        assertEquals("Insufficient balance", exception.getMessage());
    }
}
