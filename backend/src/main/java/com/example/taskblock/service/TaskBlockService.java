package com.example.taskblock.service;

import com.example.taskblock.dto.GroupDto;
import com.example.taskblock.dto.TaskBlockDto;
import com.example.taskblock.model.task.Task;
import com.example.taskblock.model.taskblock.TaskBlock;
import com.example.taskblock.model.taskblock.TaskBlockGroup;
import com.example.taskblock.model.user.Member;
import com.example.taskblock.model.wallet.Wallet;
import com.example.taskblock.repository.MemberRepository;
import com.example.taskblock.repository.TaskBlockGroupRepository;
import com.example.taskblock.repository.TaskBlockRepository;
import com.example.taskblock.repository.WalletRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskBlockService {

    private final TaskBlockRepository taskBlockRepository;
    private final MemberRepository memberRepository;
    private final WalletRepository walletRepository;
    private final TaskBlockGroupRepository taskBlockGroupRepository;


    @Autowired
    public TaskBlockService(TaskBlockGroupRepository taskBlockGroupRepository,TaskBlockRepository taskBlockRepository,MemberRepository memberRepository, WalletRepository walletRepository) {
        this.taskBlockRepository = taskBlockRepository;
        this.memberRepository = memberRepository;
        this.walletRepository =   walletRepository;
        this.taskBlockGroupRepository =  taskBlockGroupRepository;
    }

    

    public Optional<TaskBlock> getTaskBlockById(Long id) {
        return taskBlockRepository.findById(id);
    }

    public List<TaskBlock> getAllTaskBlocks() {
        return taskBlockRepository.findAll();
    }

    public void deleteTaskBlock(Long id) {
        taskBlockRepository.deleteById(id);
    }

    public void addTaskToTaskBlock(TaskBlock taskBlock, Task task) {
        taskBlock.addTask(task);
        taskBlockRepository.save(taskBlock);
    }

    public TaskBlock createTaskBlock(TaskBlockDto taskBlockDto,Member creator){
        TaskBlock taskBlock = new TaskBlock();
        taskBlock.setName(taskBlockDto.getName());
        taskBlock.setCreator(creator);
        taskBlock.setPercentageToAccept(taskBlockDto.getPercentageToAccept());
        taskBlock.setVoteDurationInSeconds(taskBlockDto.getVoteDurationInMinutes());

        // Save the TaskBlock first to generate an ID
        TaskBlock savedTaskBlock = taskBlockRepository.save(taskBlock);

        for(String handle:taskBlockDto.getMembers()){
            Member member = memberRepository.findByHandle(handle)
                        .orElseThrow(() -> new RuntimeException("Member not found: " + handle));

                    // Create a wallet for each member in this TaskBlock
                    Wallet wallet = new Wallet();
                    wallet.setUser(member);
                    wallet.setTaskBlock(savedTaskBlock);
                    wallet.addFunds(new BigDecimal(100.0));
                    // Save the wallet
                    walletRepository.save(wallet);
                    savedTaskBlock.addWallet(wallet);

        }

        // Process groups from DTO
        if (taskBlockDto.getGroups() != null) {
            for (GroupDto groupDto : taskBlockDto.getGroups()) {
                // Create TaskBlockGroup
                TaskBlockGroup taskBlockGroup = new TaskBlockGroup();
                taskBlockGroup.setName(groupDto.getName());
                taskBlockGroup.setTaskBlock(savedTaskBlock);

                // Find and add members to the group
                List<Member> groupMembers = new ArrayList<>();
                for (String memberHandle : groupDto.getMembers()) {
                    // Find member by handle
                    Member member = memberRepository.findByHandle(memberHandle)
                    .orElseThrow(() -> new RuntimeException("Member not found: " + memberHandle));
                    

                    // Add member to the group
                    groupMembers.add(member);
                }

                // Set members in the group
                taskBlockGroup.setMembers(groupMembers);

                // Save the group
                taskBlockGroupRepository.save(taskBlockGroup);

            }
        }



        // Create a wallet for the creator if not already in a group
        Wallet creatorWallet = new Wallet();
        creatorWallet.addFunds(new BigDecimal(100.0));
        creatorWallet.setUser(creator);
        creatorWallet.setTaskBlock(savedTaskBlock);
        walletRepository.save(creatorWallet);
        // Save and return the final TaskBlock
        return taskBlockRepository.save(savedTaskBlock);

    }

    public List<Wallet> createWalletForMember(Long taskBlockId,List<String> memberHandle){
        TaskBlock taskBlock = taskBlockRepository.findById(taskBlockId).get();
        for(String handle:memberHandle){
                    Member member = memberRepository.findByHandle(handle)
                        .orElseThrow(() -> new RuntimeException("Member not found: " + handle));

                    // Create a wallet for each member in this TaskBlock
                    Wallet wallet = new Wallet();
                    wallet.addFunds(new BigDecimal(100.0));
                    wallet.setUser(member);
                    wallet.setTaskBlock(taskBlock);
                    // Save the wallet
                    walletRepository.save(wallet);
                    taskBlock.addWallet(wallet);
        }

        taskBlockRepository.save(taskBlock);
        return taskBlock.getWallets();



    }

}
