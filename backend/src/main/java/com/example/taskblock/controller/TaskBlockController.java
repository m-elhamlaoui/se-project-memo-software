package com.example.taskblock.controller;


import com.example.taskblock.dto.TaskBlockDto;
import com.example.taskblock.model.taskblock.TaskBlock;
import com.example.taskblock.model.taskblock.TaskBlockGroup;
import com.example.taskblock.model.user.Member;
import com.example.taskblock.model.wallet.Wallet;
import com.example.taskblock.service.TaskBlockGroupService;
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
    private final TaskBlockGroupService taskBlockGroupService;

    @Autowired
    public TaskBlockController(TaskBlockService taskBlockService,TaskBlockGroupService taskBlockGroupService) {
        this.taskBlockService = taskBlockService;
        this.taskBlockGroupService =  taskBlockGroupService;
    }

    /**
     * Create a new TaskBlock
     */
    @PostMapping("/create")
    public ResponseEntity<TaskBlock> createTaskBlock(
            @AuthenticationPrincipal Member creator,
            @RequestBody TaskBlockDto taskBlock) {
        TaskBlock createdTaskBlock = taskBlockService.createTaskBlock(taskBlock,creator);
        return ResponseEntity.ok(createdTaskBlock);
    }

   
    /**
     * Get a specific TaskBlock by ID
     */
    @GetMapping("/{taskBlockId}")
    public ResponseEntity<?> getTaskBlock(
            @AuthenticationPrincipal Member currentUser,
            @PathVariable Long taskBlockId) {
        TaskBlock taskBlock = taskBlockService.getTaskBlockById(taskBlockId).orElse(null);
        
        if (taskBlock != null)
        return ResponseEntity.ok(taskBlock);
        else return ResponseEntity.badRequest().body("not found");
    }  
    /**
     * Get all Groups for a TaskBlock
     */
    @GetMapping("/{taskBlockId}/groups")
    public ResponseEntity<List<TaskBlockGroup>> getTaskBlockGroups(
            @AuthenticationPrincipal Member currentUser,
            @PathVariable Long taskBlockId) {
        List<TaskBlockGroup> groups = taskBlockGroupService.getGroupsInTaskBlock(taskBlockId);
        return ResponseEntity.ok(groups);
    }

    /**
     * Add a member to a TaskBlock Group
     */
    @PostMapping("/{taskBlockId}/create-wallet")
    public ResponseEntity< List<Wallet>> createWallet(
            @AuthenticationPrincipal Member currentUser,
            @PathVariable Long taskBlockId,
            @RequestBody List<String> memberHandle) {
        List<Wallet> createdWallet = taskBlockService.createWalletForMember(
            taskBlockId, memberHandle
        );
        return ResponseEntity.ok(createdWallet);
    }

    /**
     * Remove a member from a TaskBlock Group
     */
    /* 
    @PostMapping("/groups/{groupId}/remove-member")
    public ResponseEntity<TaskBlockGroup> removeMemberFromGroup(
            @AuthenticationPrincipal Member currentUser,
            @PathVariable Long groupId,
            @RequestParam Long memberId) {
        TaskBlockGroup updatedGroup = taskBlockService.removeMemberFromGroup(currentUser, groupId, memberId);
        return ResponseEntity.ok(updatedGroup);
    }
    */
}
