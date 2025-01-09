package com.example.taskblock.controller;

import com.example.taskblock.model.taskblock.TaskBlock;
import com.example.taskblock.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/wallets")
public class WalletController {
    private final WalletService walletService;

    @Autowired
    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping("/user/{userId}/taskblocks")
    public ResponseEntity<List<TaskBlock>> getUserTaskBlocks(@PathVariable Long userId) {
        try {
            List<TaskBlock> taskBlocks = walletService.getTaskBlocksByUserId(userId);
            return ResponseEntity.ok(taskBlocks);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}