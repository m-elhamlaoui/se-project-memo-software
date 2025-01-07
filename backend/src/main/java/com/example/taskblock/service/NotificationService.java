package com.example.taskblock.service;

import com.example.taskblock.model.notification.*;
import com.example.taskblock.model.task.Task;
import com.example.taskblock.model.taskblock.TaskBlock;
import com.example.taskblock.model.user.Member;
import com.example.taskblock.model.wallet.Wallet;
import com.example.taskblock.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class NotificationService implements TaskObserver {
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void onVoteCast(Task task, Member voter) {
        Notification notification = new Notification();
        notification.setType(NotificationType.NEW_VOTE);
        notification.setRecipient(task.getTaskBlock().getCreator());
        notification.setTask(task);
        notification.setMessage(String.format("User %s has voted on task: %s",
                voter.getHandle(), task.getTitle()));

        notificationRepository.save(notification);
    }

    @Override
    public void onTaskAccepted(Task task) {
        notifyAllTaskBlockMembers(task, NotificationType.TASK_ACCEPTED);
    }

    @Override
    public void onTaskRejected(Task task) {
        notifyAllTaskBlockMembers(task, NotificationType.TASK_REJECTED);
    }

    private void notifyAllTaskBlockMembers(Task task, NotificationType type) {
        TaskBlock taskBlock = task.getTaskBlock();

        for (Wallet wallet : taskBlock.getWallets()) {
            Notification notification = new Notification();
            notification.setType(type);
            notification.setRecipient(wallet.getUser());
            notification.setTask(task);
            notification.setMessage(String.format("Task '%s' has been %s",
                    task.getTitle(),
                    type == NotificationType.TASK_ACCEPTED ? "accepted" : "rejected"));

            notificationRepository.save(notification);
        }
    }
}