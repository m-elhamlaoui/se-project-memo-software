package com.example.taskblock.service;

import com.example.taskblock.model.notification.*;
import com.example.taskblock.model.task.Task;
import com.example.taskblock.model.user.User;
import com.example.taskblock.model.user.Member;
import com.example.taskblock.repository.NotificationRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class NotificationService implements TaskObserver {
    private final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public NotificationService(
            NotificationRepository notificationRepository,
            SimpMessagingTemplate messagingTemplate
    ) {
        this.notificationRepository = notificationRepository;
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void onVoteCast(Task task, Member voter) {
        String message = String.format("User %s has voted on task: %s",
                voter.getHandle(), task.getTitle());

        createAndSendNotification(
                task.getTaskBlock().getCreator(),
                task,
                NotificationType.NEW_VOTE,
                message
        );
    }

    @Override
    public void onTaskAccepted(Task task) {
        String message = String.format("Task '%s' has been accepted", task.getTitle());

        task.getTaskBlock().getWallets().forEach(wallet -> {
            createAndSendNotification(
                    wallet.getUser(),
                    task,
                    NotificationType.TASK_ACCEPTED,
                    message
            );
        });
    }

    @Override
    public void onTaskRejected(Task task) {
        String message = String.format("Task '%s' has been rejected", task.getTitle());

        task.getTaskBlock().getWallets().forEach(wallet -> {
            createAndSendNotification(
                    wallet.getUser(),
                    task,
                    NotificationType.TASK_REJECTED,
                    message
            );
        });
    }

    private void createAndSendNotification(User recipient, Task task,
                                           NotificationType type, String message) {
        Notification notification = new Notification();
        notification.setRecipient(recipient);
        notification.setTask(task);
        notification.setType(type);
        notification.setMessage(message);

        // Save to database
        Notification savedNotification = notificationRepository.save(notification);

        // Send real-time notification
        sendRealTimeNotification(savedNotification);
    }

    private void sendRealTimeNotification(Notification notification) {
        String destination = "/topic/notifications/" + notification.getRecipient().getId();
        messagingTemplate.convertAndSend(destination, notification);
    }

    // Methods for retrieving notifications
    public List<Notification> getUserNotifications(User user) {
        return notificationRepository.findByRecipientOrderByCreatedAtDesc(user);
    }

    public List<Notification> getUnreadNotifications(User user) {
        return notificationRepository.findByRecipientAndReadFalseOrderByCreatedAtDesc(user);
    }

    // Methods for managing notifications
    public void markAsRead(Long notificationId) {
        notificationRepository.findById(notificationId)
                .ifPresent(notification -> {
                    notification.setRead(true);
                    notificationRepository.save(notification);
                });
    }

    public void markAllAsRead(User user) {
        List<Notification> unreadNotifications =
                notificationRepository.findByRecipientAndReadFalseOrderByCreatedAtDesc(user);

        unreadNotifications.forEach(notification -> notification.setRead(true));
        notificationRepository.saveAll(unreadNotifications);
    }

    public void deleteNotification(Long notificationId) {
        notificationRepository.deleteById(notificationId);
    }

    public void deleteAllUserNotifications(User user) {
        List<Notification> userNotifications =
                notificationRepository.findByRecipientOrderByCreatedAtDesc(user);
        notificationRepository.deleteAll(userNotifications);
    }

    public List<Notification> getNotifications(User user) {
        return notificationRepository.findByRecipientOrderByCreatedAtDesc(user);
    }

}