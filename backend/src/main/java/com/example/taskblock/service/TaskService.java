package com.example.taskblock.service;

import com.example.taskblock.model.task.Task;
import com.example.taskblock.model.taskblock.TaskBlockGroup;
import com.example.taskblock.model.user.Member;
import com.example.taskblock.repository.TaskBlockGroupRepository;
import com.example.taskblock.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final NotificationService notificationService;
    private final TaskBlockGroupRepository taskBlockGroupRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, NotificationService notificationService,TaskBlockGroupRepository taskBlockGroupRepository) {
        this.taskRepository = taskRepository;
        this.notificationService = notificationService;
        this.taskBlockGroupRepository = taskBlockGroupRepository;
    }

    public Task createTask(Task task) {
        if (task.getDurationSeconds() < 0) {
            throw new IllegalArgumentException("Duration cannot be negative");
        }

        task.addObserver(notificationService);
        return taskRepository.save(task);
    }

    public Optional<Task> getTaskById(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        task.ifPresent(t -> t.addObserver(notificationService));
        return task;
    }

    public List<Task> getTasksByTaskBlockId(Long taskBlockId) {
        return taskRepository.findByTaskBlockId(taskBlockId);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public List<Member> getAllTaggedMembers(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        List<Member> allTaggedMembers = new ArrayList<>(task.getTaggedIndividuals());

        // Add all members from tagged groups
        for (TaskBlockGroup group : task.getTaggedGroups()) {
            for (Member member : group.getMembers()) {
                if(!allTaggedMembers.contains(member))
                     allTaggedMembers.add(member);
            }
        }

        return allTaggedMembers;
    }

    public void reorderTaggedIndividuals(Long taskId, List<Long> orderedMemberIds) throws ChangeSetPersister.NotFoundException {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());

        List<Member> currentMembers = task.getTaggedIndividuals();
        List<Member> reorderedMembers = new ArrayList<>();

        for (Long memberId : orderedMemberIds) {
            currentMembers.stream()
                    .filter(m -> m.getId().equals(memberId))
                    .findFirst()
                    .ifPresent(reorderedMembers::add);
        }

        task.setTaggedIndividuals(reorderedMembers);
        taskRepository.save(task);
    }

    public void reorderTaggedGroups(Long taskId, List<Long> orderedGroupIds) throws ChangeSetPersister.NotFoundException {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());

        List<TaskBlockGroup> currentGroups = task.getTaggedGroups();
        List<TaskBlockGroup> reorderedGroups = new ArrayList<>();

        for (Long groupId : orderedGroupIds) {
            currentGroups.stream()
                    .filter(g -> g.getId().equals(groupId))
                    .findFirst()
                    .ifPresent(reorderedGroups::add);
        }

        task.setTaggedGroups(reorderedGroups);
        taskRepository.save(task);
    }
    public void tagGroup(Long taskId, Long groupId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        TaskBlockGroup group = taskBlockGroupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));

        // Verify group belongs to same taskblock
        if (!task.getTaskBlock().getId().equals(group.getTaskBlock().getId())) {
            throw new IllegalArgumentException("Group does not belong to the same taskblock");
        }

        // Add group if not already tagged
        if (!task.getTaggedGroups().contains(group)) {
            task.getTaggedGroups().add(group);
            taskRepository.save(task);
        }
    }

    public void untagGroup(Long taskId, Long groupId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        task.getTaggedGroups().removeIf(group -> group.getId().equals(groupId));
        taskRepository.save(task);
    }


}
