package com.example.taskblock.service;

import com.example.taskblock.model.taskblock.TaskBlock;
import com.example.taskblock.model.taskblock.TaskBlockGroup;
import com.example.taskblock.model.user.Member;
import com.example.taskblock.repository.MemberRepository;
import com.example.taskblock.repository.TaskBlockRepository;
import com.example.taskblock.repository.TaskBlockGroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TaskBlockGroupService {
    private final TaskBlockGroupRepository taskBlockGroupRepository;
    private final TaskBlockRepository taskBlockRepository;
    private final MemberRepository memberRepository;

    public TaskBlockGroupService(TaskBlockGroupRepository taskBlockGroupRepository,
                                 TaskBlockRepository taskBlockRepository,
                                 MemberRepository memberRepository) {
        this.taskBlockGroupRepository = taskBlockGroupRepository;
        this.taskBlockRepository = taskBlockRepository;
        this.memberRepository = memberRepository;
    }

    public TaskBlockGroup createGroup(Long taskBlockId, Long creatorId, String groupName) {
        TaskBlock taskBlock = taskBlockRepository.findById(taskBlockId)
                .orElseThrow(() -> new IllegalArgumentException("TaskBlock not found"));

        // Verify creator
        if (!taskBlock.getCreator().getId().equals(creatorId)) {
            throw new IllegalArgumentException("Only taskblock creator can create groups");
        }

        // Check for duplicate names
        if (taskBlockGroupRepository.existsByTaskBlockIdAndName(taskBlockId, groupName)) {
            throw new IllegalArgumentException("Group with this name already exists");
        }

        TaskBlockGroup group = new TaskBlockGroup();
        group.setName(groupName);
        group.setTaskBlock(taskBlock);
        return taskBlockGroupRepository.save(group);
    }

    public void addMembersToGroup(Long taskBlockId, Long groupId, Long creatorId, List<Long> memberIds) {
        TaskBlock taskBlock = taskBlockRepository.findById(taskBlockId)
                .orElseThrow(() -> new IllegalArgumentException("TaskBlock not found"));

        // Verify creator
        if (!taskBlock.getCreator().getId().equals(creatorId)) {
            throw new IllegalArgumentException("Only taskblock creator can modify groups");
        }

        TaskBlockGroup group = taskBlockGroupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));

        List<Member> membersToAdd = memberRepository.findAllById(memberIds);
        group.getMembers().addAll(membersToAdd);
        taskBlockGroupRepository.save(group);
    }

    public void deleteGroup(Long taskBlockId, Long groupId, Long creatorId) {
        TaskBlock taskBlock = taskBlockRepository.findById(taskBlockId)
                .orElseThrow(() -> new IllegalArgumentException("TaskBlock not found"));

        // Verify creator
        if (!taskBlock.getCreator().getId().equals(creatorId)) {
            throw new IllegalArgumentException("Only taskblock creator can delete groups");
        }

        taskBlockGroupRepository.deleteById(groupId);
    }

    public List<TaskBlockGroup> getGroupsInTaskBlock(Long taskBlockId) {
        return taskBlockGroupRepository.findByTaskBlockId(taskBlockId);
    }

    public List<Member> getGroupMembers(Long groupId) {
        TaskBlockGroup group = taskBlockGroupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));
        return group.getMembers();
    }
}