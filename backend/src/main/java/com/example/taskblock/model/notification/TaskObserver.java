package com.example.taskblock.model.notification;

import com.example.taskblock.model.task.Task;
import com.example.taskblock.model.user.Member;

public interface TaskObserver {
    void onVoteCast(Task task, Member voter);
    void onTaskAccepted(Task task);
    void onTaskRejected(Task task);
}