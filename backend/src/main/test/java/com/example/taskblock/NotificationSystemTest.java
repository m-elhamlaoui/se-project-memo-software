package com.example.taskblock;

import com.example.taskblock.model.notification.Notification;
import com.example.taskblock.model.notification.NotificationType;
import com.example.taskblock.model.task.Task;
import com.example.taskblock.model.task.TaskStatus;
import com.example.taskblock.model.taskblock.TaskBlock;
import com.example.taskblock.model.user.Member;
import com.example.taskblock.model.vote.Vote;
import com.example.taskblock.model.wallet.Wallet;
import com.example.taskblock.repository.NotificationRepository;
import com.example.taskblock.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class NotificationSystemTest {
    private NotificationRepository notificationRepository;
    private NotificationService notificationService;
    private Member creator;
    private Member voter;
    private TaskBlock taskBlock;
    private Task task;

    @BeforeEach
    void setUp() {
        // Mock repository
        notificationRepository = mock(NotificationRepository.class);
        notificationService = new NotificationService(notificationRepository);

        // Set up test data
        creator = new Member();
        creator.setId(1L);
        creator.setHandle("creator");
        creator.setEmail("creator@test.com");

        voter = new Member();
        voter.setId(2L);
        voter.setHandle("voter");
        voter.setEmail("voter@test.com");

        taskBlock = new TaskBlock("Test Block", creator);

        // Add wallets for members
        Wallet creatorWallet = new Wallet(creator, taskBlock, BigDecimal.valueOf(100.0));
        Wallet voterWallet = new Wallet(voter, taskBlock, BigDecimal.valueOf(100.0));
        taskBlock.addWallet(creatorWallet);
        taskBlock.addWallet(voterWallet);

        task = new Task("Test Task", "Description", taskBlock);
        task.addObserver(notificationService);
    }

    @Test
    void whenVoteCast_creatorShouldBeNotified() {
        // Arrange
        Vote vote = new Vote(task, voter, true);
        ArgumentCaptor<Notification> notificationCaptor = ArgumentCaptor.forClass(Notification.class);

        // Act
        task.addVote(vote);

        // Assert
        verify(notificationRepository).save(notificationCaptor.capture());
        Notification capturedNotification = notificationCaptor.getValue();

        assertEquals(NotificationType.NEW_VOTE, capturedNotification.getType());
        assertEquals(creator, capturedNotification.getRecipient());
        assertEquals(task, capturedNotification.getTask());
        assertTrue(capturedNotification.getMessage().contains(voter.getHandle()));
    }

    @Test
    void whenTaskAccepted_allMembersShouldBeNotified() {
        // Arrange
        task.setStatus(TaskStatus.PENDING);
        Member member2 = new Member();
        member2.setId(3L);
        // Create and add wallet for the new member
        Wallet wallet2 = new Wallet(member2, taskBlock, BigDecimal.valueOf(100.0));
        taskBlock.addWallet(wallet2);
        ArgumentCaptor<Notification> notificationCaptor = ArgumentCaptor.forClass(Notification.class);

        // Act
        task.setStatus(TaskStatus.ACCEPTED);

        // Assert
        verify(notificationRepository, atLeast(2)).save(notificationCaptor.capture());
        List<Notification> notifications = notificationCaptor.getAllValues();

        assertTrue(notifications.stream()
                .allMatch(n -> n.getType() == NotificationType.TASK_ACCEPTED));
        assertTrue(notifications.stream()
                .map(Notification::getRecipient)
                .anyMatch(r -> r.getId().equals(creator.getId())));
    }

    @Test
    void whenTaskRejected_allMembersShouldBeNotified() {
        // Arrange
        task.setStatus(TaskStatus.PENDING);
        ArgumentCaptor<Notification> notificationCaptor = ArgumentCaptor.forClass(Notification.class);

        // Act
        task.setStatus(TaskStatus.REJECTED);

        // Assert
        verify(notificationRepository, atLeast(1)).save(notificationCaptor.capture());
        List<Notification> notifications = notificationCaptor.getAllValues();

        assertTrue(notifications.stream()
                .allMatch(n -> n.getType() == NotificationType.TASK_REJECTED));
    }

    @Test
    void multipleStatusChanges_shouldOnlyNotifyOnValidTransitions() {
        // Arrange
        task.setStatus(TaskStatus.PENDING);

        task.setStatus(TaskStatus.ACCEPTED);
        task.setStatus(TaskStatus.ACCEPTED); // Second change shouldn't trigger notification

        verify(notificationRepository, times(taskBlock.getWallets().size()))
                .save(any(Notification.class));
    }

    @Test
    void observerRegistration_shouldWork() {
        // Arrange
        Task newTask = new Task("New Task", "Description", taskBlock);

        // Act
        newTask.addObserver(notificationService);
        newTask.removeObserver(notificationService);
        Vote vote = new Vote(newTask, voter, true);
        newTask.addVote(vote);

        // Assert
        verify(notificationRepository, never()).save(any(Notification.class));
    }
}