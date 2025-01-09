package com.example.taskblock.controller;


import com.example.taskblock.dto.TaskBlockDto;
import com.example.taskblock.model.taskblock.TaskBlock;
import com.example.taskblock.model.taskblock.TaskBlockGroup;
import com.example.taskblock.model.user.Member;
import com.example.taskblock.service.TaskBlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/taskblocks")
public class TaskBlockController {

    private final TaskBlockService taskBlockService;

    @Autowired
    public TaskBlockController(TaskBlockService taskBlockService) {
        this.taskBlockService = taskBlockService;
    }

    /**
     * Create a new TaskBlock
     */
    @PostMapping("/create")
    public ResponseEntity<TaskBlock> createTaskBlock(
            @AuthenticationPrincipal Member creator,
            @RequestBody TaskBlockDto taskBlock) {
        TaskBlock createdTaskBlock = taskBlockService.createTaskBlock(creator, taskBlock);
        return ResponseEntity.ok(createdTaskBlock);
    }

    /**
     * Get all TaskBlocks for the current user
     */
    @GetMapping
    public ResponseEntity<List<TaskBlock>> getUserTaskBlocks(
            @AuthenticationPrincipal Member currentUser) {
        List<TaskBlock> taskBlocks = taskBlockService.getUserTaskBlocks(currentUser);
        return ResponseEntity.ok(taskBlocks);
    }

    /**
     * Get a specific TaskBlock by ID
     */
    @GetMapping("/{taskBlockId}")
    public ResponseEntity<TaskBlock> getTaskBlock(
            @AuthenticationPrincipal Member currentUser,
            @PathVariable Long taskBlockId) {
        TaskBlock taskBlock = taskBlockService.getTaskBlockById(currentUser, taskBlockId);
        return ResponseEntity.ok(taskBlock);
    }

    /**
     * Update an existing TaskBlock
     */
    @PutMapping("/{taskBlockId}")
    public ResponseEntity<TaskBlock> updateTaskBlock(
            @AuthenticationPrincipal Member currentUser,
            @PathVariable Long taskBlockId,
            @RequestBody TaskBlock taskBlockDetails) {
        TaskBlock updatedTaskBlock = taskBlockService.updateTaskBlock(currentUser, taskBlockId, taskBlockDetails);
        return ResponseEntity.ok(updatedTaskBlock);
    }

    /**
     * Delete a TaskBlock
     */
    @DeleteMapping("/{taskBlockId}")
    public ResponseEntity<Void> deleteTaskBlock(
            @AuthenticationPrincipal Member currentUser,
            @PathVariable Long taskBlockId) {
        taskBlockService.deleteTaskBlock(currentUser, taskBlockId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Create a TaskBlock Group
     */
    @PostMapping("/{taskBlockId}/groups/create")
    public ResponseEntity<TaskBlockGroup> createTaskBlockGroup(
            @AuthenticationPrincipal Member currentUser,
            @PathVariable Long taskBlockId,
            @RequestBody TaskBlockGroup taskBlockGroup) {
        TaskBlockGroup createdGroup = taskBlockService.createTaskBlockGroup(currentUser, taskBlockId, taskBlockGroup);
        return ResponseEntity.ok(createdGroup);
    }

    /**
     * Get all Groups for a TaskBlock
     */
    @GetMapping("/{taskBlockId}/groups")
    public ResponseEntity<List<TaskBlockGroup>> getTaskBlockGroups(
            @AuthenticationPrincipal Member currentUser,
            @PathVariable Long taskBlockId) {
        List<TaskBlockGroup> groups = taskBlockService.getTaskBlockGroups(currentUser, taskBlockId);
        return ResponseEntity.ok(groups);
    }

    /**
     * Add a member to a TaskBlock Group
     */
    @PostMapping("/groups/{groupId}/add-member")
    public ResponseEntity<TaskBlockGroup> addMemberToGroup(
            @AuthenticationPrincipal Member currentUser,
            @PathVariable Long groupId,
            @RequestParam Long memberId) {
        TaskBlockGroup updatedGroup = taskBlockService.addMemberToGroup(currentUser, groupId, memberId);
        return ResponseEntity.ok(updatedGroup);
    }

    /**
     * Remove a member from a TaskBlock Group
     */
    @PostMapping("/groups/{groupId}/remove-member")
    public ResponseEntity<TaskBlockGroup> removeMemberFromGroup(
            @AuthenticationPrincipal Member currentUser,
            @PathVariable Long groupId,
            @RequestParam Long memberId) {
        TaskBlockGroup updatedGroup = taskBlockService.removeMemberFromGroup(currentUser, groupId, memberId);
        return ResponseEntity.ok(updatedGroup);
    }
}
