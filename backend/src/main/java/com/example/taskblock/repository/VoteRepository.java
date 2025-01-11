package com.example.taskblock.repository;

import com.example.taskblock.model.vote.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    @Query("SELECT COUNT(v) * 1.0 / (SELECT COUNT(v1) FROM Vote v1 WHERE v1.task.id = :taskId) " +
            "FROM Vote v WHERE v.task.id = :taskId AND v.value = true")
    Double calculateVotePercentage(Long taskId);

    List<Vote> findByTaskId(Long taskId);
}
