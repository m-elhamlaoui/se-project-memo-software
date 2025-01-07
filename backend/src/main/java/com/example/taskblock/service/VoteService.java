package com.example.taskblock.service;

import com.example.taskblock.model.task.Task;
import com.example.taskblock.model.vote.Vote;
import com.example.taskblock.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoteService {

    private final VoteRepository voteRepository;

    @Autowired
    public VoteService(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    public List<Vote> getVotesByTaskId(Long taskId) {
        return voteRepository.findByTaskId(taskId);
    }

    public void deleteVote(Long id) {
        voteRepository.deleteById(id);
    }

    public Vote castVote(Vote vote) {
        Vote savedVote = voteRepository.save(vote);
        Task task = vote.getTask();
        task.addVote(savedVote);
        task.getTaskBlock().evaluateTask(task);
        return savedVote;
    }

}
