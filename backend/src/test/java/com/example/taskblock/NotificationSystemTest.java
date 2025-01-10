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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(MockitoExtension.class)
class NotificationSystemTest {
    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @InjectMocks
    private NotificationService notificationService;

    private Member creator;
    private Member voter;
    private TaskBlock taskBlock;
    private Task task;

    @BeforeEach
    void setUp() {
        // Set up test data
        creator = new Member();
        creator.setId(1L);
        creator.setHandle("creator");
        creator.setEmail("creator@test.com");

        voter = new Member();
        voter.setId(2L);
        voter.setHandle("voter");
        voter.setEmail("voter@test.com");

        taskBlock = new TaskBlock();
        taskBlock.setId(1L);
        taskBlock.setName("Test Block");
        taskBlock.setCreator(creator);
        taskBlock.setPercentageToAccept(75.0);
        taskBlock.setVoteDurationInSeconds(60);

        // Add wallets for members
        Wallet creatorWallet = new Wallet(creator, taskBlock, BigDecimal.valueOf(100.0));
        Wallet voterWallet = new Wallet(voter, taskBlock, BigDecimal.valueOf(100.0));
        taskBlock.addWallet(creatorWallet);
        taskBlock.addWallet(voterWallet);

        task = new Task("Test Task", "Description", taskBlock, 3600);
        task.addObserver(notificationService);

        // Setup notification repository mock to return the saved notification
        when(notificationRepository.save(any(Notification.class))).thenAnswer(invocation -> {
            Notification notification = invocation.getArgument(0);
            notification.setId(1L);
            return notification;
        });
    }

    @Test
    void whenVoteCast_creatorShouldBeNotified() {
        // Arrange
        Vote vote = new Vote(task, voter, true);
        ArgumentCaptor<Notification> notificationCaptor = ArgumentCaptor.forClass(Notification.class);
        ArgumentCaptor<String> topicCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Notification> messageCaptor = ArgumentCaptor.forClass(Notification.class);

        // Act
        task.addVote(vote);

        // Assert
        verify(notificationRepository).save(notificationCaptor.capture());
        verify(messagingTemplate).convertAndSend(topicCaptor.capture(), messageCaptor.capture());

        Notification savedNotification = notificationCaptor.getValue();
        String destination = topicCaptor.getValue();
        Notification sentNotification = messageCaptor.getValue();

        assertNotNull(savedNotification);
        assertEquals(NotificationType.NEW_VOTE, savedNotification.getType());
        assertEquals(creator, savedNotification.getRecipient());
        assertEquals(task, savedNotification.getTask());
        assertTrue(savedNotification.getMessage().contains(voter.getHandle()));
        assertEquals("/topic/notifications/" + creator.getId(), destination);
        assertEquals(savedNotification, sentNotification);
    }
}