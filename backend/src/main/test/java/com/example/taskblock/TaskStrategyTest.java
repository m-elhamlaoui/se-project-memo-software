package com.example.taskblock;

import com.example.taskblock.model.strategy.impl.FlexiblePercentageAcceptanceStrategy;
import com.example.taskblock.model.strategy.impl.FixedTimeRejectionStrategy;
import com.example.taskblock.model.task.Task;
import com.example.taskblock.model.task.TaskStatus;
import com.example.taskblock.model.taskblock.TaskBlock;
import com.example.taskblock.model.user.User;
import com.example.taskblock.repository.VoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class TaskStrategyTest {

    private TaskBlock taskBlock;
    private VoteRepository voteRepository;

    @BeforeEach
    public void setup() {
        // Mock the VoteRepository
        voteRepository = Mockito.mock(VoteRepository.class);

        // Create a default User
        User creator = new User() {
            @Override
            public String getUsername() {
                return "defaultUser@example.com";
            }
        };

        // Initialize TaskBlock
        taskBlock = new TaskBlock("Test TaskBlock", creator);

        // Set strategies and repository in TaskBlock
        taskBlock.setVoteRepository(voteRepository);
        taskBlock.setPercentageAcceptanceStrategy(new FlexiblePercentageAcceptanceStrategy(60.0)); // 60% threshold
        taskBlock.setTimeRejectionStrategy(new FixedTimeRejectionStrategy(1440)); // 1 day
    }

    @Test
    public void testTaskRejectedWhenTimeExpires() {
        // Create a task that was created 2 days ago
        Task task = new Task("Expired Task", "Task that should expire", taskBlock);
        task.setCreatedAt(LocalDateTime.now().minusDays(2)); // 2 days ago
        System.out.println(task.getCreatedAt());
        // Evaluate the task
        taskBlock.evaluateTask(task);

        // Task should be rejected
        assertEquals(TaskStatus.REJECTED, task.getStatus(), "Task should be rejected because time expired.");
    }

    @Test
    public void testTaskAcceptedWhenVotePercentageMeetsThreshold() {

        taskBlock.setPercentageAcceptanceStrategy(new FlexiblePercentageAcceptanceStrategy(0.0)); // 60% threshold
        // Create a task created 1 hour ago
        Task task = new Task("Acceptable Task", "Task that should be accepted", taskBlock);
        task.setCreatedAt(LocalDateTime.now().minusHours(1)); // 1 hour ago

        // Evaluate the task
        taskBlock.evaluateTask(task);

        // Task should be accepted
        assertEquals(TaskStatus.ACCEPTED, task.getStatus(), "Task should be accepted because vote percentage meets threshold.");
        taskBlock.setPercentageAcceptanceStrategy(new FlexiblePercentageAcceptanceStrategy(60.0)); // 60% threshold

    }

    @Test
    public void testTaskPendingWhenVotePercentageDoesNotMeetThreshold() {
        // Mock the vote percentage to 50% (below 60% threshold)
        when(voteRepository.calculateVotePercentage(anyLong())).thenReturn(50.0);

        // Create a task created 1 hour ago
        Task task = new Task("Pending Task", "Task that should stay pending", taskBlock);
        task.setCreatedAt(LocalDateTime.now().minusHours(1)); // 1 hour ago

        // Evaluate the task
        taskBlock.evaluateTask(task);

        // Task should remain pending
        assertEquals(TaskStatus.PENDING, task.getStatus(), "Task should remain pending because vote percentage does not meet threshold.");
    }

    @Test
    public void testTaskRejectedWhenVoteDurationExpiresEvenWithHighPercentage() {
        // Mock the vote percentage to 80% (above 60% threshold)
        when(voteRepository.calculateVotePercentage(anyLong())).thenReturn(80.0);

        // Create a task created 2 days ago
        Task task = new Task("Late Task", "Task that should be rejected due to expiration", taskBlock);
        task.setCreatedAt(LocalDateTime.now().minusDays(2)); // 2 days ago

        // Evaluate the task
        taskBlock.evaluateTask(task);

        // Task should be rejected due to time expiration
        assertEquals(TaskStatus.REJECTED, task.getStatus(), "Task should be rejected because time expired, even if vote percentage is high.");
    }
}
