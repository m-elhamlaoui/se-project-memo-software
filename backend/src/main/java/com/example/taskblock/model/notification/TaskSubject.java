package com.example.taskblock.model.notification;

import com.example.taskblock.model.task.Task;
import com.example.taskblock.model.user.Member;

public interface TaskSubject {
    void addObserver(TaskObserver observer);
    void removeObserver(TaskObserver observer);
    void notifyVoteCast(Task task, Member voter);

    void notifyTaskAccepted(Task task);
    void notifyTaskRejected(Task task);
}
